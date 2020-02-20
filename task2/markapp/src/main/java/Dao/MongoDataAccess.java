/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author berto
 */
public class MongoDataAccess {
    protected static MongoClient mongoClient;
    protected static MongoDatabase database;
    protected static MongoCollection<Document> collection;
    protected static final String IP="localhost";
    protected static final int PORT=27017;
    protected static final String DBNAME="mercato";
    /**
     * Instaura la connessione con il database
     * prende come parametro il nome della collection
     * @param collectionName
     * @throws Exception 
     */
    protected static void apriConnessione(String collectionName) throws Exception{
        mongoClient=MongoClients.create("mongodb://"+IP+":"+PORT);
        database=mongoClient.getDatabase(DBNAME);
        collection=database.getCollection(collectionName);        
    }
    /**
     * Chiude la connessione col database
     */
    protected static void chiudiConnessione(){
        mongoClient.close();
    }
}
