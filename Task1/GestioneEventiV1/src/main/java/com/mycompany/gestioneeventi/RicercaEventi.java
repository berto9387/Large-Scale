/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;


import static com.mycompany.gestioneeventi.GeneralGrafic.mainGroup;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

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
        graficaPrincipale.getChildren().addAll(LineaTitolo,LineaCampoRicercaCitta,LineaIscrizioneEvento,hyperlinkTornaIndietro);
        graficaPrincipale.setSpacing(20);
        setCenter(graficaPrincipale);
        BorderPane.setMargin(graficaPrincipale, new Insets(30,20,30,200));
    }
    
    private void GestioneEventoIscrizione()
    {
        if("".equals(textFieldCittaDaCercare.getText())&&utente.organizzatore==false)
        {
            return;
        }
        GestioneOperazioniDbLatoPartecipante.iscrizioneEvento(Integer.parseInt(textFieldIdEvento.getText()), utente.id);
        System.out.println("id udente: " + utente.id);
        ev=DAO.ricercaEventi(utente.id, utente.organizzatore, textFieldCittaDaCercare.getText());
        tabellaEvento.aggiornaTabellaEventi(ev);   
    }
    
    private void GestioneEventoRicerca()
    {
            System.err.println(textFieldCittaDaCercare.getText());
            if("".equals(textFieldCittaDaCercare.getText())&&utente.organizzatore==false){
                return;
            }
            System.err.println(utente.id);
            ev=DAO.ricercaEventi(utente.id, utente.organizzatore, textFieldCittaDaCercare.getText());
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
        labelRicercaCitta.setText("Ricerca per città:");
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
