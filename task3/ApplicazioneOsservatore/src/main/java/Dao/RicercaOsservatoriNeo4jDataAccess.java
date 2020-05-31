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
import static Dao.Neo4jDataAccess.UtentePresente;
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
                    info.setIdOsservatore(value.asNode().id());
                    info.setCognome(value.get("cognome").asString());
                    info.setNome(value.get("nome").asString());
                    info.setSeguito(Boolean.TRUE);
                    Societa societa=SocietaUtente(tx,value.get("email").asString(),Long.toString(info.getIdOsservatore()));
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

    public static int noFollow(final String utente,final Long idOsservatore) {
        try(Session session=driver.session())
        {
            return session.writeTransaction((Transaction tx) -> togliFollow(tx,utente,idOsservatore));
        }
    }

    public static int follow(final String utente,final Long idOsservatore) {
        try(Session session=driver.session())
        {
            return session.writeTransaction((Transaction tx) -> aggiungiFollow(tx,utente,idOsservatore));
        }
    }

    private static Integer togliFollow(Transaction tx, String utente, Long osservatore) {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("os1",utente);
        parameters.put("os2",osservatore);        
        StatementResult result=tx.run("MATCH (os1)-[r:SEGUE]->(os2) where os1.email=$os1 and id(os2)=$os2 delete r",parameters);
        return 0;
        
        
    }

    private static Integer aggiungiFollow(Transaction tx, String utente,Long osservatore) {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("os1",utente);
        parameters.put("os2",osservatore);        
        StatementResult result=tx.run("match (os1:Utente),(os2:Utente) where os1.email=$os1 and id(os2)=$os2 merge (os1)-[r:SEGUE]->(os2)",parameters);
        return 0;
    }

    public static List<InformazioniOsservatore> cercaOsservatori(final String nome, final String squadra) {
        if(!squadra.isEmpty()){
            try(Session session=driver.session())
            {
                return session.writeTransaction((Transaction tx) -> matchSquadra(tx,squadra));
            }
        } else{
           try(Session session=driver.session())
            {
                return session.writeTransaction((Transaction tx) -> matchNome(tx,nome));
            } 
        }
    }

    private static List<InformazioniOsservatore> matchSquadra(Transaction tx, String squadra) {
        List<InformazioniOsservatore> elems=new ArrayList<>();
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("squadra",squadra);      
        StatementResult result=tx.run("match (er)<-[r:INTERESSATO]-(os:Utente)-[:Tesserato_per]->(so) where so.nomeSocieta=$squadra return os,count(r) as cont",parameters);
        while(result.hasNext()){
            Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            InformazioniOsservatore info=new InformazioniOsservatore();
            for (Pair<String,Value> nameValue: values) {
                if ("os".equals(nameValue.key())) { 
                    Value value = nameValue.value();
                    info.setIdOsservatore(value.asNode().id());
                    info.setCognome(value.get("cognome").asString());
                    info.setNome(value.get("nome").asString());
                    info.setSeguito(Boolean.FALSE);
                    Societa societa=SocietaUtente(tx,value.get("email").asString(),Long.toString(info.getIdOsservatore()));
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

    private static List<InformazioniOsservatore> matchNome(Transaction tx, String nome) {
        String[] aux=nome.split(" ");
        String appoggio="";
        List<InformazioniOsservatore> elems=new ArrayList<>();
        if(aux.length<2)
            return elems;
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("nome",aux[0]);
        parameters.put("cognome",aux[1]);
        StatementResult result=tx.run("match (er)<-[r:INTERESSATO]-(os:Utente) where os.nome=$nome or os.cognome=$cognome return os,count(r) as cont",parameters);
        while(result.hasNext()){
            Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            InformazioniOsservatore info=new InformazioniOsservatore();
            for (Pair<String,Value> nameValue: values) {
                if ("os".equals(nameValue.key())) { 
                    Value value = nameValue.value();
                    info.setIdOsservatore(value.asNode().id());
                    info.setCognome(value.get("cognome").asString());
                    info.setNome(value.get("nome").asString());
                    info.setSeguito(Boolean.FALSE);
                    Societa societa=SocietaUtente(tx,value.get("email").asString(),Long.toString(info.getIdOsservatore()));
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
