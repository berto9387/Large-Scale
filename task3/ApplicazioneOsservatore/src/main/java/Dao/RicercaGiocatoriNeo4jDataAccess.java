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

    public static int rimuoviCalciatoreSeguito(String idCalciatore) {
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
        System.out.println("<<<<<<<<<<<<<<>>>>>>>>>idCalciatore= " + idCalciatore);
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

    public static List<InformazioniRicercaCalciatore> ricercaCalciatori() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
