/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GestioneProfiliMongoDataAccess;
import Entita.Utente;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import task2.markapp.ScreenController;


/**
 * FXML Controller class
 *
 * @author Gianluca
 */
public class GestioneUtentiController implements Initializable {
    private Utente utente;
    @FXML
    private JFXTextField campoEmailRicerca;
    @FXML
    private JFXTextField campoNuovaEmail;
    @FXML
    private JFXTextField campoNuovaPassword;
    @FXML
    private Label nomeUtente;
    @FXML
    private Label emailUtente;
    
    @FXML
    private Label gestisciErrore;
    @FXML
    private void gestisciRicerca(ActionEvent event){
       
        gestisciErrore.setText("");
        if(campoEmailRicerca.getText().isEmpty()){
            gestisciErrore.setText("Campo Email vuoto");
            }
        else{
            String email;
            email=campoEmailRicerca.getText();
            try {
                
                utente= GestioneProfiliMongoDataAccess.cercaUtenteDaEmail(email);
                if(utente==null){
                    gestisciErrore.setText("Errore utente non presente");
                    
                }
                else{
                    nomeUtente.setText(utente.getNome() + " " + utente.getCognome());
                    emailUtente.setText(utente.getEmail());
                }
                
            } catch (Exception ex) {
                Logger.getLogger(GestioneUtentiController.class.getName()).log(Level.SEVERE, null, ex);
                gestisciErrore.setText("Errore di ricerca nel database");
            }
        }
    }
    @FXML
    private void gestisciEventoCambioInformazioni(MouseEvent event) 
    {
        gestisciErrore.setText("");
        String nuovaEmail="";
        String password="";
        if(campoNuovaEmail.getText().isEmpty()&&campoNuovaPassword.getText().isEmpty())
        {
            gestisciErrore.setText("Errore utente non presente");
        }
        else{
            if(!campoNuovaEmail.getText().isEmpty()){
                nuovaEmail=campoNuovaEmail.getText();
            }
            if(!campoNuovaPassword.getText().isEmpty()){
                password=campoNuovaPassword.getText();
            }
            String vecchiaEmail = emailUtente.getText();
            boolean aggiornamentoCorretto;
            try {
                aggiornamentoCorretto=GestioneProfiliMongoDataAccess.modificaProfilo(vecchiaEmail,nuovaEmail, password);
                if(!aggiornamentoCorretto)
                    gestisciErrore.setText("Errore campi non corretti");
                else{
                    if(!nuovaEmail.equals("")){
                        emailUtente.setText(nuovaEmail);
                        utente.setEmail(nuovaEmail);
                        campoNuovaEmail.clear();
                        campoNuovaPassword.clear();
                    }
                    
                }
                    
            } catch (Exception ex) {
                Logger.getLogger(GestioneUtentiController.class.getName()).log(Level.SEVERE, null, ex);
                gestisciErrore.setText("Errore con l'aggiornamento riprova");
            }
        }
    }
    @FXML
    private void gestisciEliminaUtente(MouseEvent event)
    {
        gestisciErrore.setText("");
        String email=utente.getEmail();
        String ruolo=utente.getRuolo();
        String id=utente.getId();
        int risultato=GestioneProfiliMongoDataAccess.eliminaAccount(email, ruolo, id);
        if(risultato==0){
            nomeUtente.setText("");
            emailUtente.setText("");       
        }
        else{
               gestisciErrore.setText("Errore durante la elimina"); 
        }
    } 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
