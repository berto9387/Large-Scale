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
    //protected final TableView<Evento> twEvento;
    protected final TabellaVisualeEvento tabellaEvento;
   // private ObservableList<Evento> olEvento;
   /* protected final TableColumn TableColumn;
    protected final TableColumn TableColumn0;
    protected final TableColumn TableColumn1;
    protected final TableColumn TableColumn2;
    protected final TableColumn TableColumn3;
    protected final TableColumn TableColumn4;
    protected final TableColumn TableColumn5;
    protected final TableColumn TableColumn6;
    protected final TableColumn TableColumn7;*/
    private final ManagerDb insert;
    private ArrayList<Evento> ev;
    

    public RicercaEventi() {
        this.insert = new ManagerDb();
        tabellaEvento = new TabellaVisualeEvento();
        
        
        label = new Label();
        textField = new TextField();
        label0 = new Label();
        button = new Button();
        hyperlink = new Hyperlink();
        label1 = new Label();
        textField0 = new TextField();
        button0 = new Button();
        
    /*
        twEvento = new TableView();
        TableColumn5 = new TableColumn("id"); 
        
        TableColumn = new TableColumn("Nome");
        TableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn0 = new TableColumn("Città");
        TableColumn0.setCellValueFactory(new PropertyValueFactory<>("luogo"));
        TableColumn1 = new TableColumn("Data");
        TableColumn1.setCellValueFactory(new PropertyValueFactory<>("data"));
        TableColumn2 = new TableColumn("Ora");
        TableColumn2.setCellValueFactory(new PropertyValueFactory<>("ora"));
        TableColumn3 = new TableColumn("Posti");
        TableColumn3.setCellValueFactory(new PropertyValueFactory<>("posti"));
        TableColumn4 = new TableColumn("Tipologia");
        TableColumn4.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
        TableColumn6 = new TableColumn("descrizione");
        TableColumn7 = new TableColumn("organizzatore");
        
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(518.0);
        setPrefWidth(625.0);
*/
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
            
            //twEvento.getItems().clear();
            System.err.println(textField.getText());
            if("".equals(textField.getText())&&utente.organizzatore==false){
                return;
            }
            System.err.println(utente.id);
            ev=insert.ricercaEventi(utente.id, utente.organizzatore, textField.getText());
            tabellaEvento.aggiornaTabellaEventi(ev);
            //olEvento = FXCollections.observableArrayList(ev);
            //twEvento.setItems(olEvento);
            
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
            insert.iscrizioneEvento(Integer.parseInt(textField0.getText()), utente.id);
            System.out.println("id udente: " + utente.id);
            ev=insert.ricercaEventi(utente.id, utente.organizzatore, textField.getText());
            tabellaEvento.aggiornaTabellaEventi(ev);
            //olEvento = FXCollections.observableArrayList(ev);
            //twEvento.setItems(olEvento);
            
        });

        hyperlink.setText("Torna indietro");
        hyperlink.setLayoutX(275.0);
        hyperlink.setLayoutY(460.0);
        hyperlink.setPrefHeight(26.0);
        hyperlink.setPrefWidth(189.0);
        hyperlink.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new MenuUtente(), mainGroup );});
        
/*
        twEvento.setLayoutX(82.0);
        twEvento.setLayoutY(105.0);
        twEvento.setPrefHeight(200.0);
        twEvento.setPrefWidth(500.0);

        TableColumn.setPrefWidth(70.4000244140625);

        TableColumn0.setPrefWidth(80.7999267578125);

        TableColumn1.setPrefWidth(120.0);

        TableColumn2.setPrefWidth(75.0);

        TableColumn3.setPrefWidth(75.0);

        TableColumn4.setPrefWidth(81.0);
        twEvento.getColumns().addAll(TableColumn, TableColumn0, TableColumn1, TableColumn2, TableColumn3, 
                                        TableColumn4);
        getChildren().add(twEvento);*/
        getChildren().add(tabellaEvento);
        getChildren().add(label);
        getChildren().add(textField);
        getChildren().add(label0);
        getChildren().add(button);
        getChildren().add(hyperlink);
        getChildren().addAll(label1, textField0, button0);
        
    }
}
