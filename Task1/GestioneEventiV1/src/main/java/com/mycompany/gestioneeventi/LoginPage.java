/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;


import com.mycompany.hibernate.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;
//import javafx.stage.Stage;

/**
 *
 * @author gianluca
 */
public class LoginPage extends GeneralGrafic
{ 
    protected final Button buttonLoginPartecipante;
    protected final Label labelLogin;
    protected final Button buttonLoginOrganizzatore;
    protected final Hyperlink hyperlinkNonSeiRegistrato;
    protected final TextField textFieldEmail;
    protected final PasswordField textFieldPassword;
    protected VBox graficaPrincipale;
    protected HBox pulsantiLogin;
    protected Label etichettaErrore;
    public LoginPage() {
        etichettaErrore = new Label();
        graficaPrincipale=new VBox();//GIANLUCA 01)INIZIO
        pulsantiLogin = new HBox();//GIANLUCA 01) FINE
        buttonLoginPartecipante = new Button();
        labelLogin = new Label();
        
        buttonLoginOrganizzatore = new Button();
        hyperlinkNonSeiRegistrato = new Hyperlink();
        textFieldEmail = new TextField();
        textFieldPassword = new PasswordField();
        Registrazione reg = new Registrazione();

        setId("BorderPane");
        setPrefHeight(327.0);
        setPrefWidth(320);
        setStyle("-fx-font.size: 25;");
        etichettaErrore.setText("Email o password sbagliati riprova");
        etichettaErrore.setStyle("-fx-text-fill: red;");
        buttonLoginPartecipante.setMnemonicParsing(false);
        buttonLoginPartecipante.setText("Login partecipante");
        buttonLoginPartecipante.setOnAction((ActionEvent ev)->{EventoLoginPartecipante();});


        buttonLoginOrganizzatore.setText("Login organizzatore");
        buttonLoginOrganizzatore.setOnAction((ActionEvent ev)->{EventoLoginOrganizzatore();});
        hyperlinkNonSeiRegistrato.setText("Non sei registrato?");
        hyperlinkNonSeiRegistrato.setOnAction((ActionEvent ev) -> {GraficLoader.Loader(this, reg,mainGroup );});
        textFieldEmail.setPromptText("email");
        textFieldPassword.setPromptText("password");

        inizializzaGraficaLoginPage();
    }
    
    
    private void inizializzaGraficaLoginPage()//GIANLUCA 02
    {        
        hyperlinkNonSeiRegistrato.setPrefHeight(26.0);
        hyperlinkNonSeiRegistrato.setPrefWidth(150.0);
        buttonLoginPartecipante.setMinWidth(170);
        buttonLoginOrganizzatore.setMinWidth(170);
        etichettaErrore.setVisible(false);
        
        buttonLoginOrganizzatore.setMnemonicParsing(false);
        
        labelLogin.setPrefHeight(32.0);
        labelLogin.setPrefWidth(87.0);
        labelLogin.setText("LOGIN");
        labelLogin.setFont(new Font(24.0));
        
        
        HBox lineaTitolo=new HBox();
        lineaTitolo.getChildren().addAll(labelLogin);
        lineaTitolo.setAlignment(Pos.CENTER);
        pulsantiLogin.getChildren().addAll(buttonLoginPartecipante,buttonLoginOrganizzatore);
        pulsantiLogin.setSpacing(20);
        
        graficaPrincipale.getChildren().addAll(lineaTitolo,textFieldEmail,textFieldPassword,pulsantiLogin,etichettaErrore,hyperlinkNonSeiRegistrato);
        graficaPrincipale.setSpacing(25);
        //setTop(lineaTitolo);
        setCenter(graficaPrincipale);
        BorderPane.setMargin(graficaPrincipale,new Insets(30,20,30,200));
        
    }
    
    private void EventoLoginPartecipante()
    {       
        partecipante=new PartecipanteDb();
        organizzatore=new OrganizzatoreDb();
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();
        GestioneEventiManagerEM.setup();
        partecipante=GestioneOperazioniPartecipanteEM.loginPartecipante(email, password);
        //GestioneEventiManagerEM.exit();
        if(partecipante==null)
        {
            etichettaErrore.setVisible(true);
        }else
        {
            GraficLoader.Loader(this,new MenuUtente(),mainGroup );
        }
    }
    
    
    
    private void EventoLoginOrganizzatore()
    {
        partecipante=new PartecipanteDb();
        organizzatore=new OrganizzatoreDb();
        String email = textFieldEmail.getText();
        String password = textFieldPassword.getText();
        
        GestioneEventiManagerEM.setup();
        organizzatore = GestioneOperazioniOrganizzatoreEM.loginOrganizzatore(email, password);
        //GestioneEventiManagerEM.exit();
        
        if(organizzatore==null){
            
            etichettaErrore.setVisible(true);
            
        } else{
            
            GraficLoader.Loader(this,new CreazioneEvento(),mainGroup );
            
        }
         
         
    
    }

}
/*
                    COMMENTI DI GIANLUCA

01) inizializzati gli elementi in cui verranno incapsulati gli elementi grafici 
    della login page VBOX permette di inserire i node in verticale mentre HBOX
    gli elementi vengono allineati in orrizontale.
02) gli elementi di grafica vengono incapsulati negli appositi VB e VH e vengono
    inseriti nel corpo centrale della BordderPane.
*/