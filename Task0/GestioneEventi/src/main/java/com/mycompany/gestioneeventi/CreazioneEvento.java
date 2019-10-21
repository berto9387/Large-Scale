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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;

public class CreazioneEvento extends GeneralGrafic {

    protected final Button buttonCrea;
    protected final Button buttonEsci;
    protected final Button buttonVisualizzaEventi;
    protected final Label labelNome;
    protected final TextArea textAreaDescrizione;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final Label label3;
    protected final Label label4;
    protected final Label label5;
    protected final TextField textField;
    protected final TextField textField0;
    protected final TextField textField1;
    protected final TextField textField2;
    protected final TextField textField3;
    protected final Label label6;
    protected final TextField textField4;
    

    public CreazioneEvento() {
        
        buttonCrea = new Button();
        buttonEsci = new Button();
        buttonVisualizzaEventi = new Button();
        labelNome = new Label();
        textAreaDescrizione = new TextArea();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        label5 = new Label();
        textField = new TextField();
        textField0 = new TextField();
        textField1 = new TextField();
        textField2 = new TextField();
        textField3 = new TextField();
        label6 = new Label();
        textField4 = new TextField();

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
        buttonCrea.setOnAction((ActionEvent e) -> {
            int errore=0;
            String Nome=textField.getText();
            String Luogo=textField0.getText();
            String Ora=textField1.getText();
            String sData=textField4.getText();
            Date Data=Date.valueOf(sData);
            String Posti=textField2.getText();
            int intero=Integer.valueOf(Posti);
            String Descrizione=textAreaDescrizione.getText();
            String Tipologia=textField3.getText();
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
        });
        
        label6.setLayoutX(32.0);
        label6.setLayoutY(13.0);
        label6.setPrefHeight(21.0);
        label6.setPrefWidth(379.0);
        label6.setText("CREAZIONE EVENTO");
        label6.setFont(new Font(36.0));

        buttonEsci.setId("esci");
        buttonEsci.setLayoutX(592.0);
        buttonEsci.setLayoutY(24.0);
        buttonEsci.setMnemonicParsing(false);
        buttonEsci.setText("ESCI");
        buttonEsci.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new LoginPage(),mainGroup );});
        
        buttonVisualizzaEventi.setId("eventi");
        buttonVisualizzaEventi.setLayoutX(500.0);
        buttonVisualizzaEventi.setLayoutY(300.0);
        buttonVisualizzaEventi.setMnemonicParsing(false);
        buttonVisualizzaEventi.setText("Visualizza Eventi");
        buttonVisualizzaEventi.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new EventiOrganizzatore(),mainGroup );});

        labelNome.setLayoutX(32.0);
        labelNome.setLayoutY(140.0);
        labelNome.setPrefHeight(21.0);
        labelNome.setPrefWidth(96.0);
        labelNome.setText("Nome ");

        label0.setLayoutX(32.0);
        label0.setLayoutY(184.0);
        label0.setPrefHeight(21.0);
        label0.setPrefWidth(96.0);
        label0.setText("Luogo ");

        label1.setLayoutX(32.0);
        label1.setLayoutY(230.0);
        label1.setPrefHeight(21.0);
        label1.setPrefWidth(96.0);
        label1.setText("Data");

        label2.setLayoutX(32.0);
        label2.setLayoutY(281.0);
        label2.setPrefHeight(21.0);
        label2.setPrefWidth(96.0);
        label2.setText("Ora");

        label3.setLayoutX(32.0);
        label3.setLayoutY(325.0);
        label3.setPrefHeight(21.0);
        label3.setPrefWidth(96.0);
        label3.setText("Posti");

        label4.setLayoutX(32.0);
        label4.setLayoutY(374.0);
        label4.setPrefHeight(21.0);
        label4.setPrefWidth(96.0);
        label4.setText("Tipologia");

        label5.setLayoutX(32.0);
        label5.setLayoutY(415.0);
        label5.setPrefHeight(21.0);
        label5.setPrefWidth(96.0);
        label5.setText("Descrizione");

        textField.setId("nome");
        textField.setLayoutX(128.0);
        textField.setLayoutY(135.0);

        textField0.setId("luogo");
        textField0.setLayoutX(128.0);
        textField0.setLayoutY(179.0);

        textField1.setId("ora");
        textField1.setLayoutX(128.0);
        textField1.setLayoutY(276.0);

        textField2.setId("posti");
        textField2.setLayoutX(128.0);
        textField2.setLayoutY(320.0);

        textField3.setId("tipologia");
        textField3.setLayoutX(128.0);
        textField3.setLayoutY(369.0);

        textField4.setId("data");
        textField4.setLayoutX(128.0);
        textField4.setLayoutY(225.0);
        
        textAreaDescrizione.setId("descrizione");
        textAreaDescrizione.setLayoutX(128.0);
        textAreaDescrizione.setLayoutY(413.0);
        textAreaDescrizione.setPrefHeight(47.0);
        textAreaDescrizione.setPrefWidth(332.0);

        getChildren().add(buttonCrea);
        getChildren().add(buttonEsci);
        getChildren().add(buttonVisualizzaEventi);
        getChildren().add(labelNome);
        getChildren().add(textAreaDescrizione);
        getChildren().add(label0);
        getChildren().add(label1);
        getChildren().add(label2);
        getChildren().add(label3);
        getChildren().add(label4);
        getChildren().add(label5);
        getChildren().add(textField);
        getChildren().add(textField0);
        getChildren().add(textField1);
        getChildren().add(textField2);
        getChildren().add(textField3);
        getChildren().add(label6);
        getChildren().add(textField4);

    }
}


