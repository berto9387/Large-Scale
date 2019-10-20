/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import static com.mycompany.gestioneeventi.GeneralGrafic.utente;
import java.util.*;
import javafx.event.*;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 *
 * @author berto
 */
public class EventiOrganizzatore extends GeneralGrafic{
    protected final Label labelEventiInseriti;
    protected final Hyperlink hyperlinkTornaIndietro;
    protected final TabellaVisualeEvento tabellaEvento;
    protected final VBox graficaPrincipale;
    private ArrayList<Evento> ev;
    
    public EventiOrganizzatore() {
        
        tabellaEvento = new TabellaVisualeEvento();
        graficaPrincipale= new VBox();
        labelEventiInseriti = new Label();
        hyperlinkTornaIndietro = new Hyperlink();
        InizializzaGrafica();
        
    }
    
    private void InizializzaGrafica()
    {
        InizializzazioneElementiGrafica();
        HBox lineaTitolo = new HBox();
        lineaTitolo.getChildren().addAll(labelEventiInseriti);
        lineaTitolo.setAlignment(Pos.CENTER);
        
        HBox lineaTornaIndietro = new HBox();
        lineaTornaIndietro = new HBox();
        lineaTornaIndietro.getChildren().addAll(hyperlinkTornaIndietro);
        lineaTornaIndietro.setAlignment(Pos.CENTER);
        
        graficaPrincipale.getChildren().addAll(lineaTitolo,tabellaEvento,lineaTornaIndietro);
        graficaPrincipale.setSpacing(15);
        setCenter(graficaPrincipale);
        BorderPane.setMargin(graficaPrincipale,new Insets(30,20,30,200));
    }
    
    
    private void InizializzazioneElementiGrafica()
    {
             
        labelEventiInseriti.setAlignment(javafx.geometry.Pos.CENTER);
        labelEventiInseriti.setPrefHeight(39.0);
        labelEventiInseriti.setPrefWidth(189.0);
        labelEventiInseriti.setText("EVENTI INSERITI");
        labelEventiInseriti.setFont(new Font(18.0));
        
        hyperlinkTornaIndietro.setText("Torna indietro");
        hyperlinkTornaIndietro.setPrefHeight(26.0);
        hyperlinkTornaIndietro.setPrefWidth(189.0);
        hyperlinkTornaIndietro.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new CreazioneEvento(), mainGroup );});
        //partecipante come parametro serve per indirizzare nella giusta query
        ev=DAO.ricercaEventi(utente.id, utente.organizzatore,"");
        tabellaEvento.aggiornaTabellaEventi(ev);
        
        
    }
}
