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
import org.bson.conversions.Bson;
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
     * la società ha una lista di profili di interesse dell'attuale allenatore restituisce 
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
        ObjectId ObjectIdAllenatore;
        societaDoc=(Document)collectionSocieta.find(eq("_id",idSocieta)).first();//ObjectId
        if(!societaDoc.containsKey("listaProfiliDiInteresse"))
            return new ArrayList<>();
        
        listaProfiliInteresseDocument=(ArrayList<Document>)societaDoc.get("listaProfiliDiInteresse");
        prossimoID=calcoloProssimoId(listaProfiliInteresseDocument);
        
        ObjectIdAllenatore=societaDoc.getObjectId("allenatore");
        if(ObjectIdAllenatore==null)
                return new ArrayList<>(); 
        idAllenatoreSocieta=ObjectIdAllenatore.toHexString();
        for(Document doc:listaProfiliInteresseDocument){
            String idAllenatoreProfiloInteresse=doc.getString("idAllenatore");
            if(idAllenatoreProfiloInteresse.equals(idAllenatoreSocieta)){
                String id=doc.getString("_id");
                String ruolo=doc.getString("ruolo");
                int etaMinima=doc.getInteger("etaMinima");
                int etaMassima=doc.getInteger("etaMassima");
                String descrizioneCaratteristiche=doc.getString("descrizioneCaratteristiche");
                Date timeStampAggiunto=doc.getDate("timeStampAggiunto");
                ProfiloInteresse profiloAusilio=new ProfiloInteresse(id,ruolo,etaMinima,etaMassima,descrizioneCaratteristiche,
                        idAllenatoreProfiloInteresse,timeStampAggiunto);
                listaProfiloInteresse.add(profiloAusilio);
                
            }
        }
        return listaProfiloInteresse;
    }
    /**
     * Funzione che permette di restituire una lista di profili di interesse
     * sottoforma di oggetti beans.
     * che appartengono ad una societa redati dall'attuale allenatore.
     * @param idSocieta
     * @return una lista vuota se la societa non ha una lista di profili di se invece
     * la società ha una lista di oggetti beans di profili di interesse dell'attuale allenatore restituisce 
     * una lista di oggetti ProfiloInteresse.
     * 
     */
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
    /**
     * La funzione permette di calcolare l'id del prossimo profilo d'interesse
     * @param listaProfiliInteresseDoc
     * @return un intero che indica l'id della prossimma scheda profilo d'interese
     */
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
    /**
     * Funzione che aggiunge una nuova scheda profilo d'interesse alla lista 
     * della società a cui corrisponde l'idSocieta passato a parametro,la scheda profilo
     * verrà creata e caricata sul database a partire dai parametri passati alla
     * funzione
     * @param idSocieta
     * @param idAllenatore
     * @param ruolo
     * @param etaMinima
     * @param etaMassima
     * @param descrizioneCaratteristiche
     * @return l'id corrispondente al nuovo profilo d'interesse caricato nel database.
     */
    public static String aggiungiAListaProfiliInteresse(String idSocieta,String idAllenatore,String ruolo,int etaMinima,
            int etaMassima,String descrizioneCaratteristiche){
        
        String _id=Integer.toString(prossimoID);
        
        Document profiloInteresse=new Document("_id",_id)
                .append("idAllenatore",idAllenatore)
                .append("ruolo",ruolo)
                .append("etaMinima",etaMinima)
                .append("etaMassima",etaMassima)
                .append("descrizioneCaratteristiche",descrizioneCaratteristiche)
                .append("timeStampAggiunto",new Date());
        UpdateResult updateOne = collectionSocieta.updateOne(eq("_id",idSocieta),//ObjectId
                Updates.addToSet("listaProfiliDiInteresse",profiloInteresse));
        ++prossimoID;
        return _id;   
    }
    
    public static void modificaListaProfiliInteresse(String idSocieta,String idProfiloInteresse,String ruolo,int etaMinima,
            int etaMassima,String descrizioneCaratteristiche){
      
        Bson filter =and(eq("_id",idSocieta),eq("listaProfiliDiInteresse._id",idProfiloInteresse)); //ObjectId
        Document profiloInteresseAggiornato=new Document("listaProfiliDiInteresse.$.ruolo",ruolo)
              .append("listaProfiliDiInteresse.$.etaMinima",etaMinima)
              .append("listaProfiliDiInteresse.$.etaMassima",etaMassima)
              .append("listaProfiliDiInteresse.$.descrizioneCaratteristiche", descrizioneCaratteristiche)
              .append("listaProfiliDiInteresse.$.timeStampAggiunto", new Date());
        Document setDocument=new Document("$set",profiloInteresseAggiornato);
        collectionSocieta.updateOne(filter,setDocument);
        
    }
    /**
     * Funzione che passato come parametro un id di una societa e l'id di un profilo d'interesse
     * elimini dalla lista dei profili d'interesse la scheda d'id corrispondente.
     * @param idSocieta
     * @param idProfilo 
     */
    public static void eliminaProfiloInteresse(String idSocieta,String idProfilo){
    
        Bson filter = eq("_id",idSocieta);//ObjectId
        Document idProfiloInteresseDaEliminare = new Document("_id",idProfilo); 
        Document fieldDelete = new Document("listaProfiliDiInteresse",idProfiloInteresseDaEliminare);
        Document update = new Document("$pull",fieldDelete);
        collectionSocieta.updateOne(filter, update);
    }
    
}
