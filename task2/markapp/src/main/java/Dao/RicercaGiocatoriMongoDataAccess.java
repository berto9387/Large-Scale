/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.InformazioniRicercaCalciatore;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
        String regex = "\\b"+nome+"|\\b"+cognome;
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
    
}
