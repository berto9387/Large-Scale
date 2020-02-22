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
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
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
     * La funzione restituisce l'amministratore di squadra 
     * @param squadra
     * @param nazione
     * @return 0:amministratore di squadra trovato;1:non esistel'amministratore di squadra per la squadra;2: altri errori
     */
    public static int aggiornaAmministratoreDiSquadra(Utente utente,String squadra,String nazione){
        Document doc=null;
        
        try {
            apriConnessione(nomeCollectionSocieta);
        } catch (Exception e) {
            return 2;
        }
        try{
            doc = collection.aggregate(Arrays.asList(match(and(eq("nomeSocieta", squadra), eq("nazione", nazione))), lookup("utenti", "_id", "societa", "utente"), match(eq("utente.ruolo", "Amministratore di squadra")), project(include("nomeSocieta", "nazione", "utente._id", "utente.nome", "utente.cognome", "utente.email")))).first();
            
        } catch(Exception e){
            return 2;
        }
        if(doc==null)
            return 1;
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
        }
        utente.setSocieta(soc);
        return 0;
    }
    
    /**
     * Funzione di utilita per eliminare utente 
     * anche dal documento della societa
     * @return 0 se cancellazione andata a buon fine, 
     * 1 altrimenti
     */
    private static int eliminaUtenteDaSocieta(String ruolo, String id) {
        //Ancora non sappiamo se funziona perchè non abbiamo l'interfaccia per 
        //gli altri utenti diversi dall'admin
        UpdateResult updateResult = null;
        
        try{
           
            updateResult = collection.updateOne(eq(ruolo, id), new Document("$unset", new Document(ruolo, "")));
        } catch(Exception e){
            return 1;
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
        
        try {
            apriConnessione(nomeCollectionUtenti);
        } catch (Exception e) {
            return 2;
        }
        DeleteResult deleteResult = null;
        //Per prima cosa va cancellato l'utente dalla collection "utenti"
        try{
            deleteResult = collection.deleteOne(eq("email", email));
            
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
    
  /*  private Utente cercaUtenteDaEmail(String email) throws Exception
    {
        apriConnessione(nomeCollectionUtenti);
        Document utenteDoc;
        Utente utente;
        
        utenteDoc=(Document)collection.find(eq("email",email)).first();
        utente=new Utente(utenteDoc.getObjectId("_id").toString(), utenteDoc.getString("nome"),
                    utenteDoc.getString("cognome"), utenteDoc.getString("email"), utenteDoc.getString("ruolo"),soc);
    }
    
    */
}
