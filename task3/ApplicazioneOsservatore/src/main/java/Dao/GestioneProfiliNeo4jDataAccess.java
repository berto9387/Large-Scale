/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.Neo4jDataAccess.driver;
import Entita.Societa;
import Entita.Utente;
import java.util.HashMap;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.util.Pair;

/**
 *
 * @author Gianluca
 */
public class GestioneProfiliNeo4jDataAccess extends Neo4jDataAccess {
    /**
     * 
     * @param email
     * @param id
     * @return 
     */
    public static int eliminaAccount(final String email,final String id){
        try(Session session=driver.session())
        {
            
            return session.readTransaction(new TransactionWork<Integer>()
            {
                @Override
                public Integer execute(Transaction tx)
                {
                   return transactionEliminaAccount(tx,email,id);
                }
            }); 
        }         
    }
    /**
     * 
     * @param tx
     * @param email
     * @param id
     * @return 
     */
    public static int transactionEliminaAccount(Transaction tx,String email, String id){
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);
        parameters.put("<id>", id);
        StatementResult result=tx.run("MATCH(a:Utente) WHERE a.email=$email AND a.<id>=$<id>"+
                 " OPTIONAL MATCH (a)-[r]->()"+
                  "DELETE a,r",parameters);

        return 0;
    }
    /**
     * 
     * @param email
     *@return Utente se vi è all'interno el database un elemento Utente con email
     * uguale a quella del parametro della funzione.
     * null se non è presente nel database un utente con email corrispondente
     * al parametro attuale.
     */
     public static Utente cercaUtenteDaEmail(final String email) 
    {
         try(Session session=driver.session())
        {
            
            return session.readTransaction(new TransactionWork<Utente>()
            {
                @Override
                public Utente execute(Transaction tx)
                {
                   return transactionCercaUtenteDaEmail(tx,email);
                }
            });
        }    
    } 
     /**
      * Funzione che effettua una query di ricerca di un nodo che ha il parametro
      * email uguale  quello cercato
      * 
      * @param tx
      * @param email
      * @return Utente se vi è all'interno el database un elemento Utente con email
      * uguale a quella del parametro della funzione.
      * null se non è presente nel database un utente con email corrispondente
      * al parametro attuale.
      */
    public static Utente transactionCercaUtenteDaEmail(Transaction tx,String email)
    {   
        
        Utente utente=null;
        Societa societa = null;
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);
        String nomeSocieta="NA";
        String nazione="NA";
        String idSocieta="NA";
        String nome="NA";
        String cognome="NA";
        String idUtente ="NA";
        String ruolo="NA";        
        StatementResult result=tx.run("MATCH(a:Utente) WHERE a.email=$email"+
                 " OPTIONAL MATCH (a)-[r:Tesserato_per]->(s:Societa) OPTIONAL MATCH (so:Societa) WHERE (so)<-[r]-(a)"+
                  "RETURN a so",parameters);
        if(!result.hasNext())
            return null;
        Record record=result.single();
        List<Pair<String,Value>> values = record.fields();
        for (Pair<String,Value> nameValue: values) {
            if ("a".equals(nameValue.key())) { 
                Value value = nameValue.value();
                nome = value.get("nome",nome);
                cognome= value.get("cognome",cognome);
                idUtente = value.get("<id>",idUtente);
                ruolo = value.get("ruolo",ruolo);
            }
            if ("so".equals(nameValue.key())) { 
                Value value = nameValue.value();
                nomeSocieta=record.get("nomeSocieta",nomeSocieta);
                nazione=record.get("nazione",nazione);
                idSocieta=record.get("id",idSocieta);
            }         
        }
        if(idSocieta!="NA")
        {
            societa= new Societa(idSocieta, nomeSocieta,nazione, idUtente);
        }   
        utente= new Utente(idUtente,nome,cognome,email,ruolo,societa);
        return utente;
    }
}
