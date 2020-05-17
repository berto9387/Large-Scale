/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GestioneProfiliNeo4jDataAccess;
import Entita.Utente;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Gianluca
 */
public class GestioneUtentiController implements Initializable {
    private Utente utente=null;
    @FXML
    private Text scegliNazioneTesto;

    @FXML
    private JFXTextField nazioneInput;

    @FXML
    private Text scegliSquadraTesto;

    @FXML
    private JFXTextField squadraInput;

    @FXML
    private Text errorScegliSquadra;

    @FXML
    private Text nomeOsservatoreSquadra;

    @FXML
    private Text emailOsservatoreSquadra;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private Text errorCambiaOsservatoreSquadra;

    void ScegliSquadra(String newValue) {
        if(newValue.isEmpty())
            scegliSquadraTesto.setVisible(false);
        else{
            scegliSquadraTesto.setVisible(true);
        }
    }
    void scegliNazione(String newValue) {
        if(newValue.isEmpty())
                scegliNazioneTesto.setVisible(false);
        else{
                scegliNazioneTesto.setVisible(true);
        }
        
    }
    
    @FXML
    void cercaOsservatoreSquadra(ActionEvent event) {
        utente = new Utente();
        if(nazioneInput.getText().isEmpty() || squadraInput.getText().isEmpty()){
            errorScegliSquadra.setText("Completare tutti i campi!");
            return;
        }
        utente=GestioneProfiliNeo4jDataAccess.cercaProfiloUtenteDaSocieta(squadraInput.getText(), nazioneInput.getText());
        if(utente.getId()==null)
        {
            errorScegliSquadra.setText("La società non ha un amministratore di squadra!");
            nomeOsservatoreSquadra.setText("");
            emailOsservatoreSquadra.setText("");
            return;            
        }
        if(utente.getSocieta()==null)
        {
            errorScegliSquadra.setText("La società non esiste!");
            return;  
        }
        errorScegliSquadra.setText("Amministratore di squadra trovato!");
        nomeOsservatoreSquadra.setText(utente.getNome()+" "+utente.getCognome());
        emailOsservatoreSquadra.setText(utente.getEmail());
        
        
    }
    @FXML
    void modificaOsservatoreSquadra(MouseEvent event) {
        if(emailInput.getText().isEmpty()){
            errorCambiaOsservatoreSquadra.setText("Inserisci l'email del nuovo amministratore di squadra!");
            return;
        }
        if(utente.getSocieta()==null){
            errorCambiaOsservatoreSquadra.setText("Cerca prima la società!");
        }
       int risultato= GestioneProfiliNeo4jDataAccess.aggiornaTeamSocieta(utente, emailInput.getText(), squadraInput.getText(), nazioneInput.getText());
       
       if(risultato==0)
       {
            errorCambiaOsservatoreSquadra.setText("Aggiornamento riuscito!");
            nomeOsservatoreSquadra.setText(utente.getNome()+" "+utente.getCognome());
            emailOsservatoreSquadra.setText(utente.getEmail());
       
       }
       else if(risultato==1){
            errorCambiaOsservatoreSquadra.setText("Utente non presente nel database");
        } else if(risultato==2){
            errorCambiaOsservatoreSquadra.setText("utente connesso ad una società");
        }  else{
            errorCambiaOsservatoreSquadra.setText("aggiornamento non riuscito riprova!");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        nazioneInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliNazione(newValue);
        });
        squadraInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            ScegliSquadra(newValue);
        });
    }
    
}
