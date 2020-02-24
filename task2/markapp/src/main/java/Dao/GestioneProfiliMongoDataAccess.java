/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entita.*;
import com.mongodb.*;
import com.mongodb.TransactionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.TransactionBody;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
import java.util.Arrays;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author berto
 * La classe gestisce tutto ciò che riguarda la gestione dei profili
 * e l'aggiornamento del database
 * 
 */
public class GestioneProfiliMongoDataAccess extends MongoDataAccess{
    
    
    /**
     * La funzione effetua una ricerca dell'utente in base al ruolo 
     * e restituisce un'istanza dell'utente trovato come parametro
     * e ritorna un intero come codice di errore;
     * Attenzione bisogna fare un controllo sulla classe utente
     * per controllare se esiste l'utente cercato oppure no
     * @param squadra
     * @param nazione
     * @param utente
     * @param ruolo
     * @return 0:trovata la società;1:società non trovata;2: altri errori
     */
    public static int trovaUtenteInBaseAlRuolo(Utente utente,String squadra,String nazione,String ruolo){
        Document doc=null;
        try{
            doc = collectionSocieta.aggregate(Arrays.asList(match(and(eq("nomeSocieta", squadra), eq("nazione", nazione))), lookup("utenti", "_id", "societa", "utente"), match(eq("utente.ruolo", ruolo)), project(include("nomeSocieta", "nazione", "utente._id", "utente.nome", "utente.cognome", "utente.email","utente.ruolo")))).first();
            //1)
        } catch(Exception e){
            return 2;
        }
        if(doc==null){
            try {
                doc=collectionSocieta.find(and(eq("nomeSocieta", squadra), eq("nazione", nazione))).first();
                //2)
            } catch (Exception e) {
                return 2;
            }
        } else{
            List<Document> utenteDoc=(List<Document>) doc.get("utente");
            for(Document aux : utenteDoc){
                utente.setId(aux.getObjectId("_id").toString());
                utente.setNome(aux.getString("nome"));
                utente.setCognome(aux.getString("cognome"));
                utente.setEmail(aux.getString("email"));
                utente.setRuolo(aux.getString("ruolo"));
                
            }
        }
        if(doc==null){
            return 1;
        }    
        Societa soc=new Societa();
        soc.setId(doc.getObjectId("_id").toString());
        soc.setNazione(doc.getString("nazione"));
        soc.setNomeSocieta(doc.getString("nomeSocieta"));        
        utente.setSocieta(soc);
        //3
        return 0;
        //
        //1) Qui ricerco l'utente e la società a cui appartiene
        //2) Se la società non ha un utente per quel ruolo cerco solo la società
        //3) la classe restituisce in intero come codice di errore
        // e un'istanza della classe utente che contiene la società e se c'è l'utente
    }
    /**
     * la funzione aggiorna il campo dei vari ruoli nella collection
     * societa
     * @param vecchioMembro
     * @param nuovoMembroEmail
     * @param controlloRuolo
     * @return
     * 0: aggiornamento riuscito
     * 1: l'utente trovato e il ruolo scelto non coincidono,
     * 2: l'utente selezionato non esiste;
     * 3: problemi elimina da società
     * 4: non è stato possibile aggiornare la società
     */
    public  static int aggiornaTeamSocieta(Utente vecchioMembro, String nuovoMembroEmail,String controlloRuolo){
        Utente nuovoMembro=null; 
        
        try {
            nuovoMembro=cercaUtenteDaEmail(nuovoMembroEmail);
        } catch (Exception ex) {
            return 2;
        }
        if(nuovoMembro==null)
            return 2;
        String ruolo=nuovoMembro.getRuolo().toLowerCase();
        if(!ruolo.equals(controlloRuolo.toLowerCase())){
            return 1;
        }
        String idSocieta=vecchioMembro.getSocieta().getId();
        String idNuovoMembro=nuovoMembro.getId();
        int er=eliminaUtenteDaSocieta(ruolo, new ObjectId(idNuovoMembro));
        if(er!=0){
            return 3;
        }
        if(vecchioMembro.getId()!=null)
            er=eliminaUtenteDaSocieta(vecchioMembro.getRuolo().toLowerCase(), new ObjectId(vecchioMembro.getId()));
        if(er!=0){
            return 3;
        }
        
        ClientSession clientSession=mongoClient.startSession();
        TransactionBody txnBody = (TransactionBody<String>) () -> {
            collectionSocieta.updateOne(eq("_id",new ObjectId(idSocieta) ), set(ruolo,new ObjectId(idNuovoMembro))  );
            collectionUtenti.updateOne(eq("_id",new ObjectId(idNuovoMembro) ), set("societa",new ObjectId(idSocieta))  );
            return "aggiorna societa";
        };
        try {
            clientSession.withTransaction(txnBody);
        } catch (RuntimeException e) {
            return 4;
        } finally {
            clientSession.close();
        }


        
        vecchioMembro.setId(nuovoMembro.getId());
        vecchioMembro.setCognome(nuovoMembro.getCognome());
        vecchioMembro.setNome(nuovoMembro.getNome());
        vecchioMembro.setEmail(nuovoMembro.getEmail());
        vecchioMembro.setRuolo(nuovoMembro.getRuolo());
        return 0;
        //1 se non riesce a svolgere le operazioni seguenti:
        //è stato possibile settare i campi sia nella collection utenti sia nella societa
        //ma non è stato possibile inserire il nuovo utente nella nuova societa
    }
    /**
     * Funzione di utilita per eliminare utente 
     * anche dal documento della societa
     * @return 
     * 0 se cancellazione andata a buon fine, 
     * 1 non è stato possibile eseguire l'operazione
     */
    private static int eliminaUtenteDaSocieta(String ruolo,ObjectId id){
            
    ClientSession clientSession=mongoClient.startSession();
    TransactionBody txnBody = new TransactionBody<String>() {
        public String execute() {

            /*
               Important:: You must pass the session to the operations.
             */
            Bson filter = eq("_id", id);
            Bson updateOperation = set("societa", "");
            collectionUtenti.updateOne(clientSession,filter, updateOperation);
            filter=eq(ruolo.toLowerCase(),id);
            updateOperation=set(ruolo.toLowerCase(),"");
            collectionSocieta.updateOne(clientSession,filter, updateOperation);
            return "cancella societa da utenti";
        }

    };
    try {
        clientSession.withTransaction(txnBody);
    } catch (RuntimeException e) {
        return 1;
    } finally {
        clientSession.close();
    }
            
        return 0;
    }
    
