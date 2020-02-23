/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GestioneProfiliMongoDataAccess;
import Entita.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;


/**
 * FXML Controller class
 *
 * @author Gianluca
 */
public class GestioneUtentiController implements Initializable {
    @FXML
    private TextField campoEmailRicerca;
    @FXML
    private TextField campoNuovaEmail;
    @FXML
    private TextField campoNuovaPassword;
    @FXML
    private Label nomeUtente;
    @FXML
    private Label cognomeUtente;
    @FXML
    private Label emailUtente;
    
    @FXML
    private Label gestisciErrore;
    @FXML
    private void gestisciRicerca(ActionEvent event){
        Utente utente;
        
        if(campoEmailRicerca.getText().isEmpty()){
            gestisciErrore.setText("Email non valida");
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
                    nomeUtente.setText(utente.getNome());
                    cognomeUtente.setText(utente.getCognome());
                    emailUtente.setText(utente.getEmail());
                }
                
            } catch (Exception ex) {
                Logger.getLogger(GestioneUtentiController.class.getName()).log(Level.SEVERE, null, ex);
                gestisciErrore.setText("Errore di ricerca nel database");
            }
        }
    }
    @FXML
    private void gestisciEventoCambioInformazioni(ActionEvent event) 
    {
        String email="";
        String password="";
        if(campoNuovaEmail.getText().isEmpty()&&campoNuovaPassword.getText().isEmpty())
        {
            gestisciErrore.setText("Errore utente non presente");
        }
        else{
            String nuovaEmail=campoNuovaEmail.getText();
            String vecchiaEmail = emailUtente.getText();
            boolean aggiornamentoCorretto;
            try {
                aggiornamentoCorretto=GestioneProfiliMongoDataAccess.modificaProfilo(vecchiaEmail,nuovaEmail, password);
                if(!aggiornamentoCorretto)
                    gestisciErrore.setText("Errore campi non corretti");
                else{
                    emailUtente.setText(email);
                }
                    
            } catch (Exception ex) {
                Logger.getLogger(GestioneUtentiController.class.getName()).log(Level.SEVERE, null, ex);
                gestisciErrore.setText("Errore con l'aggiornamento riprova");
            }
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
