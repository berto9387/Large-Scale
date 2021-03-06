/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.MongoDataAccess.collectionStatistiche;
import Model.ValoriStatisticheIstogramma;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.TransactionBody;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import static com.mongodb.client.model.Updates.unset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author berto
 */
public class aggiornaDatabase extends MongoDataAccess{
    public static int aggiorna(String calciatore){
        Document calciatoreDoc = Document.parse(calciatore);
        String id=calciatoreDoc.getString("_id");
        ClientSession clientSession=mongoClient.startSession();
        TransactionBody txnBody = (TransactionBody<String>) () -> {
            Bson filter = eq("_id", id);
            Bson filter1 = eq("calciatore", id);
            collectionCalciatore.deleteMany(clientSession, filter);
            collectionStatistiche.deleteMany(clientSession, filter1);
            collectionCalciatore.insertOne(clientSession, calciatoreDoc);
            
            if(calciatoreDoc.get("statistiche") != null){
                List<Document> statistiche = (List<Document>)calciatoreDoc.get("statistiche");
                List<Document> auxs=new ArrayList();
                for(int i=0;i<statistiche.size();i++){
                    Document aux=new Document();
                    aux.append("calciatore", calciatoreDoc.getString("_id"));
                    aux.append("posizionePrincipale", calciatoreDoc.getString("posizionePrincipale"));
                    aux.append("stagione", statistiche.get(i).getString("stagione"));
                    aux.append("competizione", statistiche.get(i).getString("competizione"));
                    aux.append("societa", statistiche.get(i).getString("societa"));
                    aux.append("presenze", statistiche.get(i).getInteger("presenze").doubleValue());
                    try {
                        aux.append("puntiPartita", statistiche.get(i).getInteger("puntiPartita").doubleValue());
                    } catch (Exception e) {
                        aux.append("puntiPartita", statistiche.get(i).getDouble("puntiPartita"));
                    }
                    
                    
                    if(!calciatoreDoc.getString("posizionePrincipale").equals("Portiere")){
                        aux.append("assist", statistiche.get(i).getInteger("assist").doubleValue());
                        aux.append("reti", statistiche.get(i).getInteger("reti").doubleValue());
                    } else{
                        aux.append("partiteNoGoal", statistiche.get(i).getInteger("partiteNoGol").doubleValue());
                        aux.append("retiSubite", statistiche.get(i).getInteger("retiSubite").doubleValue()); 
                    }
                    
                    aux.append("doppieAmmonizioni", statistiche.get(i).getInteger("doppieAmmonizioni").doubleValue());
                    aux.append("ammonizione", statistiche.get(i).getInteger("ammonizioni").doubleValue());
                    aux.append("espulsioni", statistiche.get(i).getInteger("espulsioni").doubleValue());
                    aux.append("minutiGiocati", statistiche.get(i).getInteger("minutiGiocati").doubleValue());
                     
                    auxs.add(aux);
                }
                collectionStatistiche.insertMany(clientSession, auxs);
            }
            aggiornaGiocatorePreferito(clientSession,calciatoreDoc);
            
            return "aggiorna campi!";
        };
    try {
        clientSession.withTransaction(txnBody);
    } catch (RuntimeException e) {
        e.printStackTrace();
        return 1;
    } finally {
        clientSession.close();
    }
        return 0;    
    }
    
    private static void aggiornaGiocatorePreferito(ClientSession clientSession,Document calciatoreDoc)
    {
        Document elementoSet=null;
        try {
            elementoSet = new Document().append("giocatoriPreferiti.$[elem].valoreMercato", calciatoreDoc.getInteger("valoreAttuale"));
        } catch (Exception e) {
            elementoSet = new Document().append("giocatoriPreferiti.$[elem].valoreMercato", calciatoreDoc.getDouble("valoreAttuale").intValue());

        }
        Document documentSet = new Document().append("$set",elementoSet);
        Document filtroRicerca = new Document("giocatoriPreferiti",new Document().append("$exists", "true"));
        UpdateOptions opzioni = new UpdateOptions().arrayFilters(
           Collections.singletonList(Filters.eq("elem._id",calciatoreDoc.getString("_id"))));
        collectionSocieta.updateMany(clientSession, filtroRicerca, documentSet,opzioni);
    }
}
