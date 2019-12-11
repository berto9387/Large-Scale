/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;


import static com.mycompany.gestioneeventi.GeneralGrafic.mainGroup;
import com.mycompany.hibernate.EventoDb;
import com.mycompany.hibernate.GestioneOperazioniPartecipanteEM;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
/**
 *
 * @author berto
 */
public class RicercaEventi extends GeneralGrafic{

    protected final Label labelRicercaEventi;
    protected final TextField textFieldCittaDaCercare;
    protected final Label labelRicercaCitta;
    protected final Button buttonRicerca;
    protected final Hyperlink hyperlinkTornaIndietro;
    protected final Label labelIdEvento;
    protected final TextField textFieldIdEvento;
    protected final Button buttonIscriviti;
    protected final VBox graficaPrincipale;
    protected final TabellaVisualeEvento tabellaEvento;
    private ArrayList<Evento> ev;
    

    public RicercaEventi() {
       
        tabellaEvento = new TabellaVisualeEvento();
        graficaPrincipale = new VBox();
        labelRicercaEventi = new Label();
        textFieldCittaDaCercare = new TextField();
        labelRicercaCitta = new Label();
        buttonRicerca = new Button();
        hyperlinkTornaIndietro = new Hyperlink();
        labelIdEvento = new Label();
        textFieldIdEvento = new TextField();
        buttonIscriviti = new Button();
        ev=new ArrayList<>();
        InizializzaGrafica();
    }
    
    private void InizializzaGrafica()
    {
        InizializzazioneElementiGrafica();
        HBox LineaTitolo =new HBox();
        LineaTitolo.getChildren().addAll(labelRicercaEventi);
        LineaTitolo.setAlignment(Pos.CENTER);
        
        HBox LineaCampoRicercaCitta = new HBox();
        LineaCampoRicercaCitta.getChildren().addAll(labelRicercaCitta,textFieldCittaDaCercare,buttonRicerca);
        LineaCampoRicercaCitta.setSpacing(15);
        
        HBox LineaIscrizioneEvento = new HBox();
        LineaIscrizioneEvento.getChildren().addAll(labelIdEvento,textFieldIdEvento,buttonIscriviti);
        LineaIscrizioneEvento.setSpacing(15);
        
        graficaPrincipale.getChildren().addAll(LineaTitolo,LineaCampoRicercaCitta, tabellaEvento, LineaIscrizioneEvento,hyperlinkTornaIndietro);
        graficaPrincipale.setSpacing(20);
        setCenter(graficaPrincipale);
        BorderPane.setMargin(graficaPrincipale, new Insets(30,20,30,150));
        
        
    }
    
    
    private void GestioneEventoIscrizione() 
    {
        
        if(textFieldIdEvento.getText().equals(""))
        {
            return;
        }
        EventoDb evt=GestioneOperazioniPartecipanteEM.trovaEvento(textFieldIdEvento.getText());
        partecipante.addBook(evt);
        GestioneOperazioniPartecipanteEM.iscrizioneEvento(partecipante);
        //partecipante=GestioneOperazioniPartecipanteEM.trovaPartecipante(partecipante.getId());
        
        
        Evento e=null;
        for(int i=0;i<ev.size();i++){
            if(ev.get(i).getId()==evt.getId()){
                e=ev.get(i);
                break;
            }
        }
        if(e==null)
            return;
        Options options = new Options();
        options.createIfMissing(true);
        try {
            levelDBStore = factory.open(new File("eventi"), options);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RicercaEventi.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String partOfKey="Evento:"+evt.getId()+":";
            String data=partOfKey+"data";
            String descrizione=partOfKey+"descrizione";
            String luogo=partOfKey+"luogo";
            String nome=partOfKey+"nome";
            String numeroPartecipanti=partOfKey+"numero_partecipanti";
            String ora=partOfKey+"ora";
            String posti=partOfKey+"posti";
            String tipologia=partOfKey+"tipologia";
            String idOrganizzatore=partOfKey+"id_Organizzatore";
            levelDBStore.delete(bytes(data));
            levelDBStore.delete(bytes(descrizione));
            levelDBStore.delete(bytes(luogo));
            levelDBStore.delete(bytes(nome));
            levelDBStore.delete(bytes(numeroPartecipanti));
            levelDBStore.delete(bytes(ora));
            levelDBStore.delete(bytes(posti));
            levelDBStore.delete(bytes(tipologia));
            levelDBStore.delete(bytes(idOrganizzatore));
        } finally {
            try {
                levelDBStore.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(RicercaEventi.class.getName()).log(Level.SEVERE, null, ex);
            }

        }      
        ev.remove(e);
        tabellaEvento.aggiornaTabellaEventi(ev);   
    }
    
