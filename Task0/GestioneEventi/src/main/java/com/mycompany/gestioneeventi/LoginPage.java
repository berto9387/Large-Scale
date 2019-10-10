/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.*;
import javafx.scene.text.Font;
import javafx.stage.*;
//import javafx.stage.Stage;

/**
 *
 * @author tony_
 */
public class LoginPage extends GeneralGrafic
{   protected final Label label;
    protected final Button button;
    protected final Label label0;
    protected final Button button0;
    protected final Hyperlink hyperlink;
    protected final TextField textField;
    protected final TextField textField0;

    protected final ManagerDb mdb;

   
   

    public LoginPage() {
        
        label = new Label();
        button = new Button();
        label0 = new Label();
        button0 = new Button();
        hyperlink = new Hyperlink();
        textField = new TextField();
        textField0 = new TextField();
        mdb = new ManagerDb();
        
        Registrazione reg = new Registrazione();

        
        

        setId("AnchorPane");
        setPrefHeight(327.0);
        setPrefWidth(320);
        setStyle("-fx-font.size: 25;");

        label.setLayoutX(126);
        label.setLayoutY(120);
        label.setMinHeight(16);
        label.setMinWidth(69);

        button.setLayoutX(14.0);
        button.setLayoutY(207.0);
        button.setMnemonicParsing(false);
        button.setText("Login partecipante");
        button.setOnAction((ActionEvent ev)->{
             String email = textField.getText();
            String password = textField.getText();
            utente=mdb.loginPartecipante(email, password);
            if(utente==null)
            {
                Label errore = new Label();
                errore.setText("Email o password sbagliati riprova");
                errore.setStyle("-fx-text-fill: red;");
                errore.setLayoutX(126.0);
                errore.setLayoutY(289.0);
                label.setPrefHeight(17.0);
                label.setPrefWidth(267.0);
                getChildren().add(errore);
            }
        });
        
        label0.setAlignment(javafx.geometry.Pos.CENTER);
        label0.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label0.setLayoutX(115.0);
        label0.setLayoutY(25.0);
        label0.setPrefHeight(32.0);
        label0.setPrefWidth(87.0);
        label0.setText("LOGIN");
        label0.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);
        label0.setWrapText(true);
        label0.setFont(new Font(18.0));

        button0.setLayoutX(184.0);
        button0.setLayoutY(207.0);
        button0.setMnemonicParsing(false);
        button0.setText("Login organizzatore");
        button0.setOnAction((ActionEvent ev)->{
            String email = textField.getText();
            String password = textField.getText();
            mdb.loginOrganizzatore(email, password);
            if(utente==null)
            {
                Label errore = new Label();
                errore.setText("Email o password sbagliati riprova");
                errore.setStyle("-fx-text-fill: red;");
                errore.setLayoutX(126.0);
                errore.setLayoutY(289.0);
                label.setPrefHeight(17.0);
                label.setPrefWidth(267.0);
                getChildren().add(errore);
            }else{
                 GraficLoader.Loader(this,new CreazioneEvento(),mainGroup );
            
            }
        });
        
        
        hyperlink.setLayoutX(107.0);
        hyperlink.setLayoutY(269.0);
        hyperlink.setPrefHeight(26.0);
        hyperlink.setPrefWidth(108.0);
        hyperlink.setText("Non sei registrato?");
        hyperlink.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, reg,mainGroup );});
        
        textField.setAlignment(javafx.geometry.Pos.CENTER);
        textField.setLayoutX(85.0);
        textField.setLayoutY(94.0);
        textField.setPromptText("email");

        textField0.setAlignment(javafx.geometry.Pos.CENTER);
        textField0.setLayoutX(85.0);
        textField0.setLayoutY(151.0);
        textField0.setPromptText("password");

        getChildren().add(label);
        getChildren().add(button);
        getChildren().add(label0);
        getChildren().add(button0);
        getChildren().add(hyperlink);
        getChildren().add(textField);
        getChildren().add(textField0);

    }

  /*  private void apriRegistrazione(Stage stage) {
        Registrazione registrazione = new Registrazione();
        Group root = new Group(registrazione); 
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("GestioneEventi");
        stage.setScene(scene);
        stage.show();
    }
    
    */
}
