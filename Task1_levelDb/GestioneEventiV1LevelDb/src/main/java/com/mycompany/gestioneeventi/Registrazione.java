package com.mycompany.gestioneeventi;


import com.mycompany.hibernate.*;
import java.sql.Date;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class Registrazione extends GeneralGrafic {

    protected final TextField textFieldNome;
    protected final Label labelRegistrazione;
    protected final TextField textFieldCognome;
    protected final TextField textFieldDataDiNascita;
    protected final TextField textFieldEmail;
    protected final TextField textFieldUsername;
    protected final TextField textFieldPassword;
    protected final TextField textFieldTelefono;
    protected final Button buttonRegistrati;
    protected final RadioButton RadioButtonOrganizzatore;
    protected final RadioButton RadioButtonPartecipante;
    protected final ToggleGroup ToggleGroupGruppoUtente;
    protected final VBox graficaPrincipale;
    protected final Label labelErrore ;
    private final Hyperlink hyperlinkTornaIndietro;

    
    
    public Registrazione() {
        labelErrore = new Label();
        textFieldTelefono = new TextField();
        graficaPrincipale = new VBox();
        textFieldNome = new TextField();
        labelRegistrazione = new Label();
        textFieldCognome = new TextField();
        textFieldDataDiNascita = new TextField();
        textFieldEmail = new TextField();
        textFieldUsername = new TextField();
        textFieldPassword = new TextField();
        hyperlinkTornaIndietro = new Hyperlink();
        
        buttonRegistrati = new Button();
        ToggleGroupGruppoUtente = new ToggleGroup();
        RadioButtonOrganizzatore = new RadioButton("Organizzatore"); 
        RadioButtonPartecipante = new RadioButton("Partecipante"); 
        inizializzaGrafica();
        
    }
    
    private void inizializzaGrafica()
    {

        inizializzaElementiDelForm();
        HBox lineaTitolo=new HBox();
        lineaTitolo.getChildren().addAll(labelRegistrazione);
        lineaTitolo.setAlignment(Pos.CENTER);
        graficaPrincipale.getChildren().addAll(lineaTitolo,textFieldNome,textFieldCognome,
                textFieldDataDiNascita,textFieldEmail,textFieldUsername,textFieldPassword,textFieldTelefono,
                labelErrore, RadioButtonOrganizzatore,RadioButtonPartecipante,buttonRegistrati,hyperlinkTornaIndietro);
        graficaPrincipale.setSpacing(25);
        
        setCenter(graficaPrincipale);
        
        BorderPane.setMargin(graficaPrincipale, new Insets(300,20,50,150));
    }
    
    
    private void inizializzaElementiDelForm()
    {
        RadioButtonOrganizzatore.setToggleGroup(ToggleGroupGruppoUtente); 
        RadioButtonPartecipante.setToggleGroup(ToggleGroupGruppoUtente);
        RadioButtonPartecipante.setSelected(true);

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(458.0);
        setPrefWidth(600.0);

        labelRegistrazione.setAlignment(javafx.geometry.Pos.CENTER);
        labelRegistrazione.setPrefHeight(17.0);
        labelRegistrazione.setPrefWidth(267.0);
        labelRegistrazione.setText("REGISTRAZIONE");
        labelRegistrazione.setFont(new Font(24.0));

        textFieldNome.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldNome.setPromptText("Nome");

        textFieldCognome.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldCognome.setPromptText("Cognome");
        
        textFieldDataDiNascita.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldDataDiNascita.setPromptText("Data nascita");

        textFieldEmail.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldEmail.setPromptText("Email");

        textFieldUsername.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldUsername.setPromptText("Username");

        textFieldPassword.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldPassword.setPromptText("Password");
        
        textFieldTelefono.setAlignment(javafx.geometry.Pos.CENTER);
        textFieldTelefono.setPromptText("Telefono");
        
        
        buttonRegistrati.setMnemonicParsing(false);
        buttonRegistrati.setPrefHeight(17.0);
        buttonRegistrati.setPrefWidth(149.0);
        buttonRegistrati.setText("REGISTRATI");
        
        buttonRegistrati.setOnAction((ActionEvent e) -> {gestisciEventoLogin();});
        
        hyperlinkTornaIndietro.setPrefHeight(26.0);
        hyperlinkTornaIndietro.setPrefWidth(150.0);
        hyperlinkTornaIndietro.setText("Torna indietro");
        hyperlinkTornaIndietro.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, new LoginPage(), mainGroup );});

        RadioButtonOrganizzatore.setMnemonicParsing(false);
        RadioButtonOrganizzatore.setPrefHeight(17.0);
        RadioButtonOrganizzatore.setPrefWidth(123.0);
        
        RadioButtonPartecipante.setMnemonicParsing(false);
        RadioButtonPartecipante.setPrefHeight(17.0);
        RadioButtonPartecipante.setPrefWidth(138.0);        
        
        
                
        labelErrore.setText("Email esistente,effettuare il login");
        labelErrore.setStyle("-fx-text-fill: red;");
        labelErrore.setVisible(false);
        labelErrore.setPrefHeight(17.0);
        labelErrore.setPrefWidth(267.0);
    
    
    }
    
    private void gestisciEventoLogin()
    {
            int errore=0;
            String Nome=textFieldNome.getText();
            String Cognome=textFieldCognome.getText();
            String sData=textFieldDataDiNascita.getText();
            Date Data=Date.valueOf(sData);
            String Email=textFieldEmail.getText();
            String Phone=textFieldTelefono.getText();
            String Username=textFieldUsername.getText();
            String Password=textFieldPassword.getText(); 
            RadioButton selectedRadioButton = (RadioButton) ToggleGroupGruppoUtente.getSelectedToggle();
            String Ruolo = selectedRadioButton.getText();
            if(Ruolo.equals("Partecipante")){
                PartecipanteDb partecipante_aux=new PartecipanteDb();
                partecipante_aux.setEmail(Email);
                partecipante_aux.setNome(Nome);
                partecipante_aux.setCognome(Cognome);
                partecipante_aux.setData_nascita(Data);
                partecipante_aux.setUsername(Username);
                partecipante_aux.setPhone(Phone);
                partecipante_aux.setPassword(Password);
                errore=GestioneOperazioniPartecipanteEM.inserisciPartecipante(partecipante_aux);
            }else{
                OrganizzatoreDb organizzatore_aux=new OrganizzatoreDb();
                organizzatore_aux.setNome(Nome);
                organizzatore_aux.setCognome(Cognome);
                organizzatore_aux.setData_nascita(Data);
                organizzatore_aux.setEmail(Email);
                organizzatore_aux.setUsername(Username);
                organizzatore_aux.setPhone(Phone);
                organizzatore_aux.setPassword(Password);
                errore=GestioneOperazioniOrganizzatoreEM.inserisciOrganizzatore(organizzatore_aux);
               
              
            }
            if(errore==0){
               labelErrore.setVisible(true); 
           }else
            {
               GraficLoader.Loader(this,new LoginPage(),mainGroup );
            }
        
    
    }
}
