/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;


import com.mycompany.hibernate.*;
import java.util.*;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.*;

/**
 *
 * @author berto
 */
public class EventiOrganizzatore extends GeneralGrafic{
    protected final VBox graficaPrincipale;
    protected final HBox lineaTitolo;
    
    protected final HBox lineaModificaElimina;
    protected final HBox lineaTornaIndietro;
    protected final HBox lineaModificaEliminaPulsantiera;
    protected final Label labelEventiInseriti;
    protected final Hyperlink hyperlinkTornaIndietro;
    protected final TabellaVisualeEvento tabellaEvento;
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
        graficaPrincipale= new VBox();
        lineaTitolo = new HBox();
        lineaTornaIndietro = new HBox();
        lineaModificaElimina = new HBox();
        
        lineaModificaEliminaPulsantiera=new HBox();
        tabellaEvento = new TabellaVisualeEvento();
        labelEventiInseriti = new Label();
        hyperlinkTornaIndietro = new Hyperlink();
        labelIdEvento=new Label();
        idEvento=new TextField();
        labelNumeroPartecipanti=new Label();
        numeroPartecipanti=new TextField();
        modificaEvento=new Button();
        eliminaEvento=new Button();
        InizializzaGrafica();
        //indica che l'array � vuoto
        eventoId=-1;
        partecipanti=0;
    }
    
    private void InizializzaGrafica()
    {
        InizializzazioneElementiGrafica();
        
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);
        
        BorderPane.setAlignment(graficaPrincipale, Pos.CENTER);
        lineaTitolo.setAlignment(Pos.CENTER);
        
        lineaModificaElimina.setAlignment(Pos.CENTER);
        lineaModificaEliminaPulsantiera.setAlignment(Pos.CENTER);
        lineaTornaIndietro.setAlignment(Pos.CENTER);
        setCenter(graficaPrincipale);
        
        lineaTitolo.getChildren().addAll(labelEventiInseriti);

        lineaModificaElimina.getChildren().addAll(idEvento,numeroPartecipanti);
        lineaModificaElimina.setSpacing(15.0);
        lineaModificaEliminaPulsantiera.setSpacing(15.0);
        lineaModificaEliminaPulsantiera.getChildren().addAll(modificaEvento,eliminaEvento);
        lineaModificaEliminaPulsantiera.setSpacing(15.0);
        lineaTornaIndietro.getChildren().addAll(hyperlinkTornaIndietro);
        
        graficaPrincipale.getChildren().addAll(lineaTitolo,tabellaEvento,lineaModificaElimina,lineaModificaEliminaPulsantiera,lineaTornaIndietro);
        graficaPrincipale.setSpacing(15);
        BorderPane.setMargin(graficaPrincipale, new Insets(40,100,30,150));

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
        hyperlinkTornaIndietro.setOnAction((ActionEvent e) -> {GraficLoader.Loader(this, new CreazioneEvento(), mainGroup );});
        //partecipante come parametro serve per indirizzare nella giusta query
        
        ev = GestioneEventiManagerEM.ricercaEventi(organizzatore);
        
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
            if("".equals(newValue)){
                eventoId=-1;
                eliminaEvento.setDisable(true);
                modificaEvento.setDisable(true);
                return;
            }
            try{
                for(int i=0;i<ev.size();i++){
                    if(Integer.parseInt(newValue)==ev.get(i).getId()){
                        eventoId=Integer.parseInt(newValue);
                        break;
                    }                    
                    else
                        eventoId=-1;
                }
                //si tratta di aggiungere 1 per evitare lo 0
                if(eventoId!=-1){                   
                    eliminaEvento.setDisable(false); 
                    if(partecipanti>0)
                        modificaEvento.setDisable(false);
                }else{
                   eliminaEvento.setDisable(true);
                   modificaEvento.setDisable(true);
                }
                    
            }catch (NumberFormatException ex ){
                idEvento.setText("");
                eliminaEvento.setDisable(true);
                modificaEvento.setDisable(true);
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
                partecipanti=Integer.parseInt(newValue);
                // sempre perch� indice di array
                if(eventoId==-1){
                    return;
                }
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
        
        if(eventoId==-1 || partecipanti==0 ){
            numeroPartecipanti.setText("");
            idEvento.setDisable(true);
            idEvento.setText("");
            modificaEvento.setDisable(true);
            return;
        }
        
        for (Iterator<EventoDb> it = organizzatore.getEventiCreati().iterator(); it.hasNext(); ) {
            EventoDb f = it.next();
            System.out.println(f.getId()+" "+eventoId);
            if(f.getId()==eventoId){
                if(partecipanti<f.getNumero_partecipanti())
                    break;
                f.setPosti(partecipanti);
                GestioneOperazioniOrganizzatoreEM.modificaEvento(f.getId(),partecipanti);
                break;
            }
        }
        
        numeroPartecipanti.setText("");
        partecipanti=0;
        eventoId=-1;
        idEvento.setText("");
        eliminaEvento.setDisable(true);
        modificaEvento.setDisable(true);
        ev = GestioneEventiManagerEM.ricercaEventi(organizzatore);
        
        tabellaEvento.aggiornaTabellaEventi(ev);
    }

    private void gestisciEventoElimina() {
        if(eventoId==-1){
            numeroPartecipanti.setText("");
            idEvento.setText("");
            partecipanti=0;
            eliminaEvento.setDisable(true);
            modificaEvento.setDisable(true);
            return;
        }
        
        for (Iterator<EventoDb> it = organizzatore.getEventiCreati().iterator(); it.hasNext(); ) {
            EventoDb f = it.next();
            it.remove();
            if(f.getId()==eventoId){
                GestioneOperazioniOrganizzatoreEM.eliminaEvento(f.getId());
                //organizzatore.removeEvento(f);
                break;
            }
            
        }
        numeroPartecipanti.setText("");
        idEvento.setText("");
        partecipanti=0;
        eventoId=-1;
        eliminaEvento.setDisable(true);
        modificaEvento.setDisable(true);
        ev = GestioneEventiManagerEM.ricercaEventi(organizzatore);
        
        tabellaEvento.aggiornaTabellaEventi(ev);
    }
}
