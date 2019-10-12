/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import static com.mycompany.gestioneeventi.GeneralGrafic.utente;
import java.util.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 *
 * @author berto
 */
public class EventiOrganizzatore extends GeneralGrafic{
    protected final Label label;
    protected final Hyperlink hyperlink;
    protected final TabellaVisualeEvento tabellaEvento;
    private final ManagerDb insert;
    private ArrayList<Evento> ev;
    
    public EventiOrganizzatore() {
        this.insert = new ManagerDb();
        tabellaEvento = new TabellaVisualeEvento();
        
        label = new Label();
        hyperlink = new Hyperlink();
     
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(218.0);
        label.setLayoutY(42.0);
        label.setPrefHeight(39.0);
        label.setPrefWidth(189.0);
        label.setText("EVENTI INSERITI");
        label.setFont(new Font(18.0));
        
        

        hyperlink.setText("Torna indietro");
        hyperlink.setLayoutX(275.0);
        hyperlink.setLayoutY(460.0);
        hyperlink.setPrefHeight(26.0);
        hyperlink.setPrefWidth(189.0);
        hyperlink.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new CreazioneEvento(), mainGroup );});
        //partecipante come parametro serve per indirizzare nella giusta query
        ev=insert.ricercaEventi(utente.id, utente.organizzatore,"");
        tabellaEvento.aggiornaTabellaEventi(ev);

        getChildren().add(tabellaEvento);
        getChildren().add(label);
        getChildren().add(hyperlink);
        
    }
}
