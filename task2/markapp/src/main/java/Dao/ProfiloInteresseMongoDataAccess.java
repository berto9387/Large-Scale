/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entita.ProfiloInteresse;
import Model.ProfiloInteresseBeans;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.*;
import org.bson.*;
import org.bson.types.ObjectId;

/**
 *
 * @author Gianluca
 * gestisce le funzioni di modifica del profilo d'interesse
 */
public class ProfiloInteresseMongoDataAccess extends MongoDataAccess{
    /**
     * Funzione che permette di restituire una lista di profili di interesse 
     * che appartengono ad una societa redati dall'attuale allenatore.
     * @param idSocieta
     * @return una lista vuota se la societa non ha una lista di profili di se invece
     * la società ha una lista di interesse dell'attuale allenatore restituisce 
     * una lista di oggetti ProfiloInteresse.
     * 
     */
    private static int prossimoID=0;
    public static ArrayList<ProfiloInteresse> ottieniListaProfiliInteresse(String idSocieta)
    {
        ArrayList<Document> listaProfiliInteresseDocument;
        ArrayList<ProfiloInteresse>listaProfiloInteresse=new ArrayList<>();
        Document societaDoc;
        String idAllenatoreSocieta;
        societaDoc=(Document)collectionSocieta.find(eq("_id",idSocieta)).first();
        if(societaDoc.get("listaProfiliDiInteresse")==null)
            return new ArrayList<>();
        
        listaProfiliInteresseDocument=(ArrayList<Document>)societaDoc.get("listaProfiliDiInteresse");
        prossimoID=calcoloProssimoId(listaProfiliInteresseDocument);
        idAllenatoreSocieta=societaDoc.getString("allenatore");
        for(Document doc:listaProfiliInteresseDocument){
            String idAllenatoreProfiloInteresse=doc.getString("idAllenatore");
            if(idAllenatoreProfiloInteresse.equals(idAllenatoreSocieta)){
                String id=doc.getString("_id");
                String ruolo=doc.getString("ruolo");
                int etaMinima=doc.getInteger("etaMinima");
                int etaMassima=doc.getInteger("etaMassima");
                String descrizioneCaratteristiche=doc.getString("descrizioneCaratteristiche");
                Date timeStampAggiunto=new Date(Long.getLong(doc.getString("timeStampAggiunto")));
                ProfiloInteresse profiloAusilio=new ProfiloInteresse(id,ruolo,etaMinima,etaMassima,descrizioneCaratteristiche,
                        idAllenatoreProfiloInteresse,timeStampAggiunto);
                listaProfiloInteresse.add(profiloAusilio);
                
            }
        }
        return listaProfiloInteresse;
    }
    
    public static ArrayList<ProfiloInteresseBeans>ottieniListaProfiliInteresseBeans(String idSocieta){
        ArrayList<ProfiloInteresseBeans>listaProfiliInteresseBeans=new ArrayList<>();
        ArrayList<ProfiloInteresse>listaProfiliInteresse=ottieniListaProfiliInteresse(idSocieta);
        if(listaProfiliInteresse.isEmpty())
            return new ArrayList<>();
        for(ProfiloInteresse profilo:listaProfiliInteresse){
            ProfiloInteresseBeans profiloAppoggio=new ProfiloInteresseBeans(profilo);
            listaProfiliInteresseBeans.add(profiloAppoggio);
        }
        return listaProfiliInteresseBeans;
    }
    
    private static int calcoloProssimoId(ArrayList<Document> listaProfiliInteresseDoc){
    
        int maxID=0;
        String id;
        int idIntero;
        for(Document doc:listaProfiliInteresseDoc){
            id=doc.getString("_id");
            idIntero=Integer.parseInt(id);
            if(maxID<idIntero){
                maxID=idIntero;
            }
        }
        return (maxID+1);
    }
    
    public static void aggiungiAListaProfiliInteresse(String idSocieta,String ruolo,int etaMinima,
            int etaMassima,String descrizioneCaratteristiche){
        
        String _id=Integer.toString(prossimoID);
        Document profiloInteresse=new Document("_id",new ObjectId(_id))
                .append("ruolo",ruolo)
                .append("etaMinima",etaMinima)
                .append("etaMassima",etaMassima)
                .append("descrizioneCaratteristiche",descrizioneCaratteristiche)
                .append("timeStampAggiunto",System.currentTimeMillis());
        UpdateResult updateOne = collectionSocieta.updateOne(eq("_id",idSocieta),
                Updates.addToSet("listaProfiliDiInteresse",profiloInteresse));
        ++prossimoID;
        
    }
    
    public static void modificaListaProfiliInteresse(String idSocieta,int etaMinima,
            int etaMassima,String descrizioneCaratteristiche){
        
        
    }
    
}
