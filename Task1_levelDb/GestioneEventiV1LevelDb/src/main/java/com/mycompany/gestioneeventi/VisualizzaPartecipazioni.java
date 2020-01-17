package com.mycompany.gestioneeventi;

import com.mycompany.hibernate.EventoDb;
import com.mycompany.hibernate.GestioneOperazioniPartecipanteEM;
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
        
        for(EventoDb evt:partecipante.getBook()){
            if(evt.getId()==Long.parseLong(textFieldIdEvento.getText())){
              EventoDb evt1=GestioneOperazioniPartecipanteEM.trovaEvento(textFieldIdEvento.getText());  
              partecipante.removeBook(evt1);
              GestioneOperazioniPartecipanteEM.annullaIscrizioneEvento(evt1);
              break;
            }              
        }
        //anche se alla riga 107 rimuovo il book,alla riga 113 sembra essere ripristinato, quindi è necessario riaggiornare
        
        //partecipante=GestioneOperazioniPartecipanteEM.trovaPartecipante(partecipante.getId());
        ev = GestioneOperazioniPartecipanteEM.ricercaPrenotazioni(partecipante);
        tabellaEvento.aggiornaTabellaEventi(ev);   
    }
}
    

