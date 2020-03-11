/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.ValoriStatisticheDiagrammaTorta;
import Model.ValoriStatisticheIstogramma;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

/**
 *
 * @author berto
 */
public class StatisticheMongoDataAccess extends MongoDataAccess{
     public static List<ValoriStatisticheIstogramma> getStatistichePerIstogramma(String posizionePrincipale,
            String stagione,String competizione,String idCalciatore,String tipoStatistica){
        ArrayList<ValoriStatisticheIstogramma> listaStatistiche = new ArrayList();
        String campoStatistica= "$" + tipoStatistica;
     MongoCursor <Document> cursore = collectionStatistiche.aggregate(Arrays.asList(
            Aggregates.match(
                    Filters.and(     
                       Filters.eq("posizionePrincipale",posizionePrincipale),
                       Filters.eq("stagione",stagione),
                       Filters.eq("competizione",competizione))),
                Aggregates.group(campoStatistica, Accumulators.sum("numeroGiocatori",1)))
                        
                ).iterator();
     try{
         
        while(cursore.hasNext()){
            Document aux = cursore.next();
            System.out.println(aux.toJson());
            int numeroGiocatori= aux.getInteger("numeroGiocatori");
            double valoreStatistica = 0;
            try {
              valoreStatistica= aux.getDouble("_id");  
              listaStatistiche.add(new ValoriStatisticheIstogramma(numeroGiocatori,(int)valoreStatistica));
            } catch (Exception e) {
                //continue;
            }
            
            
            
        }
     }
    catch(Exception e){
        e.printStackTrace();
    }  
    System.out.println(listaStatistiche.size());
    return listaStatistiche;
    }
    
    public static ValoriStatisticheDiagrammaTorta statisticaAgregataSocieta(String societa,String stagione,String idCalciatore) throws Exception{
        
        Document addDoc= new Document("$add", Arrays.asList("$ammonizione", "$espulsioni"));
        Document statsDoc = collectionStatistiche.aggregate(Arrays.asList(
          Aggregates.match(
            Filters.and(
              Filters.eq("societa", societa),
              Filters.eq("stagione", stagione),
              Filters.ne("calciatore",idCalciatore))),
          Aggregates.group("$societa", Accumulators.sum("numeroReti","$reti"),
                  Accumulators.sum("numeroAssist", "$assist"),
                  Accumulators.sum("numeroCartellini", addDoc),
                  Accumulators.sum("numeroGoalSubiti", "$retiSubite")))
        
        ).first();
        double numeroReti;
        double numeroAssist;
        double numeroCartellini;
        double numeroGoalSubiti;
        int aux;
        try {
            numeroReti = statsDoc.getDouble("numeroReti");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroReti");
            numeroReti=(double)aux;
        }
        try {
            numeroAssist =statsDoc.getDouble("numeroAssist");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroAssist");
            numeroAssist=(double)aux;
        }
        try {
            numeroCartellini = statsDoc.getDouble("numeroCartellini");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroCartellini");
            numeroCartellini=(double)aux;
        }
        try {
            numeroGoalSubiti = statsDoc.getDouble("numeroGoalSubiti");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroGoalSubiti");
            numeroGoalSubiti=(double)aux;
        }
        
        return new ValoriStatisticheDiagrammaTorta(numeroReti,numeroAssist,numeroCartellini,numeroGoalSubiti);
    } 
     
    public static ValoriStatisticheDiagrammaTorta statisticaAgregataGiocatoreSocieta(String societa,String stagione,String idCalciatore) throws Exception{
        Document addDoc= new Document("$add", Arrays.asList("$ammonizione", "$espulsioni"));
        Document statsDoc = collectionStatistiche.aggregate(Arrays.asList(
          Aggregates.match(
            Filters.and(
              Filters.eq("societa", societa),      
              Filters.eq("stagione", stagione),
              Filters.eq("calciatore",idCalciatore))),
          Aggregates.group("$calciatore", Accumulators.sum("numeroReti","$reti"),
                  Accumulators.sum("numeroAssist", "$assist"),
                  Accumulators.sum("numeroCartellini", addDoc),
                  Accumulators.sum("numeroGoalSubiti", "$retiSubite")))
        
        ).first();
        double numeroReti;
        double numeroAssist;
        double numeroCartellini;
        double numeroGoalSubiti;
        int aux;
        try {
            numeroReti = statsDoc.getDouble("numeroReti");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroReti");
            numeroReti=(double)aux;
        }
        try {
            numeroAssist =statsDoc.getDouble("numeroAssist");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroAssist");
            numeroAssist=(double)aux;
        }
        try {
            numeroCartellini = statsDoc.getDouble("numeroCartellini");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroCartellini");
            numeroCartellini=(double)aux;
        }
        try {
            numeroGoalSubiti = statsDoc.getDouble("numeroGoalSubiti");
        } catch (Exception e) {
            aux=statsDoc.getInteger("numeroGoalSubiti");
            numeroGoalSubiti=(double)aux;
        }
        return new ValoriStatisticheDiagrammaTorta(numeroReti,numeroAssist,numeroCartellini,numeroGoalSubiti);
        
    }     
}
    

