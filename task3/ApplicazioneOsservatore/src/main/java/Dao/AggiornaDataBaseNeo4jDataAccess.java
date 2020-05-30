/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.Neo4jDataAccess.driver;
import java.util.HashMap;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import com.mongodb.client.model.UpdateOptions;
import java.util.List;
import org.bson.Document;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.TransactionWork;

/**
 *
 * @author Gianluca
 */
public class AggiornaDataBaseNeo4jDataAccess extends Neo4jDataAccess{
    public static int aggiornaCalciatore(final String calciatore)
    {        
        try(Session session=driver.session())
        {
            boolean risultato;
            risultato=session.readTransaction(new TransactionWork<Boolean>()
            {
                @Override
                public Boolean execute(Transaction tx)
                {
                    return transizioneEsisteCalciatore(tx,Document.parse(calciatore).getString("_id"));
                }
            });
                                
        return session.writeTransaction(new TransactionWork<Integer>()
        {
            @Override
            public Integer execute(Transaction tx)
            {
                return transizioneCreaAggiornaCalciatore(tx,calciatore,risultato);
            }
        });
           
        
        }}
    public static int aggiornaSocieta(final String societa)
    {        
        try(Session session=driver.session())
        {
            boolean risultato;
            risultato=session.readTransaction(new TransactionWork<Boolean>()
            {
                @Override
                public Boolean execute(Transaction tx)
                {
                    return transizioneEsisteSocieta(tx,Document.parse(societa).getString("_id"));
                }
            });
                                
        return session.writeTransaction(new TransactionWork<Integer>()
        {
            @Override
            public Integer execute(Transaction tx)
            {
                return transizioneCreaAggiornaSocieta(tx,societa,risultato);
            }
        });
           
        
        }}
    private static boolean transizioneEsisteCalciatore(Transaction tx,String idGiocatore)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("id",idGiocatore);
        StatementResult result=tx.run("MATCH(giocatore:Calciatore) WHERE giocatore.id=$id RETURN giocatore",parameters);
        if(!result.hasNext())
            return false;
        return true;
    }
    private static int transizioneCreaAggiornaCalciatore(Transaction tx,String calciatore,boolean nodoEsiste)
    {
        Document calciatoreDoc = Document.parse(calciatore);
        HashMap<String,Object> parameters= new HashMap<>();
        if(nodoEsiste){
            creaNodoCalciatore(tx,calciatoreDoc);
        }
        else
        {
            int res=aggiornaNodoCalciatore(tx,calciatoreDoc);
            if(res==0)
            {
                return 1;
            }
        }
       
        List<String> appoggio=(List<String>)calciatoreDoc.get("altriRuoli");
        creaCollegamentiRuolo(tx,calciatoreDoc.getString("posizionePrincipale"),appoggio,calciatoreDoc.getString("_id"));
        aggiornaRelazioneGioca(tx,calciatoreDoc.getString("_id"),(List<Document>)calciatoreDoc.get("statistiche"));
        return 0;
    
    }
    private static int transizioneCreaAggiornaSocieta(Transaction tx,String societa,boolean nodoEsiste)
    {
        Document societaDoc = Document.parse(societa);
        HashMap<String,Object> parameters= new HashMap<>();
        if(nodoEsiste){
            creaNodoSocieta(tx,societaDoc);
        }
        else
        {
            int res=aggiornaNodoSocieta(tx,societaDoc);
            if(res==0)
            {
                return 1;
            }
        }
       
        return 0;
    
    }
    private static void creaNodoCalciatore(Transaction tx, Document calciatoreDoc)
    {
        HashMap<String,Object> parameters= new HashMap<>();
        parameters.put("id",calciatoreDoc.getString("_id"));
        parameters.put("nome", calciatoreDoc.getString("nome"));
        parameters.put("dataNascita",calciatoreDoc.getLong("dataNascita"));
        parameters.put("luogoNascita",calciatoreDoc.getString("luogoNascita"));
        parameters.put("altezza",calciatoreDoc.getInteger("altezza"));
        parameters.put("nazionalita",calciatoreDoc.getString("nazionalita"));
        parameters.put("piede",calciatoreDoc.getString("piede"));
        parameters.put("procuratore",calciatoreDoc.getString("procuratore"));
        parameters.put("squadra",calciatoreDoc.getString("squadra"));
        parameters.put("linkFoto",calciatoreDoc.getString("linkFoto"));
        parameters.put("inRosa",calciatoreDoc.getLong("inRosa"));
        parameters.put("scadenza",calciatoreDoc.getLong("scadenza"));
        try{
        parameters.put("valoreAttuale",calciatoreDoc.getInteger("valoreAttuale"));
        }
        catch(Exception e)
        {
            parameters.put("valoreAttuale",calciatoreDoc.getInteger("valoreAttuale"));
        }
       StatementResult result=tx.run("CREATE(Calciatore{id:$id,nome:$nome,dataNascita:$dataNascita,"
               + "luogoNascita:$luogoNascita,altezza:$altezza,nazionalita:$nazionalita,piede:$piede,"
               + "procuratore:$procuratore,squadra:$squadra,linkFoto:$linkFoto,inRosa:$inRosa,scadenza:$scadenza})"
        ,parameters);
        
    }
    private static void creaCollegamentiRuolo(Transaction tx,String ruoloPrincipale,List<String> Altriruoli,String idCalciatore){
        HashMap<String,Object> parameters= new HashMap();
        parameters.put("idCalciatore", idCalciatore);
        int counter=0;
        String query="MATCH((giocatore:Calciatore{id:$idCalciatore})-[pos:POsizione]->())"
                + " DELETE pos";//prima di aggiornare le posizioni elimino i collegamenti e poi riaggiungo tutti i collegamenti con posizione
        parameters.put("ruolo" + counter,ruoloPrincipale);
        query=query + " CREATE (giocatore)-[:POsizione{preferito:true}]->(:Ruolo{ruolo:$ruolo" + counter +"})";
        counter++;
        for(int i=0;i<Altriruoli.size();i++)
        {
            parameters.put("ruolo"+counter, Altriruoli.get(i));
            query= query + " CREATE(giocatore)-[:POsizione{preferito:false}]->(:Ruolo{ruolo:$ruolo" + counter +"})";
            counter++;
        }
        tx.run(query,parameters);
    }
    /**
     * 
     * @param tx
     * @param calciatoreDoc
     * @return 0 in caso di aggiornamento andato a buon fine
     * @return 1 in caso di aggiornamento fallito
     */
    private static int aggiornaNodoCalciatore(Transaction tx,Document calciatoreDoc)
    {
        HashMap<String,Object> parameters= new HashMap<>();
        parameters.put("id",calciatoreDoc.getString("_id"));
        parameters.put("altezza",calciatoreDoc.getInteger("altezza"));
        parameters.put("procuratore",calciatoreDoc.getString("procuratore"));
        parameters.put("squadra",calciatoreDoc.getString("squadra"));
        parameters.put("linkFoto",calciatoreDoc.getString("linkFoto"));
        parameters.put("inRosa",calciatoreDoc.getLong("inRosa"));
        parameters.put("scadenza",calciatoreDoc.getLong("scadenza"));
        try{
            parameters.put("valoreAttuale",calciatoreDoc.getInteger("valoreAttuale"));
        }
        catch(Exception e)
        {
            parameters.put("valoreAttuale",calciatoreDoc.getInteger("valoreAttuale"));
        }
        StatementResult result=tx.run("MATCH(giocatore:Calciatore) WHERE giocatore.id=$id"
                + " SET giocatore.altezza=$altezza"
                + " SET giocatore.procuratore=$procuratore"
                + " SET giocatore.squadra=$squadra"
                + " SET giocatore.linkFoto=$linkFoto"
                + " SET giocatore.inRosa=$scadenza RETURN giocatore"
                + " SET giocatore.valoreAttuale=$valoreAttuale",parameters);
        if(!result.hasNext())
        {
           return 1; 
        }
        return 0;
    }
    private static void aggiornaRelazioneGioca(Transaction tx,String idCalciatore,List<Document> statistiche)
    {
        HashMap<String,Object> parameters= new HashMap<>();
        parameters.put("id",idCalciatore);
        String matchQuery ="MATCH(calciatore:Calciatore{id:$id})";
        String forEachQuery="";
        for(int i=0;i<statistiche.size();i++)
        {
            parameters.put("stagione" + i, statistiche.get(i).getString("stagione"));
            parameters.put("competizione"+i, statistiche.get(i).getString("competizione"));
            parameters.put("societa"+i, statistiche.get(i).getString("societa"));
            matchQuery=matchQuery + " OPTIONAL MATCH(s"+i+":Societa{nomeSocieta:$societa"+i+"})";
            forEachQuery=forEachQuery + " FOREACH(I IN CASE WHEN s"+i+" IS NOT NULL THEN [1] ELSE[] END| "
                    + "MERGE (calciatore)-[:GIOCA{competizione:$competizione"+i+",stagione:$stagione"+i+"}]->(s"+i+"))";
        }
    }
    private static boolean transizioneEsisteSocieta(Transaction tx,String idGiocatore)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("id",idGiocatore);
        StatementResult result=tx.run("MATCH(societa:Societa) WHERE societa.id=$id RETURN societa",parameters);
        return result.hasNext();
    } 
    private static void creaNodoSocieta(Transaction tx, Document societaDoc)
    {
        HashMap<String,Object> parameters= new HashMap<>();
        parameters.put("id",societaDoc.getString("_id"));
        parameters.put("nomeSocieta", societaDoc.getString("nomeSocieta"));
        parameters.put("lega",societaDoc.getString("lega"));
        parameters.put("nazione",societaDoc.getString("nazione"));
        parameters.put("linkLogo",societaDoc.getString("linkLogo"));
       
       StatementResult result=tx.run("CREATE(Societa{id:$id,nomeSocieta:$nomeSocieta,lega:$lega,"
               + "nazione:$nazione,linkLogo:$linkLogo})",parameters);
        
    }
    private static int aggiornaNodoSocieta(Transaction tx,Document societaDoc)
    {
        HashMap<String,Object> parameters= new HashMap<>();
        parameters.put("id",societaDoc.getString("_id"));
        parameters.put("nomeSocieta", societaDoc.getString("nomeSocieta"));
        parameters.put("lega",societaDoc.getString("lega"));
        parameters.put("nazione",societaDoc.getString("nazione"));
        parameters.put("linkLogo",societaDoc.getString("linkLogo"));
        StatementResult result=tx.run("MATCH(societa:Societa) WHERE societa.id=$id"
                + " SET societa.nomeSocieta=$nomeSocieta"
                + " SET societa.lega=$lega"
                + " SET societa.nazione=$nazione"
                + " SET giocatore.linkLogo=$linkLogo"
                + " RETURN societa",parameters);
        if(!result.hasNext())
        {
           return 1; 
        }
        return 0;
    }
}
