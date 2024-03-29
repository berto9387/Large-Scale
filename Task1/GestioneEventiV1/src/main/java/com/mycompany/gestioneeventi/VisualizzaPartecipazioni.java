package com.mycompany.gestioneeventi;

import com.mycompany.hibernate.EventoDb;
import com.mycompany.hibernate.GestioneOperazioniPartecipanteEM;
import static java.lang.Long.parseLong;
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
public class VisualizzaPartecipazioni extends GeneralGrafic{
//    protected final Label label;
//    protected final Hyperlink hyperlink;
//    protected final Label label1;
//    protected final TextField textField0;
//    protected final Button button0;
//    protected final TabellaVisualeEvento tabellaEvento;
//    //private final ManagerDb insert;
//    private ArrayList<Evento> ev;
    
    protected final Label labelVisualizzaPartecipazioni;
    protected final Hyperlink hyperlinkTornaIndietro;
    protected final Label labelIdEvento;
    protected final TextField textFieldIdEvento;
    protected final Button buttonAnnullaIscrizione;
    protected final VBox graficaPrincipale;
    protected final TabellaVisualeEvento tabellaEvento;
    private ArrayList<Evento> ev;
    
    public VisualizzaPartecipazioni() {
        
        tabellaEvento = new TabellaVisualeEvento();
        graficaPrincipale = new VBox();
        labelVisualizzaPartecipazioni = new Label();
        hyperlinkTornaIndietro = new Hyperlink();
        labelIdEvento = new Label();
        textFieldIdEvento = new TextField();
        buttonAnnullaIscrizione = new Button();

        InizializzaGrafica();
    }
    
     private void InizializzaGrafica()
    {
        InizializzazioneElementiGrafica();
        
        HBox LineaTitolo =new HBox();
        LineaTitolo.getChildren().addAll(labelVisualizzaPartecipazioni);
        LineaTitolo.setAlignment(Pos.CENTER);
        
        HBox LineaAnnullaIscrizione = new HBox();
        LineaAnnullaIscrizione.getChildren().addAll(labelIdEvento,textFieldIdEvento,buttonAnnullaIscrizione);
        LineaAnnullaIscrizione.setSpacing(15);
        
        graficaPrincipale.getChildren().addAll(LineaTitolo,tabellaEvento,LineaAnnullaIscrizione,hyperlinkTornaIndietro);
        graficaPrincipale.setSpacing(20);
        setCenter(graficaPrincipale);
        BorderPane.setMargin(graficaPrincipale, new Insets(30,20,30,200));
        
    }
     
    private void InizializzazioneElementiGrafica()
    {
 
        labelVisualizzaPartecipazioni.setAlignment(javafx.geometry.Pos.CENTER);
        labelVisualizzaPartecipazioni.setPrefHeight(39.0);
        labelVisualizzaPartecipazioni.setPrefWidth(189.0);
        labelVisualizzaPartecipazioni.setText("VISUALIZZA PARTECIPAZIONI");
        labelVisualizzaPartecipazioni.setFont(new Font(18.0));
        
        ev=GestioneOperazioniPartecipanteEM.ricercaPrenotazioni(partecipante);
        tabellaEvento.aggiornaTabellaEventi(ev);
        
        labelIdEvento.setPrefHeight(17.0);
        labelIdEvento.setPrefWidth(114.0);
        labelIdEvento.setText("ID evento:");
        labelIdEvento.setFont(new Font(14.0));
        
        buttonAnnullaIscrizione.setMnemonicParsing(false);
        buttonAnnullaIscrizione.setPrefHeight(7.0);
        buttonAnnullaIscrizione.setPrefWidth(118.0);
        buttonAnnullaIscrizione.setText("CANCELLA");
        buttonAnnullaIscrizione.setFont(new Font(13.0));
        buttonAnnullaIscrizione.setOnAction((ActionEvent e) -> {GestioneAnnullaIscrizione();});

        hyperlinkTornaIndietro.setText("Torna indietro");
        hyperlinkTornaIndietro.setPrefHeight(26.0);
        hyperlinkTornaIndietro.setPrefWidth(189.0);
        hyperlinkTornaIndietro.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new MenuUtente(), mainGroup );}); 
        
    }
    
    private void GestioneAnnullaIscrizione()
    {
        if("".equals(textFieldIdEvento.getText()))
        {
            return;
        }
        
        System.out.println( "ID VISTOOOOOOOOOOOOOOOO: " + parseLong(textFieldIdEvento.getText()));
        
        EventoDb evento = null;
        for(EventoDb evt:partecipante.getBook()){
            System.out.println("--------> " + evt.getId() + "<----------") ; 
            if(evt.getId()== parseLong(textFieldIdEvento.getText())){
              partecipante.removeBook(evt);
              evento = evt;
              break;
            }              
        }
        //anche se alla riga 107 rimuovo il book,alla riga 113 sembra essere ripristinato, quindi è necessario riaggiornare
        GestioneOperazioniPartecipanteEM.annullaIscrizioneEvento(partecipante, evento);
        partecipante=GestioneOperazioniPartecipanteEM.trovaPartecipante(partecipante.getId());
        ev = GestioneOperazioniPartecipanteEM.ricercaPrenotazioni(partecipante);
        tabellaEvento.aggiornaTabellaEventi(ev);   
    }
}
    
