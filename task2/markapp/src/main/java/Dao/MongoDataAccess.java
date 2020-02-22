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
    protected static MongoCollection<Document> collectionUtenti;
    protected static MongoCollection<Document> collectionSocieta;
    protected static MongoCollection<Document> collectionCalciatore;
    protected static MongoCollection<Document> collectionStatistiche;
    protected static final String IP="localhost";
    protected static final int PORT=27017;
    protected static final String DBNAME="mercato";
    private static final String nomeCollectionUtenti="utenti";
    private static final String nomeCollectionSocieta="societa";
    private static final String nomeCollectionCalciatore="calciatore";
    private static final String nomeCollectionStatistiche="statistiche";
    /**
     * Instaura la connessione con il database
     * prende come parametro il nome della collection
     * @throws Exception 
     */
    public static void apriConnessione() throws Exception{
        mongoClient=MongoClients.create("mongodb://"+IP+":"+PORT);
        database=mongoClient.getDatabase(DBNAME);
        collectionUtenti=database.getCollection(nomeCollectionUtenti);    
        collectionSocieta=database.getCollection(nomeCollectionSocieta);
        collectionCalciatore=database.getCollection(nomeCollectionCalciatore);
        collectionStatistiche=database.getCollection(nomeCollectionStatistiche);
    }
    /**
     * Chiude la connessione col database
     */
    public static void chiudiConnessione(){
        mongoClient.close();
    }
}
