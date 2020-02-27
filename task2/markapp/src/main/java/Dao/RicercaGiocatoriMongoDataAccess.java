/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entita.Calciatore;
import Entita.Infortunio;
import Entita.Statistica;
import Entita.Trasferimento;
import Model.InformazioniRicercaCalciatore;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
                cur=collectionCalciatore.aggregate(
                        Arrays.asList(
                            new Document("$unwind",unwind),
                            new Document("$group",group1),
                            new Document("$group",group2),
                            new Document("$match",filtroMatch2))
                    ).iterator();
            } else{
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

    public static Calciatore ricercaPerId(String idCalciatore) {
        Document calciatoreDoc = null;
        try{
            
        calciatoreDoc = collectionCalciatore.find(eq("_id", idCalciatore) ).first();
        
        } catch(Exception e){
            
            return null;
            
        }
        
        Calciatore calciatoreCercato = new Calciatore();
        if(calciatoreDoc != null){
            calciatoreCercato.setIdCalciatore(idCalciatore);
            if(calciatoreDoc.getString("nome") != null)
                calciatoreCercato.setNome(calciatoreDoc.getString("nome"));
            
            if(calciatoreDoc.getLong("dataNascita") != null){
                calciatoreCercato.setDataNascita(new Date(calciatoreDoc.getLong("dataNascita")));
            }
            
            if(calciatoreDoc.getString("luogoNascita") != null){
                calciatoreCercato.setLuogoNascita(calciatoreDoc.getString("luogoNascita"));
            }
            
            if(calciatoreDoc.getInteger("altezza") != null){
                calciatoreCercato.setAltezza(calciatoreDoc.getInteger("altezza"));
            }
            
            if(calciatoreDoc.getString("nazionalita") != null){
                calciatoreCercato.setNazionalita(calciatoreDoc.getString("nazionalita"));
            }
            
            if(calciatoreDoc.getString("piede") != null){
                calciatoreCercato.setPiede(calciatoreDoc.getString("piede"));
            }
            
            if(calciatoreDoc.getString("procuratore") != null){
                calciatoreCercato.setProcuratore(calciatoreDoc.getString("procuratore"));
            }
            
            if(calciatoreDoc.getString("squadra") != null){
                calciatoreCercato.setSquadra(calciatoreDoc.getString("squadra"));
            }
            
            if(calciatoreDoc.getString("linkFoto")!=null){
                    calciatoreCercato.setImage(calciatoreDoc.getString("linkFoto"));
            }
            
            if(calciatoreDoc.getString("posizionePrincipale")!=null){
                    calciatoreCercato.setRuoloPrincipale(calciatoreDoc.getString("posizionePrincipale"));
            }
            
            if(calciatoreDoc.getLong("inRosaDa") != null){
                calciatoreCercato.setInRosaDa(new Date(calciatoreDoc.getLong("inRosaDa")));
            }
            
            if(calciatoreDoc.getLong("scadenza") != null){
                calciatoreCercato.setScadenza(new Date(calciatoreDoc.getLong("scadenza")));
            }
            
            if(calciatoreDoc.getInteger("valoreAttuale") != null){
                calciatoreCercato.setValoreMercato(calciatoreDoc.getInteger("valoreAttuale"));
            }
            //Solo per giocatori IN PRESTITO
            if(calciatoreDoc.getString("inPrestitoDa") != null){
                calciatoreCercato.setInPrestitoDa(calciatoreDoc.getString("inPrestitoDa"));
            }
            
            if(calciatoreDoc.getLong("contrattoFinoAl") != null){
                calciatoreCercato.setContrattoFinoAl( new Date(calciatoreDoc.getLong("contrattoFinoAl")));
            }
            //
            String stagioneAttuale = calcolaStagioneAttuale(); //Servirà per il calcolo delle statistiche stagionali
            
            //TRASFERIMENTI
            if(calciatoreDoc.get("trasferimento") != null){
                List<Document> trasferimenti=(List<Document>)calciatoreDoc.get("trasferimento");
                Trasferimento aux = null;
                for(Document trasferimento : trasferimenti){
                    aux= new Trasferimento(trasferimento.getString("stagione"), new Date(trasferimento.getLong("data")), trasferimento.getString("venditore"), 
                                            trasferimento.getString("acquirente"), trasferimento.getInteger("valoreDiMercato"), trasferimento.getString("riscatto"));
                
                    calciatoreCercato.addTrasferimento(aux);
                }
            }
            
            //STATISTICHE
            if(calciatoreDoc.get("statistiche") != null){
                List<Document> statistiche = (List<Document>)calciatoreDoc.get("statistiche");
                Statistica aux = null;
                for(Document statistica : statistiche){
                    if(calciatoreCercato.getRuoloPrincipale().equals("Portiere")){ //Portiere e calciatori hanno statistiche diverse
                        //Portieri
                        aux= new Statistica(statistica.getString("stagione"), statistica.getString("competizione"), statistica.getString("squadra"),
                                        statistica.getInteger("presenze"), statistica.getInteger("reti"),
                                        statistica.getInteger("ammonizioni"), statistica.getInteger("doppieAmmonizioni"),statistica.getInteger("espulsioni"),
                                        statistica.getInteger("minutiGiocati"), statistica.getInteger("retiSubite"), statistica.getInteger("partiteNoGol"));
                    }else{
                        //Calciatori
                        
                        aux= new Statistica(statistica.getString("stagione"), statistica.getString("competizione"), statistica.getString("squadra"),
                                        statistica.getInteger("presenze"), statistica.getInteger("reti"),
                                        statistica.getInteger("assist"), statistica.getInteger("ammonizioni"), statistica.getInteger("doppieAmmonizioni"),
                                        statistica.getInteger("espulsioni"), statistica.getInteger("minutiGiocati"));
                    }
                    
                    try{
                        aux.setPuntiAPartita(statistica.getDouble("puntiPartita"));
                    } catch(Exception e){
                        aux.setPuntiAPartita(statistica.getInteger("puntiPartita"));
                    }
                    calciatoreCercato.addStatistica(aux);
                }
                
                //CALCOLO STATISTICHE STAGIONALI
                Document statisticheStagionali = calcoloStatisticaStagionale(stagioneAttuale, idCalciatore);
                calciatoreCercato.setGoalStagioneAttuale(statisticheStagionali.getInteger("retiStagionali"));
                calciatoreCercato.setCartelliniGialliStagioneAttuale(statisticheStagionali.getInteger("gialliStagionali"));
                calciatoreCercato.setCartelliniRossiStagioneAttuale(statisticheStagionali.getInteger("rossiStagionali"));
                calciatoreCercato.setMinutiGiocatiStagioneAttuale(statisticheStagionali.getInteger("minutiGiocatiStagionali"));
                if(calciatoreCercato.getRuoloPrincipale().equals("Portiere")){ //Solo per PORTIERI
                    
                    calciatoreCercato.setPartiteNoGoalStagioneAttuale(statisticheStagionali.getInteger("partiteNoGolStagionali"));
                    calciatoreCercato.setGoalSubitiStagioneAttuale(statisticheStagionali.getInteger("retiSubiteStagionali"));
                    
                } else { //Solo per CALCIATORI
                    calciatoreCercato.setAssistStagioneAttuale(statisticheStagionali.getInteger("assistStagionali"));
                }
            }
            
            //ALTRI RUOLI
            if(calciatoreDoc.get("altriRuoli") != null){
                List<String> ruoli = (List<String>)calciatoreDoc.get("altriRuoli");
                String aux = null;
                for(int i=0 ; i< ruoli.size(); i++){
                    
                    aux = ruoli.get(i);
                    calciatoreCercato.addRuolo(aux);
                }
            }
            
            //INFORTUNI
            if(calciatoreDoc.get("infortunio") != null){
                
                List<Document> infortuni=(List<Document>)calciatoreDoc.get("infortunio");
                Infortunio aux = new Infortunio();
                for(Document infortunio : infortuni){
                    if(infortunio.getString("stagione") != null){
                        aux.setStagione(infortunio.getString("stagione"));
                    }
                    if(infortunio.getString("tipoInfortunio") != null){
                        aux.setTipoInfortunio(infortunio.getString("tipoInfortunio"));
                    }
                    if(infortunio.getLong("da") != null){
                        aux.setInizioInfortunio(new Date(infortunio.getLong("da")));
                    }
                    if(infortunio.getLong("al") != null){
                        aux.setFineInfortunio(new Date(infortunio.getLong("al")));
                    }
                    if(infortunio.getInteger("giorni") != null){
                        aux.setGiorniInfortunio(infortunio.getInteger("giorni"));
                    }
                    if(infortunio.getInteger("partitePerse") != null){
                        aux.setPartitePerse(infortunio.getInteger("partitePerse"));
                    }
                    
                    calciatoreCercato.addInfortunio(aux);
                }
            }
            
            
            
        }
        
        return calciatoreCercato;
    }

    private static Document calcoloStatisticaStagionale(String stagione, String id) {
        
        Document statisticheStagionaliDoc = collectionCalciatore.aggregate(Arrays.asList(match(eq("_id", id)), project(fields(excludeId(), include("statistiche"))), 
                                                                    unwind("$statistiche"), match(eq("statistiche.stagione", stagione)),
                                                                    group("$statistiche.stagione", sum("retiStagionali", "$statistiche.reti"), sum("assistStagionali", "$statistiche.assist"),
                                                                    sum("gialliStagionali", "$statistiche.ammonizioni"), sum("rossiStagionali", "$statistiche.espulsioni"),
                                                                    sum("minutiGiocatiStagionali", "$statistiche.minutiGiocati"), sum("retiSubiteStagionali", "$statistiche.retiSubite"),
                                                                    sum("partiteNoGolStagionali", "$statistiche.partiteNoGol")))).first();
        
        return statisticheStagionaliDoc;
    }
    
    private static String calcolaStagioneAttuale() {
        DateFormat dfY = new SimpleDateFormat("yy"); // Solo l'anno con 2 cifre
        String yy = dfY.format(Calendar.getInstance().getTime());
        
        DateFormat dfM = new SimpleDateFormat("MM"); // Mese 
        String MM = dfM.format(Calendar.getInstance().getTime());
        
        int month = Integer.parseInt(MM);
        int year = Integer.parseInt(yy);
        
        String stagione = null;
        if(month <= 8){ //Prima di settembre stagione attuale è quelle yy-1/yy
        
            if(year == 00){
                stagione = "99/00";
            }else{
                
                stagione = Integer.toString(Integer.parseInt(yy)-1) + "/" + yy;
            
            }
            
        } else{ //Da settembre stagione attuale = yy/yy+1
            
            if(year == 99){
                stagione = "99/00";
            }else{
            
                stagione = yy + "/" + Integer.toString(Integer.parseInt(yy)+1);
            
            }
        }
        
        return stagione;
    }
}
