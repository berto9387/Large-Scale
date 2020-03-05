/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.MongoDataAccess.collectionSocieta;
import Entita.Calciatore;
import Entita.Report;
import Entita.Utente;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.unset;
import com.mongodb.client.result.UpdateResult;
import java.util.Arrays;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author berto
 * gestione inserimento o modifica del report
 */
public class GestioneReportMongoDataAccess extends MongoDataAccess{

    public static int aggiungiReport(Report report, Utente utente, Calciatore calciatore) {
        
        try{
            Date timestamp = new Date(); //Mi serve per aggiungere il timestamp d'aggiunta

            Document reportDoc = new Document("_id", new ObjectId()) 
                                                .append("timeStampAggiunto", timestamp)
                                                .append("commento", report.getCommento())
                                                .append("rating", report.getRating());

            UpdateResult updateResult = collectionSocieta.updateOne(and(eq("_id", new ObjectId(utente.getSocieta().getId())), eq("giocatoriPreferiti._id", calciatore.getIdCalciatore())),
                                                                            set("giocatoriPreferiti.$.reportOsservatore", reportDoc));

            if(updateResult.getModifiedCount() != 1){
                return 1;
            }
        } catch(Exception e){
            e.printStackTrace();
            return 2;
        }
        
        return 0;
        
    }

    
    /**
     * 
     * @param utente
     * @param calciatore
     * @param report
     * @return 0 se c'era già un report (e in report ci va il report),
     * 1 se report non c'era
     * 2 altri errori
     */
    public static Report cercaReport(Utente utente, Calciatore calciatore) {
        
        Report report = null;
        try{
            Document doc = collectionSocieta.aggregate(Arrays.asList(match(eq("_id", new ObjectId(utente.getSocieta().getId()))),
                                                        unwind("$giocatoriPreferiti"), match(eq("giocatoriPreferiti._id", calciatore.getIdCalciatore())))).first();

            Document giocatoreDoc = (Document) doc.get("giocatoriPreferiti");
            if(giocatoreDoc.get("reportOsservatore") != null){
                Document reportDoc = (Document) giocatoreDoc.get("reportOsservatore");
                report = new Report(reportDoc.getDate("timeStampAggiunto"),
                                    reportDoc.getString("commento"),
                                    reportDoc.getInteger("rating"));
                
                
            }
        } catch(Exception e){
            e.printStackTrace();
            
        }finally{
            return report;
        }
    }

    
    /**
     * 
     * @param report
     * @param utente
     * @param calciatore
     * @return 0 se report rimosso correttamente, 
     * 1 o 2 altri errori
     */
    public static int eliminaReport(Report report, Utente utente, Calciatore calciatore) {
        
        try{
            UpdateResult updateResult = collectionSocieta.updateOne(and(eq("_id", new ObjectId(utente.getSocieta().getId())), eq("giocatoriPreferiti._id", calciatore.getIdCalciatore())),
                                                                                unset("giocatoriPreferiti.$.reportOsservatore"));
            if(updateResult.getModifiedCount() != 1){
                return 1;
            }
            
        } catch(Exception e){
            e.printStackTrace();
            return 2;
        }
        
        return 0;
    }
    
}
