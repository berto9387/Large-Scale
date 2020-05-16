/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.util.HashMap;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

/**
 *
 * @author Gianluca
 */
public class Neo4jDataAccess {
    protected static Driver driver;
    protected static String uri="bolt://localhost:7687";
    protected static String user="neo4j";
    protected static String password="mercato";
    public static void InizializzaDriver()
    {
        driver=GraphDatabase.driver(uri, AuthTokens.basic(user,password));
    }
    public static void close() throws Exception
    {
        driver.close();
    }
    protected static boolean UtentePresente(Transaction tx,String email)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("email",email);
        StatementResult result=tx.run("MATCH(a:Utente) WHERE a.email=$email RETURN a",parameters);
        if(result.hasNext())
            return true;
        return false;
    }
    
}
