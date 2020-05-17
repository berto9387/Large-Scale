/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.LoginSignUpNeo4jDataAccess;
import Dao.Neo4jDataAccess;
import Entita.Utente;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 *
 * @author Gianluca
 */
public class LoginSignUpController {
       
    
    @FXML
    private Button pageRegistrati;

    @FXML
    private Button pageLogin;

    @FXML
    private VBox loginVBox;

    @FXML
    private TextField emailField;
    
    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private VBox registratiVBox;

    @FXML
    private TextField nomeRegistazione;

    @FXML
    private TextField cognomeRegistrazione;

    @FXML
    private TextField emailRegistrazione;

    @FXML
    private PasswordField passwordRegistrazione;

    
    
    @FXML
    private Label err_reg_label;
    
    @FXML
    private Label err_log_label;
    @FXML
    private Button registratiButton;
    @FXML
    private Circle btnClose;
     @FXML
    void formLogin(ActionEvent event) {
        registratiVBox.setVisible(false);
        loginVBox.setVisible(true);
        if(pageRegistrati.getStyleClass().contains("active") && !pageLogin.getStyleClass().contains("active")){
            pageRegistrati.getStyleClass().remove("active");
            pageLogin.getStyleClass().add("active");
        }
    }

    @FXML
    void formRegistrati(ActionEvent event) {
        loginVBox.setVisible(false);
        registratiVBox.setVisible(true);
        if(!pageRegistrati.getStyleClass().contains("active") && pageLogin.getStyleClass().contains("active")){
            pageRegistrati.getStyleClass().add("active");
            pageLogin.getStyleClass().remove("active");
        }
    }
    
    @FXML
    void registrazione(ActionEvent event) {
        String nome=nomeRegistazione.getText();//toLowerCase
        String cognome=cognomeRegistrazione.getText();//toLowerCase
        String email=emailRegistrazione.getText().toLowerCase();
        String password=passwordRegistrazione.getText();//toLowerCase       
        if(nome.equals("")||cognome.equals("")||email.equals("")||password.equals(""))
        {
            err_log_label.setText("Alcuni campi non compilati!");
            err_log_label.setVisible(true);
            return;
        }
        int creazioneUtente=LoginSignUpNeo4jDataAccess.registraUtente(nome,cognome,email,password);

        if(creazioneUtente == 1){
            err_reg_label.setText("Email già presente !");
            err_reg_label.setVisible(true);
        } else if (creazioneUtente == 0){
            err_reg_label.setText("Registrazione andata a buon fine");
            err_reg_label.setVisible(true);
            nomeRegistazione.clear();
            cognomeRegistrazione.clear();
            emailRegistrazione.clear();
            passwordRegistrazione.clear();
        }
        System.err.println(creazioneUtente);
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException {
        String email=emailField.getText().toLowerCase();
        String password=passwordField.getText();//toLowerCase
        if(email.equals("")||password.equals(""))
        {
            err_log_label.setText("Alcuni campi non compilati!");
            err_log_label.setVisible(true);
            return;
        }
        int login = LoginSignUpNeo4jDataAccess.loginUtente(email, password);
        if ( login == 1){
            err_log_label.setText("Email o password errate !");
            err_log_label.setVisible(true);
        }else if (login == 0){ //Carico l'interfaccia sistema
            err_log_label.setVisible(false);
            
            String homePage = null;
            Utente utente = ScreenController.getUtente();
            System.out.println("--------------->"+utente.getRuolo() +"<---------------");
            System.out.println("---->"+utente.getNome()+"<----------");
            if(utente.getSocieta()!=null)
                System.out.println(utente.getSocieta().getNomeSocieta());
            switch(utente.getRuolo().toLowerCase()){
                case "admin": 
                    homePage = "AmministratoreSistema";
                    break;

                case "osservatore":
                    homePage="Osservatore";
                    break;
                   
                  
            }
            ScreenController.showHomePage(homePage);
        }
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        err_reg_label.setVisible(false);
        err_log_label.setVisible(false);
    }    

    @FXML
    private void handleClose(MouseEvent event) {
           try {
               Neo4jDataAccess.close();
           } catch (Exception ex) {
               Logger.getLogger(LoginSignUpController.class.getName()).log(Level.SEVERE, null, ex);
           }
        System.exit(0);
    }
            
}
