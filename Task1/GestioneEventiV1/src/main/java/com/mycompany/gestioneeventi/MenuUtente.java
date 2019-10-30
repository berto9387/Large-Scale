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



import static com.mycompany.gestioneeventi.GeneralGrafic.mainGroup;
import com.mycompany.hibernate.GestioneEventiManagerEM;
import com.mycompany.hibernate.GestioneOperazioniPartecipanteEM;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MenuUtente extends GeneralGrafic {

    protected final Label labelNomeCognome;
    protected final Button buttonEsci;
    protected final Label labelMenuUtente;
    protected final Button buttonRicercaEventi;
    protected final Button buttonVisualizzaPartecipazioni;
    protected final VBox graficaPrincipale;
    protected final Label labelModificaDati;
    protected final Label labelEmail;
    protected final TextField textFieldEmail;
    protected final TextField textFieldNuovaPassword;
    protected final TextField textFieldVecchiaPassword;
    protected final Button buttonModifica;
    protected final Label labelInfo;
    protected final Hyperlink hyperlinkEliminaAccount;
    protected final Label labelError;

    public MenuUtente() {

        graficaPrincipale = new VBox();
        labelNomeCognome = new Label(); 
        buttonEsci = new Button();
        labelMenuUtente = new Label();
        buttonRicercaEventi = new Button();
        buttonVisualizzaPartecipazioni = new Button();
        labelModificaDati = new Label();
        labelEmail = new Label();
        textFieldEmail = new TextField();
        textFieldNuovaPassword = new TextField();
        textFieldVecchiaPassword = new TextField();
        buttonModifica = new Button();
        labelInfo = new Label();
        hyperlinkEliminaAccount = new Hyperlink();
        labelError = new Label();
        inizializzaGrafica();

      
//
//       button0.setLayoutX(328.0);
//        button0.setLayoutY(232.0);
//        button0.setMnemonicParsing(false);
//        button0.setPrefHeight(31.0);
//        button0.setPrefWidth(181.0);
//        button0.setText("Ricerca Eventi");
//        button0.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new RicercaEventi(),mainGroup );});
//
//        button1.setLayoutX(98.0);
//        button1.setLayoutY(232.0);
//        button1.setMnemonicParsing(false);
//        button1.setText("Visualizza Partecipazioni");
//        button1.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new VisualizzaPartecipazioni(),mainGroup );});


    }

    private void inizializzaGrafica() {
        
        inizializzaElementiMenuUtente();
        
        HBox lineaTitolo=new HBox();
        lineaTitolo.getChildren().addAll(labelMenuUtente, buttonEsci);
        lineaTitolo.setAlignment(Pos.TOP_CENTER);
        lineaTitolo.setSpacing(100);
        
        HBox lineaPulsantiEventi = new HBox();
        lineaPulsantiEventi.getChildren().addAll(buttonRicercaEventi, buttonVisualizzaPartecipazioni);
        lineaPulsantiEventi.setAlignment(Pos.CENTER);
        lineaPulsantiEventi.setSpacing(70);
        
        VBox colonnaModificaDati = new VBox();
        HBox lineaModificaDati = new HBox();
        lineaModificaDati.getChildren().addAll(textFieldEmail, textFieldNuovaPassword, textFieldVecchiaPassword);
        lineaModificaDati.setSpacing(15);
        lineaModificaDati.setAlignment(Pos.CENTER);
        colonnaModificaDati.getChildren().addAll(labelModificaDati, labelEmail, lineaModificaDati, buttonModifica, labelInfo);
        colonnaModificaDati.setSpacing(15);
        labelEmail.setAlignment(Pos.BASELINE_LEFT);
        labelModificaDati.setAlignment(Pos.BASELINE_LEFT);

        VBox colonnaEliminaAccount = new VBox();
        colonnaEliminaAccount.getChildren().addAll(hyperlinkEliminaAccount, labelError);
        colonnaEliminaAccount.setSpacing(10);
   
        setTop(lineaTitolo);
        setCenter(lineaPulsantiEventi);
       
        graficaPrincipale.getChildren().addAll(lineaTitolo, lineaPulsantiEventi, colonnaModificaDati, colonnaEliminaAccount);
        graficaPrincipale.setSpacing(35);
        setCenter(graficaPrincipale);
        BorderPane.setAlignment(graficaPrincipale, Pos.CENTER);
    }

    private void inizializzaElementiMenuUtente() {
        
        
        GestioneEventiManagerEM.creaConnesione();
                
                
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(458.0);
        setPrefWidth(600.0);
        
        labelMenuUtente.setAlignment(javafx.geometry.Pos.CENTER);
        labelMenuUtente.setPrefHeight(17.0);
        labelMenuUtente.setPrefWidth(400.0);
        labelMenuUtente.setText("Benvenuto, " + partecipante.getNome() + " " + partecipante.getCognome());
        labelMenuUtente.setFont(new Font(24.0));
        labelMenuUtente.setStyle("-fx-font-weight: bold");
        
        buttonEsci.setMnemonicParsing(false);
        buttonEsci.setPrefHeight(17.0);
        buttonEsci.setPrefWidth(149.0);
        buttonEsci.setText("Esci");
        buttonEsci.setOnAction((ActionEvent ev) -> {
            GestioneEventiManagerEM.chiudiConnesione();
            GraficLoader.Loader(this, new LoginPage(), mainGroup );
        });
        
        buttonRicercaEventi.setMnemonicParsing(false);
        buttonRicercaEventi.setPrefHeight(17.0);
        buttonRicercaEventi.setPrefWidth(149.0);
        buttonRicercaEventi.setText("Ricerca eventi");
        buttonRicercaEventi.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new RicercaEventi(), mainGroup );});
      
        buttonVisualizzaPartecipazioni.setMnemonicParsing(false);
        buttonVisualizzaPartecipazioni.setPrefHeight(17.0);
        buttonVisualizzaPartecipazioni.setPrefWidth(149.0);
        buttonVisualizzaPartecipazioni.setText("Visualizza partecipazioni");
        buttonVisualizzaPartecipazioni.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new VisualizzaPartecipazioni(), mainGroup );});
        
        labelModificaDati.setAlignment(javafx.geometry.Pos.CENTER);
        labelModificaDati.setPrefHeight(17.0);
        labelModificaDati.setPrefWidth(400.0);
        labelModificaDati.setText("Modifica email e/o password: ");
        labelModificaDati.setFont(new Font(18.0));
        labelModificaDati.setStyle("-fx-font-weight: bold");
        
        labelEmail.setAlignment(javafx.geometry.Pos.CENTER);
        labelEmail.setPrefHeight(17.0);
        labelEmail.setPrefWidth(400.0);
        labelEmail.setText("Email attuale: " + partecipante.getEmail());
        labelEmail.setFont(new Font(15.0));
        
        textFieldEmail.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldEmail.setPromptText("Nuova email");
        
        textFieldNuovaPassword.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldNuovaPassword.setPromptText("Nuova password");
        
        textFieldVecchiaPassword.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldVecchiaPassword.setPromptText("Vecchia password");
        
        buttonModifica.setMnemonicParsing(false);
        buttonModifica.setPrefHeight(17.0);
        buttonModifica.setPrefWidth(149.0);
        buttonModifica.setText("Modifica");
        //buttonModifica.setOnAction((ActionEvent ev) -> {gestisciModificaDati();});
        