//    public VisualizzaPartecipazioni() {
//        //this.insert = new ManagerDb();
//        tabellaEvento = new TabellaVisualeEvento();
//        
//        label = new Label();
//        hyperlink = new Hyperlink();
//        label1 = new Label();
//        textField0 = new TextField();
//        button0 = new Button();
//     
//        label.setAlignment(javafx.geometry.Pos.CENTER);
//        label.setLayoutX(218.0);
//        label.setLayoutY(42.0);
//        label.setPrefHeight(39.0);
//        label.setPrefWidth(189.0);
//        label.setText("VISUALIZZA PARTECIPAZIONI");
//        label.setFont(new Font(18.0));
//        
//        label1.setLayoutX(114.0);
//        label1.setLayoutY(420.0);
//        label1.setPrefHeight(17.0);
//        label1.setPrefWidth(114.0);
//        label1.setText("ID evento:");
//        label1.setFont(new Font(14.0));
//        
//        textField0.setLayoutX(238.0);
//        textField0.setLayoutY(415.0);
//        
//        button0.setLayoutX(440.0);
//        button0.setLayoutY(415.0);
//        button0.setMnemonicParsing(false);
//        button0.setPrefHeight(7.0);
//        button0.setPrefWidth(200.0);
//        button0.setText("ANNULLA PARTECIPAZIONE");
//        button0.setFont(new Font(13.0));
//        button0.setOnAction((ActionEvent e) -> {
//            
//            if("".equals(textField0.getText())&&utente.organizzatore==false){
//                return;
//            }
//            GestioneOperazioniDbLatoPartecipante.annullaIscrizioneEvento(utente.id, Integer.parseInt(textField0.getText()));
//            ev=GestioneOperazioniDbLatoPartecipante.ricercaPrenotazioni(utente.id, utente.organizzatore);
//            tabellaEvento.aggiornaTabellaEventi(ev);
//            //olEvento = FXCollections.observableArrayList(ev);
//            //twEvento.setItems(olEvento);
//            
//        });
//
//        hyperlink.setText("Torna indietro");
//        hyperlink.setLayoutX(275.0);
//        hyperlink.setLayoutY(460.0);
//        hyperlink.setPrefHeight(26.0);
//        hyperlink.setPrefWidth(189.0);
//        hyperlink.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new MenuUtente(), mainGroup );});
//        //partecipante come parametro serve per indirizzare nella giusta query
//        ev=GestioneOperazioniPartecipanteEM.ricercaPrenotazioni(partecipante);
//        tabellaEvento.aggiornaTabellaEventi(ev);
//
//        getChildren().add(tabellaEvento);
//        getChildren().add(label);
//        getChildren().add(hyperlink);
//        getChildren().addAll(label1, textField0, button0);
//        
//    }
//}
