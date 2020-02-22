/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.MongoDataAccess.apriConnessione;
import Entita.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import task2.markapp.ScreenController;

/**
 *
 * @author berto
 * La classe gestisce tutto ciò che riguarda la gestione dei profili
 * e l'aggiornamento del database
 * 
 */
public class GestioneProfiliMongoDataAccess extends MongoDataAccess{
    private static final String nomeCollectionSocieta="societa"; 
    private static final String nomeCollectionUtenti = "utenti";
    /**
     * La funzione effetua una ricerca dell'utente in base al ruolo 
     * e restituisce un'istanza dell'utente trovato come parametro
     * e ritorna un intero come codice di errore
     * @param squadra
     * @param nazione
     * @param utente
     * @param ruolo
     * @return 0:utente trovato;1:non esiste un attore con quel ruolo nella societa;2: altri errori
     * 3:solo societa
     */
    public static int trovaUtenteInBaseAlRuolo(Utente utente,String squadra,String nazione,String ruolo){
        Document doc=null;
        try{
            doc = collectionSocieta.aggregate(Arrays.asList(match(and(eq("nomeSocieta", squadra), eq("nazione", nazione))), lookup("utenti", "_id", "societa", "utente"), match(eq("utente.ruolo", ruolo)), project(include("nomeSocieta", "nazione", "utente._id", "utente.nome", "utente.cognome", "utente.email")))).first();
            
        } catch(Exception e){
            return 2;
        }
        if(doc==null){
            try {
                doc=collectionSocieta.find(and(eq("nomeSocieta", squadra), eq("nazione", nazione))).first();
                Societa soc=new Societa();
                soc.setId(doc.getObjectId("_id").toString());
                soc.setNazione(doc.getString("nazione"));
                soc.setNomeSocieta(doc.getString("nomeSocieta"));
                utente.setSocieta(soc);
                return 3;
            } catch (Exception e) {
                return 2;
            }
        }
            
        Societa soc=new Societa();
        soc.setId(doc.getObjectId("_id").toString());
        soc.setNazione(doc.getString("nazione"));
        soc.setNomeSocieta(doc.getString("nomeSocieta"));
        List<Document> utenteDoc=(List<Document>) doc.get("utente");
        for(Document aux : utenteDoc){
            utente.setId(aux.getObjectId("_id").toString());
            utente.setNome(aux.getString("nome"));
            utente.setCognome(aux.getString("cognome"));
            utente.setEmail(aux.getString("email"));
            utente.setRuolo(aux.getString("ruolo"));
        }
        utente.setSocieta(soc);
        return 0;
    }
    /**
     * la funzione aggiorna il campo dei vari ruoli nella collection
     * societa
     * @param vecchioMembro
     * @param nuovoMembroEmail
     * @param controlloRuolo
     * @return 0 aggiornamento riuscito
     */
    public  static int aggiornaTeamSocieta(Utente vecchioMembro, String nuovoMembroEmail,String controlloRuolo){
        Utente nuovoMembro=null; 
        
        try {
            nuovoMembro=cercaUtenteDaEmail(nuovoMembroEmail);
        } catch (Exception ex) {
            return 2;
        }
        String ruolo=nuovoMembro.getRuolo().toLowerCase();
        if(!ruolo.equals(controlloRuolo)){
            return 1;
        }
        String idSocieta=vecchioMembro.getSocieta().getId();
        String idNuovoMembro=nuovoMembro.getId();
//        int er=eliminaUtenteDaSocieta(nuovoMembro.getRuolo().toLowerCase(), idNuovoMembro);
//        if(er==2){
//            return 3;
//        }
//        if(vecchioMembro.getId()!=null)
//            er=eliminaUtenteDaSocieta(vecchioMembro.getRuolo().toLowerCase(), vecchioMembro.getId());
//        if(er==2){
//            return 3;
//        }
        UpdateResult updateResult = null;    
        
        try{
           
            updateResult = collectionSocieta.updateOne(eq("_id",idSocieta ), set(ruolo,idNuovoMembro)  );
            
            
        } catch(Exception e){
            return 4;
        }
        try{
           
            updateResult = collectionUtenti.updateOne(eq("_id",idNuovoMembro ), set("societa",idSocieta)  );
            
            
        } catch(Exception e){
            return 4;
        }
        
        nuovoMembro.setSocieta(vecchioMembro.getSocieta());
        vecchioMembro=nuovoMembro;
        return 0;
        
    }
    
    /**
     * Funzione di utilita per eliminare utente 
     * anche dal documento della societa
     * @return 0 se cancellazione andata a buon fine, 
     * 1 l'utente non appartiene alla societa,
     * 2 altri errori
     */
    private static int eliminaUtenteDaSocieta(String ruolo, String id) {
        //Ancora non sappiamo se funziona perchè non abbiamo l'interfaccia per 
        //gli altri utenti diversi dall'admin
        UpdateResult updateResult = null;
        
        try{
           
            updateResult = collectionUtenti.updateOne(eq(ruolo, id), new Document("$unset", new Document("societa", "")));
            //ma da società ??
            
        } catch(Exception e){
            return 2;
        }
        
        if(updateResult.getModifiedCount() == 1){
            
            System.out.println(ruolo + " eliminato dalla società");
            return 0;
        }else{
            
            System.out.println(ruolo + " NON eliminato dalla società");
            return 1;
        }
          
         
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
                return eliminaUtenteDaSocieta(ruolo, id);
                
            } else{                 //Se è un admin abbiamo finito. 
                return 0;
            }
        } else //Se non è stato cancellato un utente è un errore. 
               //(Problema: se, per qualsiasi motivo, vengono cancellati più utenti ?
            return 1;
    }
    
    /**
     * Funzione che restituisce un utente a partire da un email,
     * viene utilizzata per ricercare e restituire le informazioni principali di
     * un utente con il campo oggetto società a null poichè è un'informazione
     * che non serve nella modifica profilo dell'utente
     * 
     * @param email
     * @return Se l'utente è presente restituirà un oggetto di tipo User
     * altrimenti restituisce null
     * @throws Exception 
     */
    public static Utente cercaUtenteDaEmail(String email) throws Exception
    {
        Document utenteDoc;
        Utente utente; 
        utenteDoc=(Document)collectionUtenti.find(eq("email",email)).first();
        utente=new Utente(utenteDoc.getObjectId("_id").toString(), utenteDoc.getString("nome"),
                    utenteDoc.getString("cognome"), utenteDoc.getString("email"), utenteDoc.getString("ruolo"),null);
        return utente;
    }
    public static boolean modificaProfilo(String email,String password)
    {
        return false;
    }
    
}
