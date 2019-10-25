/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.util.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 *
 * @author berto
 */
public class VisualizzaPartecipazioni extends GeneralGrafic{
    protected final Label label;
    protected final Hyperlink hyperlink;
    protected final Label label1;
    protected final TextField textField0;
    protected final Button button0;
    protected final TabellaVisualeEvento tabellaEvento;
    //private final ManagerDb insert;
    private ArrayList<Evento> ev;
    
    public VisualizzaPartecipazioni() {
        //this.insert = new ManagerDb();
        tabellaEvento = new TabellaVisualeEvento();
        
        label = new Label();
        hyperlink = new Hyperlink();
        label1 = new Label();
        textField0 = new TextField();
        button0 = new Button();
     
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(218.0);
        label.setLayoutY(42.0);
        label.setPrefHeight(39.0);
        label.setPrefWidth(189.0);
        label.setText("VISUALIZZA PARTECIPAZIONI");
        label.setFont(new Font(18.0));
        
        label1.setLayoutX(114.0);
        label1.setLayoutY(420.0);
        label1.setPrefHeight(17.0);
        label1.setPrefWidth(114.0);
        label1.setText("ID evento:");
        label1.setFont(new Font(14.0));
        
        textField0.setLayoutX(238.0);
        textField0.setLayoutY(415.0);
        
        button0.setLayoutX(440.0);
        button0.setLayoutY(415.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(7.0);
        button0.setPrefWidth(200.0);
        button0.setText("ANNULLA PARTECIPAZIONE");
        button0.setFont(new Font(13.0));
        button0.setOnAction((ActionEvent e) -> {
            
            if("".equals(textField0.getText())&&utente.organizzatore==false){
                return;
            }
            GestioneOperazioniDbLatoPartecipante.annullaIscrizioneEvento(utente.id, Integer.parseInt(textField0.getText()));
            ev=GestioneOperazioniDbLatoPartecipante.ricercaPrenotazioni(utente.id, utente.organizzatore);
            tabellaEvento.aggiornaTabellaEventi(ev);
            //olEvento = FXCollections.observableArrayList(ev);
            //twEvento.setItems(olEvento);
            
        });

        hyperlink.setText("Torna indietro");
        hyperlink.setLayoutX(275.0);
        hyperlink.setLayoutY(460.0);
        hyperlink.setPrefHeight(26.0);
        hyperlink.setPrefWidth(189.0);
        hyperlink.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new MenuUtente(), mainGroup );});
        //partecipante come parametro serve per indirizzare nella giusta query
        ev=GestioneOperazioniDbLatoPartecipante.ricercaPrenotazioni(utente.id, utente.organizzatore);
        tabellaEvento.aggiornaTabellaEventi(ev);

        getChildren().add(tabellaEvento);
        getChildren().add(label);
        getChildren().add(hyperlink);
        getChildren().addAll(label1, textField0, button0);
        
    }
}
