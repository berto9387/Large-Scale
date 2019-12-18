/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estraiDati;

/**
 *
 * @author berto
 */
import com.mycompany.gestioneeventi.Evento;
import com.mycompany.gestioneeventi.levelDbManager;
import com.mycompany.hibernate.GestioneOperazioniPartecipanteEM;
import com.mycompany.hibernate.PartecipanteDb;
import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
public class EstraiEvento extends levelDbManager implements Runnable{
    PartecipanteDb partecipante;
    
    public EstraiEvento(PartecipanteDb p){
        partecipante = p;
    }
    @Override
    public void run(){
        try {
            System.out.println("-------------->");
            Options options = new Options();
            options.createIfMissing(true);
            levelDBStore = factory.open(new File("eventi"), options);
            
            try {
                System.out.println("-------------->1");
                ArrayList<Evento> ev = GestioneOperazioniPartecipanteEM.ricercaEventi(partecipante , "");
                for(int i=0;i<ev.size();i++){
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String dataStringa = df.format(ev.get(i).getData());
                    String partOfKey="Evento:"+ev.get(i).getLuogo()+ ":" +ev.get(i).getId()+":";
                    String data=partOfKey+"data";
                    String descrizione=partOfKey+"descrizione";
                    //String luogo=partOfKey+"luogo";
                    String nome=partOfKey+"nome";
                    String numeroPartecipanti=partOfKey+"numero_partecipanti";
                    String ora=partOfKey+"ora";
                    String posti=partOfKey+"posti";
                    String tipologia=partOfKey+"tipologia";
                    String idOrganizzatore=partOfKey+"id_Organizzatore";
                    levelDBStore.put(bytes(data),bytes(dataStringa));
                    levelDBStore.put(bytes(descrizione),bytes(ev.get(i).getDescrizione()));
                    //levelDBStore.put(bytes(luogo),bytes(ev.get(i).getLuogo()));
                    levelDBStore.put(bytes(nome),bytes(ev.get(i).getNome()));
                    levelDBStore.put(bytes(numeroPartecipanti),bytes(String.valueOf(ev.get(i).getNumeroPartecipanti())));
                    levelDBStore.put(bytes(ora),bytes(ev.get(i).getOra()));
                    levelDBStore.put(bytes(posti),bytes(String.valueOf(ev.get(i).getPosti())));
                    levelDBStore.put(bytes(tipologia),bytes(ev.get(i).getTipologia()));
                    levelDBStore.put(bytes(idOrganizzatore),bytes(String.valueOf(ev.get(i).getOrganizzatore())));
                    
                    System.out.println("-------------->" + numeroPartecipanti);
                }
                
            } finally {
                // Make sure you close the db to shutdown the
                // database and avoid resource leaks.
                levelDBStore.close();
            }
        }   catch (IOException ex) {
            java.util.logging.Logger.getLogger(EstraiEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
