/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.Neo4jDataAccess.driver;
import Model.InformazioniOsservatore;
import Entita.Societa;
import Entita.Utente;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.util.Pair;
import static Dao.LoginSignUpNeo4jDataAccess.SocietaUtente;
/**
 *
 * @author berto
 */
public class RicercaOsservatoriNeo4jDataAccess {
    /**
     * Restituisce una lista di osservatori seguiti
     * cercaOsservatoriSeguiti
     * @param email
     * @return 
     */
    public static List<InformazioniOsservatore> cercaOsservatoriSeguiti(final String email)
    {
        
        try(Session session=driver.session())
        {
            return session.writeTransaction((Transaction tx) -> matchOsservatoriSeguiti(tx,email));
        }
    
    
    }
    /**
     * 
     * @param tx
     * @param email
     * @param password
     * @return 0 se viene trovato un utente che ha la password ed email passati
     * a parametro alla funzione
     * @return 1 se non esiste nessun utente che abbia email e password dati da 
     * parametro
     */
    private static List<InformazioniOsservatore> matchOsservatoriSeguiti(Transaction tx,String email)
    {
        List<InformazioniOsservatore> elems=new ArrayList<>();
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);      
        StatementResult result=tx.run("match (os1:Utente)-[:SEGUE]->(os)-[r:INTERESSATO]->(ca) where os1.email=$email return os,count(r) as cont",parameters);
        while(result.hasNext()){
            Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            InformazioniOsservatore info=new InformazioniOsservatore();
            for (Pair<String,Value> nameValue: values) {
                if ("os".equals(nameValue.key())) { 
                    Value value = nameValue.value();
                    info.setIdOsservatore(Long.toString(value.asNode().id()));
                    info.setCognome(value.get("cognome").asString());
                    info.setNome(value.get("nome").asString());
                    info.setSeguito(Boolean.TRUE);
                    Societa societa=SocietaUtente(tx,value.get("email").asString(),info.getIdOsservatore());
                    if(societa!=null)
                        info.setSquadra(societa.getNomeSocieta());                    
                }
                if("cont".equals(nameValue.key())){
                   Value value = nameValue.value(); 
                   info.setCalciatoriSeguiti(value.toString());
                }
            }
            elems.add(info);
        }
        return elems;      
    
    }
}
