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

/**
 *
 * @author berto
 * La classe gestisce tutto ciò che riguarda la gestione dei profili
 * e l'aggiornamento del database
 * 
 */
public class GestioneProfiliMongoDataAccess extends MongoDataAccess{
    private static final String nomeCollectionSocieta="societa"; 
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
}
