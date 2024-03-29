/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import static Dao.Neo4jDataAccess.driver;
import Model.InformazioniRicercaCalciatore;
import Model.InformazioniRicercaCalciatoreSeguito;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class RicercaGiocatoriNeo4jDataAccess {

    public static int rimuoviCalciatoreSeguito(final String idCalciatore) {
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<Integer>()
            {
                @Override
                public Integer execute(Transaction tx)
                {
                   return transactionRimuoviCalciatoreSeguito(tx,idCalciatore);
                }
            }); 
        }   
    }
    
    private static int transactionRimuoviCalciatoreSeguito(Transaction tx,String idCalciatore){
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("idCalciatore", idCalciatore);
        parameters.put("email", ScreenController.getUtente().getEmail());
        StatementResult result=tx.run("MATCH(a:Utente)-[i:INTERESSATO]->(c:Calciatore) WHERE a.email=$email and c.id=$idCalciatore"+
                 " DELETE i",parameters);

        return 0;
    }

    public static List<InformazioniRicercaCalciatoreSeguito> ricercaGiocatoriSeguiti()
    {
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<List>()
            {
                @Override
                public List<InformazioniRicercaCalciatoreSeguito> execute(Transaction tx)
                {
                   return transactionRicercaCalciatoreSeguito(tx);
                }
            }); 
        }   
    }
    
     private static List<InformazioniRicercaCalciatoreSeguito> transactionRicercaCalciatoreSeguito(Transaction tx)
     {
         List<InformazioniRicercaCalciatoreSeguito> calciatoriSeguiti = new ArrayList<>();
         HashMap<String,Object> parameters =new HashMap<>();
         parameters.put("email", ScreenController.getUtente().getEmail());
         StatementResult result=tx.run("MATCH(a:Utente)-[:INTERESSATO]->(c:Calciatore)-[:POsizione]->(r:Ruolo) WHERE a.email=$email "
                 + " RETURN c, collect(r)[..1] as ruoloPrincipale", parameters);
         while(result.hasNext())
         {
             Record record=result.next();
             List<Pair<String,Value>> values = record.fields();
             InformazioniRicercaCalciatoreSeguito calciatoreSeguito=new InformazioniRicercaCalciatoreSeguito();
             for (Pair<String,Value> nameValue: values)
             {
                 if("c".equals(nameValue.key()))
                 {
                     Value value = nameValue.value();
                     calciatoreSeguito.setIdCalciatore(value.get("id").asString());
                     Date dataNascita =  new Date(Long.valueOf(value.get("dataNascita").asString()));
                     LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                       ZoneId.systemDefault());
                     calciatoreSeguito.setEta(ldt);
                     calciatoreSeguito.setNazionalita(value.get("nazionalita").asString());
                     calciatoreSeguito.setNome(value.get("nome").asString());
                     calciatoreSeguito.setSquadra(value.get("squadra").asString());
                     calciatoreSeguito.setValoreMercato(Integer.parseInt(value.get("valoreAttuale").asString()));
                     ImageView fotoCalciatore = new ImageView(new Image(value.get("linkFoto").asString()));
                     calciatoreSeguito.setImage(fotoCalciatore);
                 }
                 if("ruoloPrincipale".equals(nameValue.key()))
                 {
                     Value value = nameValue.value(); 
                     calciatoreSeguito.setRuoloPrincipale(value.get(0).get("ruolo").asString());
                 }
             }
             calciatoriSeguiti.add(calciatoreSeguito);
         }
         return calciatoriSeguiti;
     }

    public static List<InformazioniRicercaCalciatore> ricercaCalciatori(final String nomeCalciatore,final String squadraCalciatore, final String email) {
        
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<List>()
            {
                @Override
                public List<InformazioniRicercaCalciatore> execute(Transaction tx)
                {
                   return transactionRicercaCalciatori(tx, nomeCalciatore, squadraCalciatore, email);
                }
            }); 
        }
        
    }
    
    private static List<InformazioniRicercaCalciatore> transactionRicercaCalciatori(Transaction tx, String nomeCalciatore, String squadraCalciatore, String email)
    {
        List<InformazioniRicercaCalciatore> calciatoriCercati = new ArrayList<>();
        
        HashMap<String,Object> parameters =new HashMap<>();
        
        parameters.put("email", email);
        if(!nomeCalciatore.equals(""))
            parameters.put("nome", nomeCalciatore);
        if(!squadraCalciatore.equals(""))
            parameters.put("squadra", squadraCalciatore);
        
        StatementResult result = null;
        System.out.println("<<<>> nome SQUADRA =  " + squadraCalciatore);
        System.out.println("<<<>> nome CALCIATORE =  " + nomeCalciatore);
        if(nomeCalciatore.equals("")) //Se il nome è vuoto cerco solo per squadra
        {
            System.out.println("<<<>>nome = '' ");
            result=tx.run("MATCH (c:Calciatore)-[:POsizione]->(r:Ruolo) "
                    + " WHERE c.squadra=$squadra AND NOT ((c:Calciatore)<-[:INTERESSATO]-(:Utente{email:$email}))"
                    + " OPTIONAL MATCH (c:Calciatore)<-[i:INTERESSATO]-() "
                    + " RETURN c, COUNT(DISTINCT i) AS seguitoDa,collect(r)[..1] as ruoloPrincipale"
                    + " ORDER BY seguitoDa DESC ", parameters);
        }
        if(squadraCalciatore.equals("")) //Se la squadra è vuota cerco solo per nome
        {
            System.out.println("<<<>>SQUADRA = '' ");
            result=tx.run("MATCH (c:Calciatore)-[:POsizione]->(r:Ruolo) "
                    + " WHERE c.nome=$nome AND NOT ((c:Calciatore)<-[:INTERESSATO]-(:Utente{email:$email}))"
                    + " OPTIONAL MATCH (c:Calciatore)<-[i:INTERESSATO]-() "
                    + " RETURN c, COUNT(DISTINCT i) AS seguitoDa,collect(r)[..1] as ruoloPrincipale"
                    + " ORDER BY seguitoDa DESC ", parameters);
        }
        if(!nomeCalciatore.equals("") && !squadraCalciatore.equals("")) //Altrimenti cerco per entrambi
        {
            System.out.println("<<<>> nome SQUADRA = '', nome calciatore = '' " + squadraCalciatore);
            result=tx.run("MATCH (c:Calciatore)-[:POsizione]->(r:Ruolo) "
                    + " WHERE c.nome=$nome AND c.squadra=$squadra"
                    + " AND NOT ((c:Calciatore)<-[:INTERESSATO]-(:Utente{email:$email}))"
                    + " OPTIONAL MATCH (c:Calciatore)<-[i:INTERESSATO]-() "
                    + " RETURN c, COUNT(DISTINCT i) AS seguitoDa,collect(r)[..1] as ruoloPrincipale"
                    + " ORDER BY seguitoDa DESC ", parameters);
            
        }
        while(result.hasNext())
        {
            Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            
            InformazioniRicercaCalciatore calciatoreRicercato=new InformazioniRicercaCalciatore();
            for (Pair<String,Value> nameValue: values)
            {
                if("c".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setIdCalciatore(value.get("id").asString());
                    Date dataNascita =  new Date(Long.valueOf(value.get("dataNascita").asString()));
                    LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                      ZoneId.systemDefault());
                    calciatoreRicercato.setEta(ldt);
                    calciatoreRicercato.setNome(value.get("nome").asString());
                    calciatoreRicercato.setSquadra(value.get("squadra").asString());
                    calciatoreRicercato.setNazionalita(value.get("nazionalita").asString());
                    calciatoreRicercato.setValoreMercato(Integer.parseInt(value.get("valoreAttuale").asString()));
                    ImageView fotoCalciatore = new ImageView(new Image(value.get("linkFoto").asString()));
                    calciatoreRicercato.setImage(fotoCalciatore);
                }
                if("seguitoDa".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setSeguitoDa(Integer.parseInt(value.toString()));
                }
                if("ruoloPrincipale".equals(nameValue.key()))
                {
                    Value value = nameValue.value(); 
                    calciatoreRicercato.setRuoloPrincipale(value.get(0).get("ruolo").asString());
                }
            }
            calciatoriCercati.add(calciatoreRicercato);
        }
        
        return calciatoriCercati;
    }

    public static List<InformazioniRicercaCalciatore> ricercaBandiere(String email) {
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<List>()
            {
                @Override
                public List<InformazioniRicercaCalciatore> execute(Transaction tx)
                {
                   return transactionRicercaBandiere(tx, email);
                }
            }); 
        }
    }
    private static List<InformazioniRicercaCalciatore> transactionRicercaBandiere(Transaction tx, String email)
     {
         List<InformazioniRicercaCalciatore> calciatoriCercati = new ArrayList<>();
         Calendar calendar  = Calendar.getInstance();
         calendar.add(Calendar.YEAR, -27);
         Long targetDate = calendar.getTime().getTime();
        
         HashMap<String,Object> parameters =new HashMap<>();
         parameters.put("email", email);
         parameters.put("targetDate", targetDate);
         StatementResult result=tx.run("match (e:Ruolo)<-[:POsizione]-(c:Calciatore)-[r:GIOCA]->(s:Societa)"
                 + " OPTIONAL MATCH (c:Calciatore)<-[i:INTERESSATO]-() "
                 + " with e,c,s,i,count(r) as cnt"
                 + " where toInt(c.dataNascita)<=$targetDate and cnt>10"
                 + " AND NOT ((c:Calciatore)<-[:INTERESSATO]-(:Utente{email:$email}))"
                 + " return c, count(distinct s.nomeSocieta) as numeroSquadre,collect(e)[..1] as ruoloPrincipale,"
                 + " COUNT(DISTINCT i) AS seguitoDa"
                 + " order by numeroSquadre ASC limit 10 ",parameters);
         while(result.hasNext())
         {
             Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            
            InformazioniRicercaCalciatore calciatoreRicercato=new InformazioniRicercaCalciatore();
            for (Pair<String,Value> nameValue: values)
            {
                if("c".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setIdCalciatore(value.get("id").asString());
                    Date dataNascita =  new Date(Long.valueOf(value.get("dataNascita").asString()));
                    LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                      ZoneId.systemDefault());
                    calciatoreRicercato.setEta(ldt);
                    calciatoreRicercato.setNome(value.get("nome").asString());
                    calciatoreRicercato.setSquadra(value.get("squadra").asString());
                    calciatoreRicercato.setNazionalita(value.get("nazionalita").asString());
                    calciatoreRicercato.setValoreMercato(Integer.parseInt(value.get("valoreAttuale").asString()));
                    ImageView fotoCalciatore = new ImageView(new Image(value.get("linkFoto").asString()));
                    calciatoreRicercato.setImage(fotoCalciatore);
                }
                if("seguitoDa".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setSeguitoDa(Integer.parseInt(value.toString()));
                }
                if("ruoloPrincipale".equals(nameValue.key()))
                {
                    Value value = nameValue.value(); 
                    calciatoreRicercato.setRuoloPrincipale(value.get(0).get("ruolo").asString());
                }
            }
            calciatoriCercati.add(calciatoreRicercato);
        }
        
        return calciatoriCercati;
     }

    public static List<InformazioniRicercaCalciatore> ricercaTop(String email) {
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<List>()
            {
                @Override
                public List<InformazioniRicercaCalciatore> execute(Transaction tx)
                {
                   return transactionRicercaTop(tx, email);
                }
            }); 
        }
    }
    private static List<InformazioniRicercaCalciatore> transactionRicercaTop(Transaction tx, String email)
     {
         List<InformazioniRicercaCalciatore> calciatoriCercati = new ArrayList<>();
         
         HashMap<String,Object> parameters =new HashMap<>();
         parameters.put("email", email);
         StatementResult result=tx.run("match (e:Ruolo)<-[:POsizione]-(c:Calciatore)<-[i:INTERESSATO]-(o:Utente)"
                 + " WHERE NOT ((c:Calciatore)<-[:INTERESSATO]-(:Utente{email:$email}))"
                 + " return c,collect(e)[..1] as ruoloPrincipale,COUNT(DISTINCT i) AS seguitoDa"
                 + " order by seguitoDa DESC limit 20 ", parameters);
         while(result.hasNext())
         {
             Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            
            InformazioniRicercaCalciatore calciatoreRicercato=new InformazioniRicercaCalciatore();
            for (Pair<String,Value> nameValue: values)
            {
                if("c".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setIdCalciatore(value.get("id").asString());
                    Date dataNascita =  new Date(Long.valueOf(value.get("dataNascita").asString()));
                    LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                      ZoneId.systemDefault());
                    calciatoreRicercato.setEta(ldt);
                    calciatoreRicercato.setNome(value.get("nome").asString());
                    calciatoreRicercato.setSquadra(value.get("squadra").asString());
                    calciatoreRicercato.setNazionalita(value.get("nazionalita").asString());
                    calciatoreRicercato.setValoreMercato(Integer.parseInt(value.get("valoreAttuale").asString()));
                    ImageView fotoCalciatore = new ImageView(new Image(value.get("linkFoto").asString()));
                    calciatoreRicercato.setImage(fotoCalciatore);
                }
                if("seguitoDa".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setSeguitoDa(Integer.parseInt(value.toString()));
                }
                if("ruoloPrincipale".equals(nameValue.key()))
                {
                    Value value = nameValue.value(); 
                    calciatoreRicercato.setRuoloPrincipale(value.get(0).get("ruolo").asString());
                }
            }
            calciatoriCercati.add(calciatoreRicercato);
        }
        
        return calciatoriCercati;
     }

    public static List<InformazioniRicercaCalciatore> ricercaConsigliati(String email) {
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<List>()
            {
                @Override
                public List<InformazioniRicercaCalciatore> execute(Transaction tx)
                {
                   return transactionRicercaConsigliati(tx,email);
                }
            }); 
        }
    }
    private static List<InformazioniRicercaCalciatore> transactionRicercaConsigliati(Transaction tx,String email)
     {
         List<InformazioniRicercaCalciatore> calciatoriCercati = new ArrayList<>();
         HashMap<String,Object> parameters =new HashMap<>();
         parameters.put("email", email);
         StatementResult result=tx.run("match (e:Ruolo)<-[:POsizione]-(c:Calciatore)<-[i:INTERESSATO]-(:Utente)<-[:SEGUE]-(os:Utente)"
                 + " where os.email=$email AND NOT ((c:Calciatore)<-[:INTERESSATO]-(:Utente{email:$email}))"
                 + " return c,collect(e)[..1] as ruoloPrincipale,COUNT(DISTINCT i) AS seguitoDa"
                 + " order by seguitoDa DESC limit 20 ",parameters);
         while(result.hasNext())
         {
             Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            
            InformazioniRicercaCalciatore calciatoreRicercato=new InformazioniRicercaCalciatore();
            for (Pair<String,Value> nameValue: values)
            {
                if("c".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setIdCalciatore(value.get("id").asString());
                    Date dataNascita =  new Date(Long.valueOf(value.get("dataNascita").asString()));
                    LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                      ZoneId.systemDefault());
                    calciatoreRicercato.setEta(ldt);
                    calciatoreRicercato.setNome(value.get("nome").asString());
                    calciatoreRicercato.setSquadra(value.get("squadra").asString());
                    calciatoreRicercato.setNazionalita(value.get("nazionalita").asString());
                    calciatoreRicercato.setValoreMercato(Integer.parseInt(value.get("valoreAttuale").asString()));
                    ImageView fotoCalciatore = new ImageView(new Image(value.get("linkFoto").asString()));
                    calciatoreRicercato.setImage(fotoCalciatore);
                }
                if("seguitoDa".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setSeguitoDa(Integer.parseInt(value.toString()));
                }
                if("ruoloPrincipale".equals(nameValue.key()))
                {
                    Value value = nameValue.value(); 
                    calciatoreRicercato.setRuoloPrincipale(value.get(0).get("ruolo").asString());
                }
            }
            calciatoriCercati.add(calciatoreRicercato);
        }
        
        return calciatoriCercati;
     }

    public static List<InformazioniRicercaCalciatore> ricercaPosizione(String posizione, String email) {
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<List>()
            {
                @Override
                public List<InformazioniRicercaCalciatore> execute(Transaction tx)
                {
                   return transactionRicercaPosizione(tx,posizione, email);
                }
            }); 
        }
    }
    private static List<InformazioniRicercaCalciatore> transactionRicercaPosizione(Transaction tx,String posizione, String email)
     {
         List<InformazioniRicercaCalciatore> calciatoriCercati = new ArrayList<>();
         HashMap<String,Object> parameters =new HashMap<>();
         parameters.put("posizione", posizione);
         parameters.put("email", email);
         StatementResult result=tx.run("match (e:Ruolo)<-[:POsizione]-(c:Calciatore)<-[i:INTERESSATO]-()"
                 + " where e.ruolo=$posizione AND NOT ((c:Calciatore)<-[:INTERESSATO]-(:Utente{email:$email}))"
                 + " return c,COUNT(DISTINCT i) AS seguitoDa"
                 + " order by seguitoDa DESC limit 20 ",parameters);
         while(result.hasNext())
         {
             Record record=result.next();
            List<Pair<String,Value>> values = record.fields();
            
            InformazioniRicercaCalciatore calciatoreRicercato=new InformazioniRicercaCalciatore();
            for (Pair<String,Value> nameValue: values)
            {
                if("c".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setIdCalciatore(value.get("id").asString());
                    Date dataNascita =  new Date(Long.valueOf(value.get("dataNascita").asString()));
                    LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                      ZoneId.systemDefault());
                    calciatoreRicercato.setEta(ldt);
                    calciatoreRicercato.setNome(value.get("nome").asString());
                    calciatoreRicercato.setSquadra(value.get("squadra").asString());
                    calciatoreRicercato.setNazionalita(value.get("nazionalita").asString());
                    calciatoreRicercato.setValoreMercato(Integer.parseInt(value.get("valoreAttuale").asString()));
                    ImageView fotoCalciatore = new ImageView(new Image(value.get("linkFoto").asString()));
                    calciatoreRicercato.setImage(fotoCalciatore);
                    calciatoreRicercato.setRuoloPrincipale(posizione);
                }
                if("seguitoDa".equals(nameValue.key()))
                {
                    Value value = nameValue.value();
                    calciatoreRicercato.setSeguitoDa(Integer.parseInt(value.toString()));
                }
                
            }
            
            calciatoriCercati.add(calciatoreRicercato);
        }
        
        return calciatoriCercati;
     }

    public static int follow(String email, String idCalciatore) {
        try(Session session=driver.session())
        {
            
            return session.writeTransaction(new TransactionWork<Integer>()
            {
                @Override
                public Integer execute(Transaction tx)
                {
                   return transactionFollowCalciatore(tx,email, idCalciatore);
                }
            }); 
        }
    }
    
    private static int transactionFollowCalciatore(Transaction tx,String email,  String idCalciatore)
    {
        HashMap<String,Object> parameters =new HashMap<>();
        parameters.put("idCalciatore", idCalciatore);
        parameters.put("email", ScreenController.getUtente().getEmail());
        
            StatementResult result=tx.run("MATCH(o:Utente),(c:Calciatore) "
                                        + " WHERE o.email=$email and c.id=$idCalciatore"
                                        + " MERGE (o)-[i:INTERESSATO]->(c)", parameters);
        

        return 0;
    }
}
