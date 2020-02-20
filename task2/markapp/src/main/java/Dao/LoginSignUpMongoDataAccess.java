/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entita.InformazioniPrincipali;
import Entita.ProfiloInteresse;
import Entita.Report;
import Entita.Societa;
import Entita.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import static task2.markapp.ScreenController.utente;

/**
 * 
 * @author berto
 * Gestione delle funzioni necessarie per effettuare il  login e il signup 
 */
public class LoginSignUpMongoDataAccess extends MongoDataAccess{
    private static final String nomeCollectionUtenti="utenti";
    private static final String nomeCollectionSocieta="societa";
    
    /**
     * La funzione verifica l'esistenza dell'email nel database
     * @param email
     * @return 0 se l'email non esiste;1 se l'email esiste;2 se ci sono altri errori
     */
    private static int controllaEsistenzaUtente(String email){
        Document trovaUtente;
        try {
            trovaUtente=(Document) collection.find(eq("email",email)).first();
        } catch (Exception e) {
            return 2;
        }
       
       if(trovaUtente!=null)
           return 1;
       return 0;
    }
    /**
     * La funzione è utilizzata per registrare l'utente nel database
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @param ruolo
     * @return 0 se utente è inserito correttamente;1 email esistente;2 altri errori
     */
    public static int registraUtente(String nome,String cognome,String email,String password,String ruolo){
        try {
            apriConnessione(nomeCollectionUtenti);
        } catch (Exception e) {
            return 2;
        }
        int giaEsiste=controllaEsistenzaUtente(email);
        
        if(giaEsiste!=0){
            chiudiConnessione();
            return  giaEsiste;
        } 
        
        try {
            Document document=new Document("nome",nome).append("cognome", cognome)
                    .append("email", email).append("password", password).append("ruolo", ruolo);
            collection.insertOne(document);
            
        } catch (Exception e1) {
            chiudiConnessione();
            return 2;
        } finally{
            chiudiConnessione();
        }
        
        return 0;
    }
    
    /**
     * La funzione restituisce l'utente se la password è corretta
     * @param email
     * @param password
     * @return il Document bson dell'utente 
     */
    private static Document ricercaUtente(String email,String password){
        Document utenteDoc=null;
       
        try {
            apriConnessione(nomeCollectionUtenti);
        } catch (Exception e) {
            return null;
        }
        try{
            utenteDoc = collection.find(and(eq("email", email), eq("password", password))).projection(exclude("password")).first();
            
        } catch(Exception e){
            return null;
        }finally{
            chiudiConnessione();            
        }
        return utenteDoc;
        
    }
     /**
     * La funzione restituisce la societa dato il suo ObjectId
     * @param idSocieta
     * @return null se ci sono stati errori
     */
    private static Document ricercaSocietaDoc(ObjectId idSocieta){
        Document societaDoc=null;
       
        try {
            apriConnessione(nomeCollectionSocieta);
        } catch (Exception e) {
            return null;
        }
        try{
            societaDoc = collection.find(eq("_id", idSocieta)).first();
            
        } catch(Exception e){
            return null;
        }finally{
            chiudiConnessione();            
        }
        return societaDoc;
        
    }
    private static Societa creaSocieta(Document societaDoc) {
        Societa soc=new Societa();
        if(societaDoc.getObjectId("_id")!=null){
            soc.setId(societaDoc.getObjectId("_id").toString());
        }else{
            return null;
        } 
        if(societaDoc.getString("nomeSocieta")!=null){
            soc.setNomeSocieta(societaDoc.getString("nomeSocieta"));
        }
        if(societaDoc.getString("nazione")!=null){
            soc.setNazione(societaDoc.getString("nazione"));
        } 
        if(societaDoc.get("giocatoriPreferiti")!=null){
            List<Document> list=(List<Document>)societaDoc.get("giocatoriPreferiti");
            for(Document doc: list){
                Report report=null;
                if(doc.get("report")!=null){
                    System.out.println("Report esistente!!");
                    Document reportDoc=(Document)doc.get("report");
                    System.out.println(reportDoc.toString());
                    report=new Report(reportDoc.getObjectId("_id").toString(), reportDoc.getString("commento"), reportDoc.getInteger("rating"));
                }

                InformazioniPrincipali aux=new InformazioniPrincipali(doc.getObjectId("_id").toString(), doc.getString("nome"), doc.getString("ruoloPrincipale"), 
                                                                        doc.getString("squadra"), new Date(doc.getLong("dataNascita")), doc.getInteger("valoreMercato"), 
                                                                        doc.getString("nazionalita"), doc.getInteger("giudizioDirigenza"), doc.getInteger("giudizioAllenatore"),doc.getString("propostoDa"),report);

                soc.addGiocatorePreferito(aux);
            }
        }
        if(societaDoc.get("listaProfiliDiInteresse")!=null){
            List<Document> list=(List<Document>)societaDoc.get("listaProfiliDiInteresse");
            for(Document doc: list){
                ProfiloInteresse aux=new ProfiloInteresse(doc.getObjectId("_id").toString(),doc.getString("nomeLista"),doc.getInteger("etaMinima"),doc.getInteger("etaMassima"), doc.getString("descrizioneCaratteristiche"));

               soc.addProfiloInteresse(aux);
            }
        }
        return soc;
    }
    /**
     * La funzione è usata per loggare l'utente
     * @param email
     * @param password
     * @return 0:login è andato a buon fine; 1:email o password errate;2:altri errori
     */
    public static int login(String email,String password){
        
        Document utenteDoc=ricercaUtente(email, password);
        if(utenteDoc==null){
            return 1;
        }
        
        if(utenteDoc.getString("ruolo").equals(("admin"))){
            utente=new Utente(utenteDoc.getObjectId("_id").toString(), utenteDoc.getString("nome"),
                    utenteDoc.getString("cognome"), utenteDoc.getString("email"), utenteDoc.getString("ruolo"));
            return 0;
        } else {
            Document societaDoc=ricercaSocietaDoc(utenteDoc.getObjectId("societa"));
            
            Societa soc=null;
       
            if(societaDoc!=null){
                soc=creaSocieta(societaDoc);                
            }
            utente=new Utente(utenteDoc.getObjectId("_id").toString(), utenteDoc.getString("nome"),
                    utenteDoc.getString("cognome"), utenteDoc.getString("email"), utenteDoc.getString("ruolo"),soc);
            
            ObjectMapper mapper = new ObjectMapper();
            try{
            String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(utente);
            System.out.println(jsonInString2);
            }catch(Exception e){
                e.printStackTrace();
            }
           
            
        }
        return 0;
    }
}
