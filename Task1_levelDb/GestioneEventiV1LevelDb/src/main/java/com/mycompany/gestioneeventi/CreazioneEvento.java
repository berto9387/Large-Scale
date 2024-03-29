/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

/**
 *
 * @author berto
 * 
 */

import com.mycompany.hibernate.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.*;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class CreazioneEvento extends GeneralGrafic {

    protected final Button buttonCrea;
    protected final Button buttonEsci;
    protected final Button buttonVisualizzaEventi;
    protected final Label labelNome;
    protected final TextArea textAreaDescrizione;
    protected final Label labelLuogo;
    protected final Label labelData;
    protected final Label labelOra;
    protected final Label labelPosti;
    protected final Label labelTipologia;
    protected final Label labelDescrizione;
    protected final TextField textFieldNome;
    protected final TextField textFieldLuogo;
    protected final TextField textFieldOra;
    protected final TextField textFieldPosti;
    protected final TextField textFieldTipologia;
    protected final Label labelCreazioneEvento;
    protected final TextField textFieldData;
    protected final VBox graficaPrincipale;
    protected final Label labelErrore;
    public CreazioneEvento() {
        
        labelErrore = new Label();
        graficaPrincipale = new VBox();
        buttonCrea = new Button();
        buttonEsci = new Button();
        buttonVisualizzaEventi = new Button();
        labelNome = new Label();
        textAreaDescrizione = new TextArea();
        labelLuogo = new Label();
        labelData = new Label();
        labelOra = new Label();
        labelPosti = new Label();
        labelTipologia = new Label();
        labelDescrizione = new Label();
        textFieldNome = new TextField();
        textFieldLuogo = new TextField();
        textFieldOra = new TextField();
        textFieldPosti = new TextField();
        textFieldTipologia = new TextField();
        labelCreazioneEvento = new Label();
        textFieldData = new TextField();

        setId("data");
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(495.0);
        setPrefWidth(702.0);

        buttonCrea.setId("crea");
        buttonCrea.setLayoutX(591.0);
        buttonCrea.setLayoutY(426.0);
        buttonCrea.setMnemonicParsing(false);
        buttonCrea.setText("Crea");
        buttonCrea.setOnAction((ActionEvent e) -> {GestisciEventoCrea();});
        

        labelCreazioneEvento.setPrefHeight(21.0);
        labelCreazioneEvento.setPrefWidth(379.0);
        labelCreazioneEvento.setText("CREAZIONE EVENTO");
        labelCreazioneEvento.setFont(new Font(36.0));

        buttonEsci.setId("esci");

        buttonEsci.setMnemonicParsing(false);
        buttonEsci.setText("ESCI");
        buttonEsci.setOnAction((ActionEvent ev) -> {
            GraficLoader.Loader(this, new LoginPage(),mainGroup );
        });
        
        buttonVisualizzaEventi.setId("eventi");

        buttonVisualizzaEventi.setMnemonicParsing(false);
        buttonVisualizzaEventi.setText("Visualizza Eventi");
        buttonVisualizzaEventi.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new EventiOrganizzatore(),mainGroup );});

        labelDescrizione.setPrefHeight(21.0);
        labelDescrizione.setPrefWidth(96.0);
        labelDescrizione.setText("Descrizione");

        textFieldNome.setId("nome");
        textFieldNome.setPromptText("Nome");
        textFieldNome.setAlignment(javafx.geometry.Pos.CENTER);
        
        textFieldLuogo.setId("luogo");

        textFieldLuogo.setPromptText("Luogo");
        textFieldLuogo.setAlignment(javafx.geometry.Pos.CENTER);
        
        textFieldOra.setId("ora");
        textFieldOra.setPromptText("Ora");
        textFieldOra.setAlignment(javafx.geometry.Pos.CENTER);
        

        textFieldPosti.setId("posti");
        textFieldPosti.setPromptText("Posti");
        textFieldPosti.setAlignment(javafx.geometry.Pos.CENTER);

        textFieldTipologia.setId("tipologia");
        textFieldTipologia.setPromptText("Tipologia");
        textFieldTipologia.setAlignment(javafx.geometry.Pos.CENTER);

        textFieldData.setId("data");
        textFieldData.setPromptText("Data");
        textFieldData.setAlignment(javafx.geometry.Pos.CENTER);
        
        textAreaDescrizione.setId("descrizione");
        textAreaDescrizione.setPrefHeight(47.0);
        textAreaDescrizione.setPrefWidth(332.0);
        textAreaDescrizione.setPromptText("Descrizione");
        
        
        InizializzaGrafica();
    }
    
    private void InizializzaGrafica()
    {
        
        
        labelErrore.setText("Inserimento Evento non riuscito");
        labelErrore.setStyle("-fx-text-fill: red;");
        labelErrore.setPrefHeight(17.0);
        labelErrore.setPrefWidth(267.0);
        labelErrore.setVisible(false);
        
        HBox lineaErrore = new HBox();
        lineaErrore.getChildren().add(labelErrore);
        lineaErrore.setAlignment(Pos.CENTER);
        HBox LineaTitolo = new HBox();
        LineaTitolo.getChildren().addAll(labelCreazioneEvento,buttonEsci);
        LineaTitolo.setSpacing(15);
        LineaTitolo.setAlignment(Pos.CENTER);
        HBox LineaPulsantiera = new HBox();
        
        LineaPulsantiera.getChildren().addAll(buttonCrea,buttonVisualizzaEventi);
        LineaPulsantiera.setSpacing(15);
        LineaPulsantiera.setAlignment(Pos.CENTER);
        graficaPrincipale.getChildren().addAll(textFieldNome,textFieldLuogo,textFieldOra,textFieldPosti,
                textFieldTipologia,textFieldData,labelDescrizione,textAreaDescrizione,lineaErrore,LineaPulsantiera);
        graficaPrincipale.setSpacing(20);
        setCenter(graficaPrincipale);
        setTop(LineaTitolo);
       
        BorderPane.setMargin(graficaPrincipale, new Insets(100,100,30,150));
    }
    
    private void GestisciEventoCrea()
    {
            String Nome=textFieldNome.getText();
            String Luogo=textFieldLuogo.getText();
            String Ora=textFieldOra.getText();
            String sData=textFieldData.getText();
            Date Data= Date.valueOf(sData);
            String Posti=textFieldPosti.getText();
            int intero=Integer.valueOf(Posti);
            String Descrizione=textAreaDescrizione.getText();
            String Tipologia=textFieldTipologia.getText();
            EventoDb evento=new EventoDb();
            evento.setNome(Nome);
            evento.setLuogo(Luogo);
            evento.setData(Data);
            evento.setOra(Ora);
            evento.setPosti(intero);
            evento.setTipologia(Tipologia);
            evento.setDescrizione(Descrizione);
            evento.setOrganizzatore(organizzatore);
            int er=GestioneOperazioniOrganizzatoreEM.inserisciEvento(evento);
            organizzatore.addEvento(evento);           
            if(er==0){
                labelErrore.setText("Inserimento Evento non riuscito");
                labelErrore.setVisible(true);
                GraficLoader.Loader(this,new LoginPage(),mainGroup );
            }else{
               GraficLoader.Loader(this,new CreazioneEvento(),mainGroup );
            }
            Iterator<EventoDb> it=organizzatore.getEventiCreati().iterator();
            while(it.hasNext()){
            System.out.println(it.next().getId());
            }
    
    }
}


