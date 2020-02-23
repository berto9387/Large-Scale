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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author berto
 * gestisce la pagina fxml che appartiene all'admin che sceglie 
 * gli amministratori di squadra
 */
public class GestioneAmministratoreSquadraController implements Initializable{
    private Utente utente=null;
    private String ruolo="Amministratore di squadra";
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
    private Text nomeAmministratoreSquadra;

    @FXML
    private Text emailAmministratoreSquadra;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private Text errorCambiaAmministratoreSquadra;

    
    void ScegliSquadra(String newValue) {
        if(newValue.isEmpty())
            scegliSquadraTesto.setVisible(false);
        else{
            scegliSquadraTesto.setVisible(true);
        }
    }

    @FXML
    void cercaAmministratoreSquadra(ActionEvent event) {
        if(nazioneInput.getText().isEmpty() || squadraInput.getText().isEmpty()){
            errorScegliSquadra.setText("Completare tutti i campi!");
            return;
        }
        utente=new Utente();
        
        int er=GestioneProfiliMongoDataAccess.trovaUtenteInBaseAlRuolo(utente, squadraInput.getText().toLowerCase(), nazioneInput.getText().toLowerCase(),ruolo);
        if(er==0){
            if(utente.getId()==null){
                errorScegliSquadra.setText("La società non ha un amministratore di squadra!");
                nomeAmministratoreSquadra.setText("");
                emailAmministratoreSquadra.setText("");
                return;
            }
            errorScegliSquadra.setText("Amministratore di squadra trovato!");
            nomeAmministratoreSquadra.setText(utente.getNome()+" "+utente.getCognome());
            emailAmministratoreSquadra.setText(utente.getEmail());
            return;
        } else if(er==1){
            errorScegliSquadra.setText("La società non esiste!");
        } else{
            errorScegliSquadra.setText("Riprova più tardi!");
        }
        nomeAmministratoreSquadra.setText("");
        emailAmministratoreSquadra.setText("");
        
    }

    
    void scegliNazione(String newValue) {
        if(newValue.isEmpty())
                scegliNazioneTesto.setVisible(false);
        else{
                scegliNazioneTesto.setVisible(true);
        }
        
    }
    @FXML
    void modificaAmministratoreSquadra(MouseEvent event) {
        if(emailInput.getText().isEmpty()){
            errorCambiaAmministratoreSquadra.setText("Inserisci l'email del nuovo amministratore di squadra!");
            return;
        }
        if(utente.getSocieta().getId()==null){
            errorCambiaAmministratoreSquadra.setText("Cerca prima la società!");
        }
        int er=GestioneProfiliMongoDataAccess.aggiornaTeamSocieta(utente, emailInput.getText(),ruolo);
        if(er==0){
            errorCambiaAmministratoreSquadra.setText("Aggiornamento riuscito!");
            errorScegliSquadra.setText("Amministratore di squadra trovato!");
            nomeAmministratoreSquadra.setText(utente.getNome()+" "+utente.getCognome());
            emailAmministratoreSquadra.setText(utente.getEmail());
        }else if(er==1){
            errorCambiaAmministratoreSquadra.setText("Ruolo dell'utente non idoneo!");
        } else if(er==2){
            errorCambiaAmministratoreSquadra.setText("L'utente non esiste!");
        } else{
            errorCambiaAmministratoreSquadra.setText("problemi di consistenza!");
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
