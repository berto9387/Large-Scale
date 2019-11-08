/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;


import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

/**
 *
 * @author Gianluca
 */
public class TabellaVisualeEvento extends TableView<Evento>{
    private final ObservableList<Evento> listaOsservabileEvento;
    
    public TabellaVisualeEvento()
    {
        //setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setMaxHeight(USE_PREF_SIZE);
        this.setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(200.0);
        setPrefWidth(500.0);
        setLayoutX(82.0);
        setLayoutY(160.0);
        
        
        TableColumn colonna_id = new TableColumn("id");
        TableColumn colonna_nome = new TableColumn("nome");
        TableColumn colonna_citta = new TableColumn("città");
        TableColumn colonna_data = new TableColumn("data");
        TableColumn colonna_ora = new TableColumn("ora");
        TableColumn colonna_posti = new TableColumn("posti");
        TableColumn colonna_numero_partecipanti = new TableColumn("numero_partecipanti");
        TableColumn colonna_tipologia = new TableColumn("tipologia");
        TableColumn colonna_descrizione = new TableColumn("descrizione");
        TableColumn colonna_organizzatore = new TableColumn("organizzatore");
        
        colonna_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colonna_nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        colonna_citta.setCellValueFactory(new PropertyValueFactory<>("Luogo"));
        colonna_data.setCellValueFactory(new PropertyValueFactory<>("Data"));
        colonna_ora.setCellValueFactory(new PropertyValueFactory<>("Ora"));
        colonna_posti.setCellValueFactory(new PropertyValueFactory<>("Posti"));
        colonna_numero_partecipanti.setCellValueFactory(new PropertyValueFactory<>("NumeroPartecipanti"));
        colonna_tipologia.setCellValueFactory(new PropertyValueFactory<>("Tipologia"));
        colonna_descrizione.setCellValueFactory(new PropertyValueFactory<>("Descrizione"));
        colonna_organizzatore.setCellValueFactory(new PropertyValueFactory<>("Organizzatore"));
        
        
        colonna_nome.setPrefWidth(70.4000244140625);

        colonna_citta.setPrefWidth(80.7999267578125);

        colonna_data.setPrefWidth(120.0);

        colonna_ora.setPrefWidth(75.0);

        colonna_posti.setPrefWidth(75.0);

        colonna_tipologia.setPrefWidth(81.0);
        
        
        
        listaOsservabileEvento = FXCollections.observableArrayList();
        setItems(listaOsservabileEvento);
        getColumns().addAll(colonna_id,colonna_nome,colonna_citta,colonna_data,colonna_ora,colonna_posti,
                    colonna_numero_partecipanti,colonna_tipologia,colonna_descrizione,colonna_organizzatore);
        
    }
    public void aggiornaTabellaEventi(ArrayList<Evento> listaEventi)
    {
        listaOsservabileEvento.clear();
        listaOsservabileEvento.addAll(listaEventi); 
    }
}
