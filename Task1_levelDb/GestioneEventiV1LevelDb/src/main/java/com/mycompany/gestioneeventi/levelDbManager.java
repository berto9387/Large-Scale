/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import static com.mycompany.gestioneeventi.GeneralGrafic.popolaLevelDb;
import com.mycompany.hibernate.EventoDb;
import com.mycompany.hibernate.PartecipanteDb;
import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;

/**
 *
 * @author tony_
 */
public class levelDbManager {
    protected static DB levelDBStore;
    private static Date data;
    private static String descrizione;
    private static String luogo;
    private static String nome;
    private static Integer numeroPartecipanti;
    private static String ora;
    private static Integer posti;
    private static String tipologia;
    private static Integer idOrganizzatore;
       
    private static void setNullAllEventAttributes()
    {
        data = null;
        descrizione = null;
        luogo = null;
        nome = null;
        numeroPartecipanti = null;
        ora = null;
        posti = null;
        tipologia = null;
        idOrganizzatore = null;
    }
    
    private static void addEventAtList(Integer id,PartecipanteDb partecipante,ArrayList<Evento> ev)
    {
    
        System.out.println(""+id+data+nome+luogo+ora+posti+tipologia+descrizione+idOrganizzatore+numeroPartecipanti);
                    
        boolean trovato = false;
        for (EventoDb evento : partecipante.getBook()) {
            if(evento.getId() == id)
                 trovato = true;     //Controllo se il partecipante � gi� iscritto all'evento
                }
            if(trovato == false)    //Se non � iscritto, aggiungo l'evento alla lista, altrimenti no
                ev.add(new Evento(id, nome, luogo, data, ora, posti, tipologia, descrizione, idOrganizzatore, numeroPartecipanti));
                setNullAllEventAttributes();
    
    } 
            
    private static void SetAnEventAttributeFromKeyValue(String[] dividiKey,String value)
    {
                if(dividiKey[3].equals("data")){
                    //System.out.println(dividiKey[2]);
                    data=Date.valueOf(value);                    
                } else if(dividiKey[3].equals("descrizione")){
                    //System.out.println(dividiKey[2]);
                    descrizione=value;
                } else if(dividiKey[3].equals("nome")){
                    //System.out.println(dividiKey[2]);
                    nome=value;
                }else if(dividiKey[3].equals("numero_partecipanti")){
                    //System.out.println(dividiKey[2]);
                    numeroPartecipanti=Integer.parseInt(value);
                }else if(dividiKey[3].equals("ora")){
                    //System.out.println(dividiKey[2]);
                    ora=value;
                }else if(dividiKey[3].equals("posti")){
                    System.out.println(dividiKey[2]);
                    posti=Integer.parseInt(value);
                }else if(dividiKey[3].equals("tipologia")){
                   // System.out.println(dividiKey[2]);
                    tipologia=value;
                }else if(dividiKey[3].equals("id_Organizzatore")){
                    //System.out.println(dividiKey[2]);
                    idOrganizzatore=Integer.parseInt(value);
                }
    
    }
    
    static ArrayList<Evento> RicercaEventi(String citta,PartecipanteDb partecipante) {
        ArrayList<Evento> ev = new ArrayList<>(); // Variabile in cui ci andr� il risultato
        try {
            //prova levelDb
            popolaLevelDb.join();
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(RicercaEventi.class.getName()).log(Level.SEVERE, null, ex);
        }
        Options options = new Options();
        options.createIfMissing(true);
        try {
            levelDBStore = factory.open(new File("eventi"), options);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RicercaEventi.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBIterator iterator = levelDBStore.iterator();
        setNullAllEventAttributes();
        String argSeek;
        if(citta.equals("")){
            argSeek = "Evento:";
        } else{
            argSeek = "Evento:"+citta;
        }
        iterator.seek(bytes(argSeek));
        try{
            while(iterator.hasNext()){
                String key = asString(iterator.peekNext().getKey());
                String value = asString(iterator.peekNext().getValue());
                String[] dividiKey=key.split(":");
                Integer id=Integer.parseInt(dividiKey[2]);
                luogo = dividiKey[1];
                SetAnEventAttributeFromKeyValue(dividiKey,value);
                if(id !=null && data!=null && nome!=null && luogo!=null && ora!=null && posti!=null && tipologia!=null && descrizione!=null && idOrganizzatore!=null && numeroPartecipanti!=null){
                    addEventAtList(id,partecipante,ev);
                }
                iterator.next();
            }
          }finally {
            try {
                // Make sure you close the iterator to avoid resource leaks.
                iterator.close();
                levelDBStore.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(RicercaEventi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }            
        return ev;
    }
    
}
