/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.InformazioniRicercaCalciatore;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author berto
 * Gestisce le funzioni necessarie per il filtraggio e la ricerca dei calciatori
 * e restituisce la lista dei giocatori preferiti e/o il giocatore scelto
 */
public class RicercaGiocatoriMongoDataAccess extends MongoDataAccess{
    public static List<InformazioniRicercaCalciatore> ricerca(String nome, String cognome) {
        if(!nome.isEmpty())
            nome+="([a-z])*";
        if(!cognome.isEmpty())
            cognome+="([a-z])*";
        String regex =  "(^.*?\\b"+nome+"\\b.*?\\b"+cognome+"\\b.*?$)|(^.*?\\b"+cognome+"\\b.*?\\b"+nome+"\\b.*?$)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Bson filter = Filters.eq("nome", pattern);
        List<InformazioniRicercaCalciatore> infos=new ArrayList<>();
        try(MongoCursor<Document> cur=collectionCalciatore.find(filter).iterator()){
            while (cur.hasNext()) {

                Document aux = cur.next();
                System.err.println(aux.getLong("dataNascita")+" "+aux.getString("nome"));
                InformazioniRicercaCalciatore info=new InformazioniRicercaCalciatore();
                if(aux.getString("_id")!=null)
                    info.setIdCalciatore(aux.getString("_id"));
                if(aux.getString("nome")!=null)
                    info.setNome(aux.getString("nome"));
                if(aux.getLong("dataNascita")!=null){
                    Date dataNascita=new Date(aux.getLong("dataNascita"));
                    LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                                                 ZoneId.systemDefault());
                    info.setEta(ldt);
                }
                if(aux.getString("nazionalita")!=null)
                    info.setNazionalita(aux.getString("nazionalita"));
                if(aux.getString("linkFoto")!=null){
                    ImageView item_1 = new ImageView(new Image(aux.getString("linkFoto")));
                    info.setImage(item_1);
                }
                    
                if(aux.getString("squadra")!=null)
                    info.setSquadra(aux.getString("squadra"));
                if(aux.getString("posizionePrincipale")!=null)
                    info.setRuoloPrincipale(aux.getString("posizionePrincipale"));
                infos.add(info);
            }
        } 
        return infos;
    }
    public static List<InformazioniRicercaCalciatore> ricercaAvanzata(String competizione,String stagione, String squadra,
            String posizionePrincipale, int minVM,int maxVM,int minAltezza,int maxAltezza,
            int etaMin, int etaMax, String contratto, double infortuniMedia, double goalMedia,
            double assitMedia, double goalSubitiMedia, double cartelliniMedia) {
        

        System.out.println(competizione+"  "+stagione+"  "+squadra+"  "+
                posizionePrincipale+"  "+minVM+"  "+maxVM+"  "+minAltezza+"  "+maxAltezza+"  "+
                etaMin+"  "+etaMax+"  "+contratto+"  "+infortuniMedia+"  "+goalMedia+"  "+
                assitMedia+"  "+goalSubitiMedia+"  "+cartelliniMedia);
        //prima match importante per sfoltire i risultati
        Document filtroMatch1=new Document();
        //filtro squadra
        if(!squadra.isEmpty())
            filtroMatch1.append("squadra",squadra);
        //filtro competizione attuale
        if(!stagione.isEmpty() && !competizione.isEmpty())
            filtroMatch1.append("statistiche",new Document("$elemMatch",
                new Document("stagione",stagione)
                        .append("competizione", competizione)));
        //filtro per la ricerca del ruolo
        if(posizionePrincipale!=null)
            filtroMatch1.append("$or", Arrays.asList(new Document("posizionePrincipale", posizionePrincipale), new Document("altriRuoli", new Document("$eq", posizionePrincipale))));
        //filtro per la ricerca del valore attuale
        minVM*=1000000;
        maxVM*=1000000;
        if(!(minVM==0.0 && maxVM==0.0))
            filtroMatch1.append("valoreAttuale", new Document("$gt",minVM).append("$lt", maxVM));
        //filtro altezza
        if(!(minAltezza==130 && maxAltezza==130) )
            filtroMatch1.append("altezza", new Document("$gt", minAltezza).append("$lt", maxAltezza));
        //ricerca range eta
        
        if(!(etaMin==10 && etaMax==10)){
            Date etaMinDate=trovaDataDiXAnniFa((int)etaMin);
            Date etaMaxDate=trovaDataDiXAnniFa((int)etaMax);
            filtroMatch1.append("dataNascita", new Document("$gt", etaMaxDate.getTime()).append("$lt", etaMinDate.getTime()));
        }
        
        //filtro unwind/////////////////////
        Document unwind=new Document();
        unwind.append("path", "$statistiche");
        unwind.append("preserveNullAndEmptyArrays", false);
        //filtro che raggruppa in base alla stagione/////////////////
        Document group1=new Document();
            Document groupStagione=new Document()
                .append("_id", "$_id")
                .append("nome", "$nome")
                .append("dataNascita", "$dataNascita")
                .append("altezza", "$altezza")
                .append("nazionalita", "$nazionalita")
                .append("squadra", "$squadra")
                .append("linkFoto", "$linkFoto")
                .append("posizionePrincipale", "$posizionePrincipale")
                .append("valoreAttuale", "$valoreAttuale")
                .append("infortunio", "$infortunio")
                .append("stagione", "$statistiche.stagione");
        group1.append("_id", groupStagione)
        //somma tutte le statistiche per una determinata stagione
        .append("presenze", new Document("$sum", "$statistiche.presenze"))
        .append("reti", new Document("$sum", "$statistiche.reti"))
        .append("cartellini", new Document("$sum", new Document("$multiply", Arrays.asList("$statistiche.ammonizioni", "$statistiche.doppieAmmonizioni", "$statistiche.espulsioni"))))
        .append("retiSubite", new Document("$sum", "$statistiche.retiSubite"))
        .append("puntiPartita", new Document("$sum", "$statistiche.puntiPartita"))
        .append("minutiGiocati", new Document("$sum", "$statistiche.minutiGiocati"))
        .append("partiteNoGoal", new Document("$sum", "$statistiche.partiteNoGoal"))
        .append("assist", new Document("$sum", "$statistiche.assist"));       
        //filtro che raggruppa i risultati trovati dal filtro precedente
        Document group2=new Document();
            Document groupMedia=new Document()
                    .append("_id", "$_id._id")
                    .append("nome", "$_id.nome")
                    .append("dataNascita", "$_id.dataNascita")
                    .append("altezza", "$_id.altezza")
                    .append("nazionalita", "$_id.nazionalita")
                    .append("squadra", "$_id.squadra")
                    .append("linkFoto", "$_id.linkFoto")
                    .append("posizionePrincipale", "$_id.posizionePrincipale")
                    .append("valoreAttuale", "$_id.valoreAttuale")
                    .append("infortunio", "$_id.infortunio");
        group2.append("_id", groupMedia)
        //media delle caratteristiche
        .append("presenze", new Document("$avg", "$presenze"))
        .append("reti", new Document("$avg", "$reti"))
        .append("cartellini", new Document("$avg", "$cartellini"))
        .append("retiSubite", new Document("$avg", "$retiSubite"))
        .append("puntiPartita", new Document("$avg", "$puntiPartita"))
        .append("minutiGiocati", new Document("$avg", "$minutiGiocati"))
        .append("partiteNoGoal", new Document("$avg", "$partiteNoGoal"))
        .append("assist", new Document("$avg", "$assist"));
        
        //ora qui occore fare il filtro che matcha con i filtri scelti
        Document filtroMatch2=new Document();
        //filtro media goal stagionali
        if(goalMedia!=0.0 && !"Portiere".equals(posizionePrincipale))
            filtroMatch2.append("reti", new Document("$gt",goalMedia));
        //filtro media cartellini stagionali
        if(cartelliniMedia!=0.0 && !"Portiere".equals(posizionePrincipale))
            filtroMatch2.append("cartellini", new Document("$lt",cartelliniMedia));
        //filtro media assist stagionali
        if(assitMedia!=0.0 && !"Portiere".equals(posizionePrincipale))
            filtroMatch2.append("assist", new Document("$gt",assitMedia));
        //filtro media goal subiti stagionali
        if(goalSubitiMedia!=0.0 && "Portiere".equals(posizionePrincipale))
            filtroMatch2.append("retiSubite", new Document("$lt",goalSubitiMedia));
           
            
        List<InformazioniRicercaCalciatore> infos=new ArrayList<>();
        
        try {  
            MongoCursor<Document> cur;
            if(filtroMatch1.isEmpty() && filtroMatch2.isEmpty())
                return null;
            else if(!filtroMatch1.isEmpty() && filtroMatch2.isEmpty()){
                cur=collectionCalciatore.aggregate(
                        Arrays.asList(new Document("$match",filtroMatch1))
                    ).iterator();
            } else if(filtroMatch1.isEmpty() && !filtroMatch2.isEmpty()){
                System.out.println("filtro2");
                cur=collectionCalciatore.aggregate(
                        Arrays.asList(
                            new Document("$unwind",unwind),
                            new Document("$group",group1),
                            new Document("$group",group2),
                            new Document("$match",filtroMatch2))
                    ).iterator();
            } else{
                System.out.println("filtro");
                cur=collectionCalciatore.aggregate(
                        Arrays.asList(
                            new Document("$match",filtroMatch1),
                            new Document("$unwind",unwind),
                            new Document("$group",group1),
                            new Document("$group",group2),
                            new Document("$match",filtroMatch2))
                    ).iterator();
            }
            
            while (cur.hasNext()) {
                
                Document prova= cur.next();
                Document aux = null;
                if(!filtroMatch1.isEmpty() && filtroMatch2.isEmpty()){
                    aux=prova;
                } else{
                    aux=(Document)prova.get("_id");
                }
                        
                 
                System.err.println(prova.toJson());
                InformazioniRicercaCalciatore info=new InformazioniRicercaCalciatore();
                if(aux.getString("_id")!=null)
                    info.setIdCalciatore(aux.getString("_id"));
                if(aux.getString("nome")!=null)
                    info.setNome(aux.getString("nome"));
                if(aux.getLong("dataNascita")!=null){
                    Date dataNascita=new Date(aux.getLong("dataNascita"));
                    LocalDateTime ldt=LocalDateTime.ofInstant(dataNascita.toInstant(),
                                                 ZoneId.systemDefault());
                    info.setEta(ldt);
                }
                if(aux.getString("nazionalita")!=null)
                    info.setNazionalita(aux.getString("nazionalita"));
                if(aux.getString("linkFoto")!=null){
                    ImageView item_1 = new ImageView(new Image(aux.getString("linkFoto")));
                    info.setImage(item_1);
                }
                    
                if(aux.getString("squadra")!=null)
                    info.setSquadra(aux.getString("squadra"));
                if(aux.getString("posizionePrincipale")!=null)
                    info.setRuoloPrincipale(aux.getString("posizionePrincipale"));
                infos.add(info);
            }
            cur.close();
        } catch (Exception e) {           
            return null;
        } 
        
        return infos;
    }
    
}
