/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;


import static com.mycompany.gestioneeventi.GeneralGrafic.mainGroup;
import java.util.ArrayList;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;

/**
 *
 * @author berto
 */
public class RicercaEventi extends GeneralGrafic{

    protected final Label label;
    protected final TextField textField;
    protected final Label label0;
    protected final Button button;
    protected final Hyperlink hyperlink;
    protected final Label label1;
    protected final TextField textField0;
    protected final Button button0;

    protected final TabellaVisualeEvento tabellaEvento;

   // private final ManagerDb insert;
    private ArrayList<Evento> ev;
    

    public RicercaEventi() {
       // this.insert = new ManagerDb();
        tabellaEvento = new TabellaVisualeEvento();
        
        
        label = new Label();
        textField = new TextField();
        label0 = new Label();
        button = new Button();
        hyperlink = new Hyperlink();
        label1 = new Label();
        textField0 = new TextField();
        button0 = new Button();

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(218.0);
        label.setLayoutY(42.0);
        label.setPrefHeight(39.0);
        label.setPrefWidth(189.0);
        label.setText("RICERCA EVENTI");
        label.setFont(new Font(18.0));
        
        label0.setLayoutX(114.0);
        label0.setLayoutY(110.0);
        label0.setPrefHeight(17.0);
        label0.setPrefWidth(114.0);
        label0.setText("Ricerca per città:");
        label0.setFont(new Font(14.0));
        
        textField.setLayoutX(238.0);
        textField.setLayoutY(105.0);
        
        button.setLayoutX(440.0);
        button.setLayoutY(105.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(7.0);
        button.setPrefWidth(118.0);
        button.setText("RICERCA");
        button.setFont(new Font(13.0));
        
        button.setOnAction((ActionEvent e) -> {
            
            System.err.println(textField.getText());
            if("".equals(textField.getText())&&utente.organizzatore==false){
                return;
            }
            System.err.println(utente.id);
            ev=DAO.ricercaEventi(utente.id, utente.organizzatore, textField.getText());
            tabellaEvento.aggiornaTabellaEventi(ev);
;
            
        });
        
        label1.setLayoutX(114.0);
        label1.setLayoutY(420.0);
        label1.setPrefHeight(17.0);
        label1.setPrefWidth(114.0);
        label1.setText("ID evento:");
        label1.setFont(new Font(14.0));
        
        textField0.setLayoutX(238.0);
        textField0.setLayoutY(415.0);
        
        button0.setLayoutX(440.0);
        button0.setLayoutY(415.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(7.0);
        button0.setPrefWidth(118.0);
        button0.setText("ISCRIVITI");
        button0.setFont(new Font(13.0));
        button0.setOnAction((ActionEvent e) -> {
            
            if("".equals(textField.getText())&&utente.organizzatore==false){
                return;
            }
            GestioneOperazioniDbLatoPartecipante.iscrizioneEvento(Integer.parseInt(textField0.getText()), utente.id);
            System.out.println("id udente: " + utente.id);
            ev=DAO.ricercaEventi(utente.id, utente.organizzatore, textField.getText());
            tabellaEvento.aggiornaTabellaEventi(ev);
           
        });

        hyperlink.setText("Torna indietro");
        hyperlink.setLayoutX(275.0);
        hyperlink.setLayoutY(460.0);
        hyperlink.setPrefHeight(26.0);
        hyperlink.setPrefWidth(189.0);
        hyperlink.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new MenuUtente(), mainGroup );});
        
        getChildren().add(tabellaEvento);
        getChildren().add(label);
        getChildren().add(textField);
        getChildren().add(label0);
        getChildren().add(button);
        getChildren().add(hyperlink);
        getChildren().addAll(label1, textField0, button0);
        
    }
}
