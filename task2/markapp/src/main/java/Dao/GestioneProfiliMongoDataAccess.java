/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

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
    
    private static final String nomeCollectionUtenti = "utenti";
    
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

    
}
