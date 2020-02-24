/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GestioneProfiliMongoDataAccess;
import Entita.Utente;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import task2.markapp.ScreenController;


/**
 *
 * @author berto
 * usa GestioneProfiliMongoDataAccess
 */
public class ModificaProfiloUtenteController extends GenerallController{
    @FXML
    private Label nomeUtenteLabel;

    @FXML
    private Label emailAttualeLabel;

    @FXML
    private JFXTextField nuovaEmailInput;

    @FXML
    private JFXPasswordField nuovaPasswordInput;

    @FXML
    private JFXPasswordField confermaNuovaPasswordInput;
    
    @FXML
    private Label errEliminaAccountLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utente utente = ScreenController.getUtente();
        nomeUtenteLabel.setText(utente.getNome() + " " + utente.getCognome());
        emailAttualeLabel.setText(utente.getEmail());
    }
    
    @FXML
    void handleEliminaAccount(MouseEvent event) throws IOException {
        Utente utente = ScreenController.getUtente();
        int err = GestioneProfiliMongoDataAccess.eliminaAccount(utente.getEmail(), utente.getRuolo(), utente.getId());
        if(err == 0)
            ScreenController.showLogin();
        
        else
            errEliminaAccountLabel.setVisible(true);
        
    }

    @FXML
    void handleModificaDati(MouseEvent event) {
        errEliminaAccountLabel.setText("");
        
        Utente utente = ScreenController.getUtente();
        String nuovaEmail="";
        String password="";
        String confermaPassword="";
        
        if(nuovaEmailInput.getText().isEmpty()&&nuovaPasswordInput.getText().isEmpty()){
            errEliminaAccountLabel.setText("Errore utente non presente");
        }
        else{
            if(!nuovaEmailInput.getText().isEmpty()){
                nuovaEmail=nuovaEmailInput.getText();
            }
            if(!nuovaPasswordInput.getText().isEmpty()){
                password=nuovaPasswordInput.getText();
            }
            if(!confermaNuovaPasswordInput.getText().isEmpty()){
                confermaPassword=confermaNuovaPasswordInput.getText();
            }
            if(!confermaPassword.equals(password)){
                errEliminaAccountLabel.setText("i campi password e conferma password non combaciano");
                return;
            }
            String vecchiaEmail = emailAttualeLabel.getText();
            boolean aggiornamentoCorretto;
            try {
                aggiornamentoCorretto=GestioneProfiliMongoDataAccess.modificaProfilo(vecchiaEmail,nuovaEmail, password);
                if(!aggiornamentoCorretto)
                    errEliminaAccountLabel.setText("Errore campi non corretti");
                else{
                    if(!nuovaEmail.equals("")){
                        emailAttualeLabel.setText(nuovaEmail);
                        utente.setEmail(nuovaEmail);
                    }
                }
                    
            } catch (Exception ex) {
                Logger.getLogger(GestioneUtentiController.class.getName()).log(Level.SEVERE, null, ex);
                errEliminaAccountLabel.setText("Errore con l'aggiornamento riprova");
            }
        }
    }
}
