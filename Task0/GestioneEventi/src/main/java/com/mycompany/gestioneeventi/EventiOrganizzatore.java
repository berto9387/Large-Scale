/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import static com.mycompany.gestioneeventi.GeneralGrafic.utente;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    protected final Label labelIdEvento;
    protected final TextField idEvento;
    protected final Label labelNumeroPartecipanti;
    protected final TextField numeroPartecipanti;
    protected final Button modificaEvento;
    protected final Button eliminaEvento;
    private ArrayList<Evento> ev;
    private  int eventoId;
    private int partecipanti;
    
    public EventiOrganizzatore() {
        
        tabellaEvento = new TabellaVisualeEvento();
        graficaPrincipale= new VBox();
        labelEventiInseriti = new Label();
        hyperlinkTornaIndietro = new Hyperlink();
        labelIdEvento=new Label();
        idEvento=new TextField();
        labelNumeroPartecipanti=new Label();
        numeroPartecipanti=new TextField();
        modificaEvento=new Button();
        eliminaEvento=new Button();
        InizializzaGrafica();
        eventoId=0;
        partecipanti=0;
    }
    
    private void InizializzaGrafica()
    {
        InizializzazioneElementiGrafica();
        HBox lineaTitolo = new HBox();
        lineaTitolo.getChildren().addAll(labelEventiInseriti);
        lineaTitolo.setAlignment(Pos.CENTER);
        
        HBox lineaTornaIndietro = new HBox();
        lineaTornaIndietro.getChildren().addAll(hyperlinkTornaIndietro);
        lineaTornaIndietro.setAlignment(Pos.CENTER);
        
        HBox lineaModicaElimina = new HBox();
        lineaModicaElimina.getChildren().addAll(labelIdEvento,idEvento,labelNumeroPartecipanti,numeroPartecipanti,modificaEvento,eliminaEvento);
        lineaModicaElimina.setAlignment(Pos.CENTER);
        lineaModicaElimina.setSpacing(15.0);
        
        graficaPrincipale.getChildren().addAll(lineaTitolo,tabellaEvento,lineaTornaIndietro,lineaModicaElimina);
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
        
        labelIdEvento.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        labelIdEvento.setPrefHeight(39.0);
        labelIdEvento.setPrefWidth(189.0);
        labelIdEvento.setText("Seleziona ID evento");
        labelIdEvento.setFont(new Font(18.0));
        
        
        idEvento.setAlignment(javafx.geometry.Pos.CENTER);
        idEvento.setPromptText("Id evento");
        //evento gestito dal text field idEvento
        idEvento.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            try{
                Integer.parseInt(newValue);
                for(int i=0;i<ev.size();i++){
                    if(Integer.parseInt(newValue)==ev.get(i).getId())
                        eventoId=Integer.parseInt(newValue);
                    else
                        eventoId=0;
                }
                if(eventoId!=0)
                    eliminaEvento.setDisable(false);
                else
                    eliminaEvento.setDisable(true);
            }catch (NumberFormatException ex ){
                idEvento.setText("");
                eliminaEvento.setDisable(true);
            }
        });
        
        labelNumeroPartecipanti.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        labelNumeroPartecipanti.setPrefHeight(39.0);
        labelNumeroPartecipanti.setPrefWidth(189.0);
        labelNumeroPartecipanti.setText("Modifica numero partecipanti");
        labelNumeroPartecipanti.setFont(new Font(18.0));
                
        numeroPartecipanti.setAlignment(javafx.geometry.Pos.CENTER);
        numeroPartecipanti.setPromptText("Numero partecipanti");
        numeroPartecipanti.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            try{
                Integer.parseInt(newValue);
                modificaEvento.setDisable(false);
            }catch (NumberFormatException ex ){
                numeroPartecipanti.setText("");
                modificaEvento.setDisable(true);
            }
        });       
        
        modificaEvento.setMnemonicParsing(false);
        modificaEvento.setDisable(true);
        modificaEvento.setPrefHeight(17.0);
        modificaEvento.setPrefWidth(149.0);
        modificaEvento.setText("MODIFICA");
        
        modificaEvento.setOnAction((ActionEvent e) -> {gestisciEventoModifica();});
                
        
        eliminaEvento.setMnemonicParsing(false);
        eliminaEvento.setDisable(true);
        eliminaEvento.setPrefHeight(17.0);
        eliminaEvento.setPrefWidth(149.0);
        eliminaEvento.setText("ELIMINA");
        
        eliminaEvento.setOnAction((ActionEvent e) -> {gestisciEventoElimina();});
    }

    private void gestisciEventoModifica() {
        if(eventoId==0 || partecipanti==0 || partecipanti<ev.get(eventoId).getNumeroPartecipanti()){
            numeroPartecipanti.setText("");
            idEvento.setDisable(true);
            idEvento.setText("");
            modificaEvento.setDisable(true);
            return;
        }
        
        GestioneOperazioniDbLatoOrganizzatore.modificaEvento(eventoId, partecipanti, utente.id);
        numeroPartecipanti.setText("");
        idEvento.setText("");
        idEvento.setDisable(true);
        modificaEvento.setDisable(true);
    }

    private void gestisciEventoElimina() {
        if(eventoId==0){
            numeroPartecipanti.setText("");
            idEvento.setText("");
            idEvento.setDisable(true);
            modificaEvento.setDisable(true);
            return;
        }
        
        GestioneOperazioniDbLatoOrganizzatore.eliminaEvento(eventoId, utente.id);
        numeroPartecipanti.setText("");
        idEvento.setText("");
        idEvento.setDisable(true);
        modificaEvento.setDisable(true);
    }
}
