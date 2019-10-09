package com.mycompany.gestioneeventi;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class Registrazione extends Pane {

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

    public Registrazione() {

        textField = new TextField();
        label = new Label();
        textField0 = new TextField();
        textField1 = new TextField();
        textField2 = new TextField();
        textField3 = new TextField();
        textField4 = new TextField();
        button = new Button();
        tg = new ToggleGroup();
        r1 = new RadioButton("Organizzatore"); 
        r2 = new RadioButton("Parteciante"); 
        r1.setToggleGroup(tg); 
        r2.setToggleGroup(tg);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(458.0);
        setPrefWidth(600.0);

        textField.setAlignment(javafx.geometry.Pos.CENTER);
        textField.setLayoutX(226.0);
        textField.setLayoutY(179.0);
        textField.setPromptText("Data nascita");

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(167.0);
        label.setLayoutY(33.0);
        label.setPrefHeight(17.0);
        label.setPrefWidth(267.0);
        label.setText("REGISTRAZIONE");
        label.setFont(new Font(24.0));

        textField0.setAlignment(javafx.geometry.Pos.CENTER);
        textField0.setLayoutX(226.0);
        textField0.setLayoutY(96.0);
        textField0.setPromptText("Nome");

        textField1.setAlignment(javafx.geometry.Pos.CENTER);
        textField1.setLayoutX(226.0);
        textField1.setLayoutY(138.0);
        textField1.setPromptText("Cognome");

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
        
        
        getChildren().add(r1); 
        getChildren().add(r2); 
        

    }
}