    private void GestioneEventoRicerca()
    {
            
        ev.clear();
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
        try {
            Date data = null;
            String descrizione = null;
            String luogo = null;
            String nome = null;
            Integer numeroPartecipanti = null;
            String ora = null;
            Integer posti = null;
            String tipologia = null;
            Integer idOrganizzatore = null;
            for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
              
                String key = asString(iterator.peekNext().getKey());
                String value = asString(iterator.peekNext().getValue());
                String[] dividiKey=key.split(":");
                Integer id=Integer.parseInt(dividiKey[1]);

                if(dividiKey[2].equals("data")){
                    System.out.println(dividiKey[2]);
                    data=Date.valueOf(value);                    
                } else if(dividiKey[2].equals("descrizione")){
                    System.out.println(dividiKey[2]);
                    descrizione=value;
                } else if(dividiKey[2].equals("luogo")){
                    System.out.println(dividiKey[2]);
                    luogo=value;
                }else if(dividiKey[2].equals("nome")){
                    System.out.println(dividiKey[2]);
                    nome=value;
                }else if(dividiKey[2].equals("numero_partecipanti")){
                    System.out.println(dividiKey[2]);
                    numeroPartecipanti=Integer.parseInt(value);
                }else if(dividiKey[2].equals("ora")){
                    System.out.println(dividiKey[2]);
                    ora=value;
                }else if(dividiKey[2].equals("posti")){
                    System.out.println(dividiKey[2]);
                    posti=Integer.parseInt(value);
                }else if(dividiKey[2].equals("tipologia")){
                    System.out.println(dividiKey[2]);
                    tipologia=value;
                }else if(dividiKey[2].equals("id_Organizzatore")){
                    System.out.println(dividiKey[2]);
                    idOrganizzatore=Integer.parseInt(value);
                }
                if(textFieldCittaDaCercare.getText().equals("") || textFieldCittaDaCercare.getText().equals(luogo)){
                    if(data!=null && nome!=null && luogo!=null && ora!=null && posti!=null && tipologia!=null && descrizione!=null && idOrganizzatore!=null && numeroPartecipanti!=null){
                        System.out.println(data+nome+luogo+ora+posti+tipologia+descrizione+idOrganizzatore+numeroPartecipanti);
                        
                        ev.add(new Evento(id, nome, luogo, data, ora, posti, tipologia, descrizione, idOrganizzatore, numeroPartecipanti));
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
                    
                }
          }
        } finally {
            try {
                // Make sure you close the iterator to avoid resource leaks.
                iterator.close();
                levelDBStore.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(RicercaEventi.class.getName()).log(Level.SEVERE, null, ex);
            }

        }           
        //ev=GestioneOperazioniPartecipanteEM.ricercaEventi(partecipante , textFieldCittaDaCercare.getText());           
        tabellaEvento.aggiornaTabellaEventi(ev);

    }
    
    private void InizializzazioneElementiGrafica()
    {
 
        labelRicercaEventi.setAlignment(javafx.geometry.Pos.CENTER);
        labelRicercaEventi.setPrefHeight(39.0);
        labelRicercaEventi.setPrefWidth(189.0);
        labelRicercaEventi.setText("RICERCA EVENTI");
        labelRicercaEventi.setFont(new Font(18.0));
        
        labelRicercaCitta.setPrefHeight(17.0);
        labelRicercaCitta.setPrefWidth(114.0);
        labelRicercaCitta.setText("Ricerca per città :");
        labelRicercaCitta.setFont(new Font(14.0));
        
        buttonRicerca.setLayoutX(440.0);
        buttonRicerca.setLayoutY(105.0);
        buttonRicerca.setMnemonicParsing(false);
        buttonRicerca.setPrefHeight(7.0);
        buttonRicerca.setPrefWidth(118.0);
        buttonRicerca.setText("RICERCA");
        buttonRicerca.setFont(new Font(13.0));
        
        buttonRicerca.setOnAction((ActionEvent e) -> {GestioneEventoRicerca();});
        
        labelIdEvento.setPrefHeight(17.0);
        labelIdEvento.setPrefWidth(114.0);
        labelIdEvento.setText("ID evento:");
        labelIdEvento.setFont(new Font(14.0));
        
        buttonIscriviti.setMnemonicParsing(false);
        buttonIscriviti.setPrefHeight(7.0);
        buttonIscriviti.setPrefWidth(118.0);
        buttonIscriviti.setText("ISCRIVITI");
        buttonIscriviti.setFont(new Font(13.0));
        buttonIscriviti.setOnAction((ActionEvent e) -> {GestioneEventoIscrizione();});

        hyperlinkTornaIndietro.setText("Torna indietro");
        hyperlinkTornaIndietro.setPrefHeight(26.0);
        hyperlinkTornaIndietro.setPrefWidth(189.0);
        hyperlinkTornaIndietro.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new MenuUtente(), mainGroup );}); 
        
    }
}
