/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entita.Societa;
import Entita.Utente;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import java.util.HashMap;
import javax.naming.spi.DirStateFactory.Result;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.Value;
import static org.neo4j.driver.v1.Values.parameters;

/**
 *
 * @author Gianluca
 */
public class LoginSignUpNeo4jDataAccess extends Neo4jDataAccess{
    /**
     * Funzione che permette la registrazione di un utente nuovo tramite l'utilizzo della funzione
     * createUtenteNode
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @param ruolo 
     */
    public static int registraUtente(final String nome,final String cognome,final String email,final String password,final String ruolo)
    {
        
        try(Session session=driver.session())
        {
            return session.writeTransaction(new TransactionWork<Integer>()
            {
                @Override
                public Integer execute(Transaction tx)
                {
                    return createUtenteNode(tx,nome,cognome,email,password,ruolo);
                }
            }
            );
        }
    
    
    }
    /**
     * Funzione che permette la creazione del nodo utente da registrare
     * treamite i valori passati da parametro
     * 
     * @param tx
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @param ruolo
     * @return 0 se non esiste un utente con la stessa email l'operazione andra' a
     * buon fine.
     * @return  1 se esiste un utente con la stessa email passata come parametro
     * l'operazione fallisce.
     */
    private static int createUtenteNode(Transaction tx,String nome,String cognome,String email,String password,String ruolo)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("nome",nome);
        parameters.put("cognome",cognome);
        parameters.put("email",email);
        parameters.put("password",password);
        parameters.put("ruolo",ruolo);
        if(!UtentePresente(tx,email)){
            tx.run("CREATE(a:Utente{nome:$nome,cognome:$cognome,email:$email,password:$password,ruolo:$ruolo})",parameters);
            return 0;
        }
        return 1;
    } 
    /**
     * La funzione controlla se vi sono utenti registrati con la data email
     * @param tx
     * @param email
     * @return true se vi e' gia' un utente registrato con l'email passata
     * come parametro
     * @return false se non vi e' un utente registrato con l'email passata
     * come parametro
     */
    private static boolean UtentePresente(Transaction tx,String email)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);
        StatementResult result=tx.run("MATCH(a:Utente) WHERE a.email=$email RETURN a",parameters);
        if(result.hasNext())
            return true;
        return false;
    }
    public static int loginUtente(final String email,final String password)
    {
        
        try(Session session=driver.session())
        {
            
            return session.readTransaction(new TransactionWork<Integer>()
            {
                @Override
                public Integer execute(Transaction tx)
                {
                    return matchUtente(tx,email,password);
                }
            });
           
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
    private static int matchUtente(Transaction tx,String email,String password)
    {
        Utente utente;
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);
        parameters.put("password",password);       
        StatementResult result=tx.run("MATCH(a:Utente) WHERE a.email=$email AND a.password = $password RETURN a",parameters);
        if(!result.hasNext())
            return 1;
        
        Record record=result.single();
        String nome = record.get("nome", "NA");
        String cognome= record.get("cognome","NA");
        String id = record.get("<id>","NA");
        String ruolo = record.get("ruolo","NA");
        Societa societa=SocietaUtente(tx,email,id);
        utente = new Utente(id, nome, cognome, email, ruolo,societa);
        ScreenController.setUtente(utente);
        return 0;
    
    }
    /**
     * 
     * @param tx
     * @param email
     * @return Societa di un utente se tale utente lavora per una società
     * @return null se l'utente passato come parameto non lavora per nessuna
     * societa'
     */
    private static Societa SocietaUtente(Transaction tx,String email,String idUtente)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);
        StatementResult result=tx.run("MATCH(a:Utente) WHERE a.email=$email AND password = $password"+
                 "OPTIONAL MATCH (a)-[r:Tesserato_per]->(s:Societa) OPTIONAL MATCH (so:Societa) WHERE (so)<-[r]-(a)"+
                  "RETURN so",parameters);
        if(!result.hasNext())
            return null;
        Record record=result.single();
        String nomeSocieta=record.get("nomeSocieta", "NA");
        String nazione=record.get("nazione", "NA");
        String id=record.get("id","NA");
        Societa societaRisultato=new Societa(id,nomeSocieta,nazione,idUtente);
        return societaRisultato;
    }
    
}
