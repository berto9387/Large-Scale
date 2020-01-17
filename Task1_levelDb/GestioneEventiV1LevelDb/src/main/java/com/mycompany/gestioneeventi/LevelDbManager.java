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
public class LevelDbManager {
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
    
                    
        boolean trovato = false;
        for (EventoDb evento : partecipante.getBook()) {
            if(evento.getId() == id)
                 trovato = true;     //Controllo se il partecipante è già iscritto all'evento
        }
        if(trovato == false)    //Se non è iscritto, aggiungo l'evento alla lista, altrimenti no
            ev.add(new Evento(id, nome, luogo, data, ora, posti, tipologia, descrizione, idOrganizzatore, numeroPartecipanti));
        setNullAllEventAttributes();
    
    } 
            
    private static void SetAnEventAttributeFromKeyValue(String[] dividiKey,String value)
    {
                if(dividiKey[3].equals("data")){
                    data=Date.valueOf(value);                    
                } else if(dividiKey[3].equals("descrizione")){
                    descrizione=value;
                } else if(dividiKey[3].equals("nome")){
                    nome=value;
                }else if(dividiKey[3].equals("numero_partecipanti")){
                    numeroPartecipanti=Integer.parseInt(value);
                }else if(dividiKey[3].equals("ora")){
                    ora=value;
                }else if(dividiKey[3].equals("posti")){
                    posti=Integer.parseInt(value);
                }else if(dividiKey[3].equals("tipologia")){
                    tipologia=value;
                }else if(dividiKey[3].equals("id_Organizzatore")){
                    idOrganizzatore=Integer.parseInt(value);
                }
    
    }
    
    public static ArrayList<Evento> RicercaEventi(String citta,PartecipanteDb partecipante,boolean TestDAccesso) {
        ArrayList<Evento> ev = new ArrayList<>(); // Variabile in cui ci andrà il risultato
        try {
            //prova levelDb
            if(!TestDAccesso)
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
                if(!citta.equals("")&&!citta.equals(luogo)){
                    break;
                }
                
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