    /**
     * 
     * @param email
     * @return  0 se account eliminato correttamente, 1 se email non esistente, 
     * 2 altri errori
     */
    public static int eliminaAccount(String email, String ruolo, String id){
        DeleteResult deleteResult = null;
        //Per prima cosa va cancellato l'utente dalla collection "utenti"
        try{
            deleteResult = collectionUtenti.deleteOne(eq("email", email));
            
        } catch(Exception e){
            return 2;
        }
        if(deleteResult.getDeletedCount() == 1){ //Se è stato cancellato un utente...
            
            if(!ruolo.equals("admin")){ //Se è un utente di una società dobbiamo eliminarlo
                                        //anche dal documento della società
                return eliminaUtenteDaSocieta(ruolo, new ObjectId(id));
                
            } else{                 //Se è un admin abbiamo finito. 
                return 0;
            }
        } else //Se non è stato cancellato un utente è un errore. 
               //(Problema: se, per qualsiasi motivo, vengono cancellati più utenti ?
            return 1;
    }
    
    /**
     * Funzione che restituisce un utente in formato document a partire da un email,
     * viene utilizzata per ricercare e restituire un document di un utente che
     * verrà utilizzato per creare successivamente un oggetto di tipo Utente
     * 
     * @param email
     * @return Se l'utente è presente restituirà un oggetto di tipo Document
     * altrimenti restituisce null
     * @throws Exception 
     */
    public static Document cercaUtenteDocumentDaEmail(String email) throws Exception
    {
        if(email==null)
            return null;
        Document utenteDoc;
        utenteDoc=(Document)collectionUtenti.find(eq("email",email)).first();
        return utenteDoc;
    }
    /**
     * Funzione che datata l'email vecchia permette di cambiare i seguenti 
     * parametri dell'utente: email e password in base ai valori presenti nei
     * parametri nuovaEmail e password.
     * 
     * @param vecchiaEmail email attuale dell'utente a cui si vogliono cambiare
     * le informazioni
     * @param nuovaEmail rappresenta la nuova email da sostituire con quella vecchia
     * se non si vuole cambiare email va passato il carattere ""
     * @param password rasenta la nuova password da sostituire a quella vecchia
     * se non si vuole cambiare password va passato il carattere ""
     * @return restituisce false se la modifica non è andata a buon fine: la 
     * vecchiaEmail non corrisponde a nessun utente registrato, la nuova email è
     * già usata da un utente oppure non viene effettuata nessuna modifica ovvero
     * la nuovaEmail e password sono entrambe stringhe "";
     * @throws Exception 
     */
    public static boolean modificaProfilo(String vecchiaEmail,String nuovaEmail,String password) throws Exception
    {
        Document nuovoValore;
        Document updateDocument;
        UpdateResult updateResult;
        Document utenteDoc=cercaUtenteDocumentDaEmail(vecchiaEmail);
        if(utenteDoc==null || (nuovaEmail.equals("")&&password.equals(""))){
            return false;
        }
        if(!nuovaEmail.equals(""))
        {
            if(cercaUtenteDocumentDaEmail(nuovaEmail)!=null)
            {
                return false;
            }
            if(password.equals(""))
            {
                nuovoValore = new Document("email",nuovaEmail);
                updateDocument = new Document("$set",nuovoValore);
                updateResult =collectionUtenti.updateOne(eq("email",vecchiaEmail),updateDocument);
                return true;
                
            }
            nuovoValore= new Document("email",nuovaEmail);
            nuovoValore=nuovoValore.append("password", password);
            updateDocument = new Document("$set",nuovoValore);
            updateResult = collectionUtenti.updateOne(eq("email",vecchiaEmail),updateDocument);
            return true;
        }
        nuovoValore = new Document("password",password);
        updateDocument = new Document("$set",nuovoValore);
        updateResult =collectionUtenti.updateOne(eq("email",vecchiaEmail),updateDocument);
        return true;
    }
    /**
     * Funzione che restituisce un utente come oggetto Utente a partire da un'email
     * viene utilizzata per ricercare e restituire le informazioni principali di
     * un utente con il campo oggetto società a null poichè è un'informazione
     * che non serve nella modifica profilo dell'utente
     * @param email
     * @return
     * Se l'utente è presente restituirà un oggetto di tipo Utente con i campi 
     * @throws Exception 
     */
    
    public static Utente cercaUtenteDaEmail(String email) throws Exception
    {
        Document utenteDoc;
        Utente utente; 
        utenteDoc=cercaUtenteDocumentDaEmail(email);
        if(utenteDoc==null)
            return null;
        utente=new Utente(utenteDoc.getObjectId("_id").toString(), utenteDoc.getString("nome"),
                   utenteDoc.getString("cognome"), utenteDoc.getString("email"), utenteDoc.getString("ruolo"),null);
        return utente;
    
    } 
}
