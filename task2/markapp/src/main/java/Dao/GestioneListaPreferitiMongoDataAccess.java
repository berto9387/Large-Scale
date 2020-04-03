/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entita.Calciatore;
import Entita.Utente;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.UpdateResult;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author tony_
 */
public class GestioneListaPreferitiMongoDataAccess extends MongoDataAccess{

    /**
     * 
     * @param calciatore
     * @param utente
     * @return 0 aggiunto correttamente, 1 già presente il lista, 2 altri errori
     */
    public static int aggiungiGiocatoreLista(Calciatore calciatore, Utente utente) {
        //Per prima cosa controlliamo che il calciatore non sia stato già inserito nella
        //lista preferiti della squadra
        try{
            
            Document doc = collectionSocieta.find(and(eq("_id", utente.getSocieta().getId()),//ObjectId
                                                        eq("giocatoriPreferiti._id", calciatore.getIdCalciatore()))).first();
            if(doc != null){
                //Caso in cui esiste già il giocatore nella lista
                return 1;
            }
            
            //Altrimenti possiamo inserirlo
            Date timestamp = new Date(); //Mi serve per aggiungere il timestamp d'aggiunta
            
            //Creo il documento da inserire
            Document nuovoPreferitoDoc = new Document("_id", calciatore.getIdCalciatore())
                                                    .append("timeStampAggiunto", timestamp)
                                                    .append("linkFoto", calciatore.getLinkFoto())
                                                    .append("nome", calciatore.getNome())
                                                    .append("nazionalita", calciatore.getNazionalita())
                                                    .append("squadra", calciatore.getSquadra())
                                                    .append("posizione", calciatore.getRuoloPrincipale())
                                                    .append("valoreMercato", calciatore.getValoreMercato())
                                                    .append("dataNascita", calciatore.getDataNascita())
                                                    .append("propostoDa", utente.getRuolo())
                                                    .append("giudizioAllenatore", 2)
                                                    .append("giudizioDirigenza", 2);
            
            //Inserisco il documento
            collectionSocieta.updateOne(eq("_id",utente.getSocieta().getId()), //ObjectId
                                        Updates.addToSet("giocatoriPreferiti", nuovoPreferitoDoc));
            
        } catch (Exception e ){
            e.printStackTrace();
            return 2; 
        }
        
        return 0;
    }

    public static int valutaGiocatore(Calciatore calciatore, Utente utente, int voto) {
        try{
            if(utente.getRuolo().equals("allenatore") || utente.getRuolo().equals("Allenatore")){
                
                UpdateResult updateResult = collectionSocieta.updateOne(and(eq("_id",utente.getSocieta().getId()), eq("giocatoriPreferiti._id", calciatore.getIdCalciatore())),
                                                                        set("giocatoriPreferiti.$.giudizioAllenatore", voto));
                
                if(updateResult.getModifiedCount() == 0){
                    return 1;
                }
            }
            if(utente.getRuolo().equals("amministratore delegato")){
                
                UpdateResult updateResult = collectionSocieta.updateOne(and(eq("_id",utente.getSocieta().getId()),//objectid
                                                                            eq("giocatoriPreferiti._id", calciatore.getIdCalciatore())),
                                                                        set("giocatoriPreferiti.$.giudizioDirigenza", voto));
                
                if(updateResult.getModifiedCount() == 0){
                    return 1;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return 2;
        }
        return 0;
    }
    
}
