package com.mycompany.gestioneeventi;


import java.sql.Date;
import javafx.event.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;

public class Registrazione extends GeneralGrafic {

    protected final TextField textField;
    protected final Label label;
    protected final TextField textField0;
    protected final TextField textField1;
    protected final TextField textField2;
    protected final TextField textField3;
    protected final TextField textField4;
    protected final Button button;
    protected final RadioButton r1;
    protected final RadioButton r2;
    protected final ToggleGroup tg;
    private final ManagerDb insert;
    private final Hyperlink hyperlink;

    
    
    public Registrazione() {
        this.insert = new ManagerDb();
        textField = new TextField();
        label = new Label();
        textField0 = new TextField();
        textField1 = new TextField();
        textField2 = new TextField();
        textField3 = new TextField();
        textField4 = new TextField();
        hyperlink = new Hyperlink();
        
        button = new Button();
        tg = new ToggleGroup();
        r1 = new RadioButton("Organizzatore"); 
        r2 = new RadioButton("Partecipante"); 
        r1.setToggleGroup(tg); 
        r2.setToggleGroup(tg);
        r2.setSelected(true);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(458.0);
        setPrefWidth(600.0);

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(167.0);
        label.setLayoutY(33.0);
        label.setPrefHeight(17.0);
        label.setPrefWidth(267.0);
        label.setText("REGISTRAZIONE");
        label.setFont(new Font(24.0));

        textField.setAlignment(javafx.geometry.Pos.CENTER);
        textField.setLayoutX(226.0);
        textField.setLayoutY(96.0);
        textField.setPromptText("Nome");

        textField0.setAlignment(javafx.geometry.Pos.CENTER);
        textField0.setLayoutX(226.0);
        textField0.setLayoutY(138.0);
        textField0.setPromptText("Cognome");
        
        textField1.setAlignment(javafx.geometry.Pos.CENTER);
        textField1.setLayoutX(226.0);
        textField1.setLayoutY(179.0);
        textField1.setPromptText("Data nascita");

        textField2.setAlignment(javafx.geometry.Pos.CENTER);
        textField2.setLayoutX(226.0);
        textField2.setLayoutY(217.0);
        textField2.setPromptText("Email");

        textField3.setAlignment(javafx.geometry.Pos.CENTER);
        textField3.setLayoutX(226.0);
        textField3.setLayoutY(253.0);
        textField3.setPromptText("Username");

        textField4.setAlignment(javafx.geometry.Pos.CENTER);
        textField4.setLayoutX(226.0);
        textField4.setLayoutY(289.0);
        textField4.setPromptText("Password");

        button.setLayoutX(226.0);
        button.setLayoutY(402.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(17.0);
        button.setPrefWidth(149.0);
        button.setText("REGISTRATI");
        button.setOnAction((ActionEvent e) -> {
            int errore;
            String Nome=textField.getText();
            String Cognome=textField0.getText();
            String sData=textField1.getText();
            Date Data=Date.valueOf(sData);
            String Email=textField2.getText();
            String Phone="3333333333";
            String Password=textField4.getText();
            String Username=textField3.getText();
            RadioButton selectedRadioButton = (RadioButton) tg.getSelectedToggle();
            String Ruolo = selectedRadioButton.getText();
            String Confronta="Partecipante";
            System.err.println(Ruolo.equals(Confronta));
            if(Ruolo.equals(Confronta)){                
                errore=insert.inserisciPartecipante(Nome, Cognome, Data, Email,Username, Phone,Password);
            }else{
               errore=insert.inserisciOrganizzatore(Nome, Cognome, Data, Email,Username, Phone,Password);
            }
            if(errore==0){
                Label label = new Label();
                label.setText("Email esistente,effettuare il login");
                label.setStyle("-fx-text-fill: red;");
                label.setLayoutX(226.0);
                label.setLayoutY(373.0);
                label.setPrefHeight(17.0);
                label.setPrefWidth(267.0);
                getChildren().add(label);
            }else{
               GraficLoader.Loader(this,new LoginPage(),mainGroup );
            }
        });
        
        hyperlink.setLayoutX(226.0);
        hyperlink.setLayoutY(450.0);
        hyperlink.setPrefHeight(26.0);
        hyperlink.setPrefWidth(150.0);
        hyperlink.setText("Torna indietro");
        hyperlink.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new LoginPage(), mainGroup );});

        r1.setLayoutX(226.0);
        r1.setLayoutY(328.0);
        r1.setMnemonicParsing(false);
        r1.setPrefHeight(17.0);
        r1.setPrefWidth(123.0);
        

        r2.setLayoutX(226.0);
        r2.setLayoutY(356.0);
        r2.setMnemonicParsing(false);
        r2.setPrefHeight(17.0);
        r2.setPrefWidth(138.0);
        
        
        

        getChildren().add(textField);
        getChildren().add(label);
        getChildren().add(textField0);
        getChildren().add(textField1);
        getChildren().add(textField2);
        getChildren().add(textField3);
        getChildren().add(textField4);
        getChildren().add(button);
        getChildren().add(hyperlink);
        
        getChildren().add(r1); 
        getChildren().add(r2); 
        

    }
}
