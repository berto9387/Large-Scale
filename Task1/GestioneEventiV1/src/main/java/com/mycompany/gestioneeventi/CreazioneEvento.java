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

import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    public CreazioneEvento() {
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
        buttonEsci.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new LoginPage(),mainGroup );});
        
        buttonVisualizzaEventi.setId("eventi");

        buttonVisualizzaEventi.setMnemonicParsing(false);
        buttonVisualizzaEventi.setText("Visualizza Eventi");
        buttonVisualizzaEventi.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new EventiOrganizzatore(),mainGroup );});

        /*labelNome.setPrefHeight(21.0);
        labelNome.setPrefWidth(96.0);
        labelNome.setText("Nome ");*/

       
       /* labelLuogo.setPrefHeight(21.0);
        labelLuogo.setPrefWidth(96.0);
        labelLuogo.setText("Luogo ");*/


       /* labelData.setPrefHeight(21.0);
        labelData.setPrefWidth(96.0);
        labelData.setText("Data");


        labelOra.setPrefHeight(21.0);
        labelOra.setPrefWidth(96.0);
        labelOra.setText("Ora");


        labelPosti.setPrefHeight(21.0);
        labelPosti.setPrefWidth(96.0);
        labelPosti.setText("Posti");


        labelTipologia.setPrefHeight(21.0);
        labelTipologia.setPrefWidth(96.0);
        labelTipologia.setText("Tipologia");*/


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
        HBox LineaTitolo = new HBox();
        LineaTitolo.getChildren().addAll(labelCreazioneEvento,buttonEsci);
        LineaTitolo.setSpacing(15);
        LineaTitolo.setAlignment(Pos.CENTER);
        HBox LineaPulsantiera = new HBox();
        
        LineaPulsantiera.getChildren().addAll(buttonCrea,buttonVisualizzaEventi);
        LineaPulsantiera.setSpacing(15);
        graficaPrincipale.getChildren().addAll(textFieldNome,textFieldLuogo,textFieldOra,textFieldPosti,
                textFieldTipologia,textFieldData,labelDescrizione,textAreaDescrizione,LineaPulsantiera);
        graficaPrincipale.setSpacing(20);
        setCenter(graficaPrincipale);
        setTop(LineaTitolo);
       
       // BorderPane.setAlignment(buttonEsci, Pos.TOP_RIGHT);
        BorderPane.setMargin(graficaPrincipale, new Insets(30,20,30,200));
    }
    
    private void GestisciEventoCrea()
    {
        {
            int errore=0;
            String Nome=textFieldNome.getText();
            String Luogo=textFieldLuogo.getText();
            String Ora=textFieldOra.getText();
            String sData=textFieldData.getText();
            Date Data=Date.valueOf(sData);
            String Posti=textFieldPosti.getText();
            int intero=Integer.valueOf(Posti);
            String Descrizione=textAreaDescrizione.getText();
            String Tipologia=textFieldTipologia.getText();
            errore=GestioneOperazioniDbLatoOrganizzatore.creaEvento(Nome,Luogo,Data,Ora,intero,Tipologia,Descrizione,utente.id);
            if(errore==0){
                Label label = new Label();
                label.setText("Inserimento Evento non riuscito");
                label.setStyle("-fx-text-fill: red;");
                label.setLayoutX(226.0);
                label.setLayoutY(373.0);
                label.setPrefHeight(17.0);
                label.setPrefWidth(267.0);
                getChildren().add(label);
            }else{
               GraficLoader.Loader(this,new CreazioneEvento(),mainGroup );
            }
        }
    
    
    }
}