//        labelInfo.setText("Email esistente,effettuare il login");
//        labelInfo.setStyle("-fx-text-fill: red;");
        labelInfo.setVisible(false);
        labelInfo.setPrefHeight(17.0);
        labelInfo.setPrefWidth(267.0);
        
        hyperlinkEliminaAccount.setPrefHeight(26.0);
        hyperlinkEliminaAccount.setPrefWidth(150.0);
        hyperlinkEliminaAccount.setText("Elimina Account");
       // hyperlinkEliminaAccount.setOnAction((ActionEvent ev) -> {gestisciEliminaAccount();});
        
        labelError.setVisible(false);
        labelError.setPrefHeight(17.0);
        labelError.setPrefWidth(267.0);
    }
/*
    private void gestisciModificaDati() {
        int errore = 2;
        String email = textFieldEmail.getText();
        String nuovaPassword = textFieldNuovaPassword.getText();
        String vecchiaPassword = textFieldVecchiaPassword.getText();
        
        errore = GestioneOperazioniDbLatoPartecipante.modificaDati(utente, email, nuovaPassword, vecchiaPassword);
        
        if(errore == 1){
            labelInfo.setText("Modifiche effettuate con successo");
            labelInfo.setStyle("-fx-text-fill: green;");
            
        } else if(errore == 0){
            labelInfo.setText("Vecchia password errata");
            labelInfo.setStyle("-fx-text-fill: red;");
        } else{
            labelInfo.setText("Errore durante la modifica");
            labelInfo.setStyle("-fx-text-fill: red;");
        }
        labelInfo.setVisible(true);
    }

    private void gestisciEliminaAccount() {
        int errore;
        
        errore = GestioneOperazioniDbLatoPartecipante.eliminaAccount(partecipante);
        
        if(errore == 0){
            labelError.setText("Errore durante la modifica");
            labelError.setStyle("-fx-text-fill: red;");
            labelError.setVisible(true);
        } else{
            GraficLoader.Loader(this, new LoginPage(), mainGroup );
        }
        
    }*/
}

