/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entita.Utente;
import java.util.HashMap;
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
    public static void registraUtente(final String nome,final String cognome,final String email,final String password,final String ruolo)
    {
        
        try(Session session=driver.session())
        {
            session.writeTransaction(new TransactionWork<Integer>()
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
    
    private static int createUtenteNode(Transaction tx,String nome,String cognome,String email,String password,String ruolo)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("nome",nome);
        parameters.put("cognome",cognome);
        parameters.put("email",email);
        parameters.put("password",password);
        parameters.put("ruolo",ruolo);
        tx.run("CREATE(a:Utente{nome:$nome,cognome:$cognome,email:$email,password:$password,ruolo:$ruolo})",parameters);
        return 1;
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
    private static int matchUtente(Transaction tx,String email,String password)
    {
        Utente utente;
        String query="MATCH(a:Utente) WHERE a.email=$email AND password = $password RETURN a";
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);
        parameters.put("password",password);       
        StatementResult result=tx.run("MATCH(a:Utente) WHERE a.email=$email AND password = $password RETURN a",parameters);
        result.single().get("nome");
        return (-1);
    
    }
}
