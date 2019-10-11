/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

/**
 *
 * @author berto
 */


import com.mycompany.gestioneeventi.GeneralGrafic;
import static com.mycompany.gestioneeventi.GeneralGrafic.mainGroup;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class MenuUtente extends GeneralGrafic {

    protected final Button button;
    protected final Label label;
    protected final Button button0;
    protected final Button button1;

    public MenuUtente() {

        button = new Button();
        label = new Label();
        button0 = new Button();
        button1 = new Button();

        

        button.setLayoutX(581.0);
        button.setLayoutY(32.0);
        button.setMnemonicParsing(false);
        button.setText("Esci");
        button.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new LoginPage(),mainGroup );});

        label.setLayoutX(62.0);
        label.setLayoutY(22.0);
        label.setPrefHeight(21.0);
        label.setPrefWidth(346.0);
        label.setText("Menu Utente");
        label.setFont(new Font(35.0));

        button0.setLayoutX(328.0);
        button0.setLayoutY(232.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(31.0);
        button0.setPrefWidth(181.0);
        button0.setText("Ricerca Eventi");
        //button0.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new RicercaEventi(),mainGroup );});

        button1.setLayoutX(98.0);
        button1.setLayoutY(232.0);
        button1.setMnemonicParsing(false);
        button1.setText("Visualizza Partecipazioni");
        //button1.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new VisualizzaPartecipazioni(),mainGroup );});

        getChildren().add(button);
        getChildren().add(label);
        getChildren().add(button0);
        getChildren().add(button1);

    }
}

