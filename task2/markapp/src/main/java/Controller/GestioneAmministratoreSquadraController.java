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
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author berto
 * gestisce la pagina fxml che appartiene all'admin che sceglie 
 * gli amministratori di squadra
 */
public class GestioneAmministratoreSquadraController implements Initializable{
    Utente utente=null;
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
        int er=GestioneProfiliMongoDataAccess.aggiornaAmministratoreDiSquadra(utente, squadraInput.getText().toLowerCase(), nazioneInput.getText().toLowerCase());
        if(er==0){
            errorScegliSquadra.setText("Amministratore di squadra trovato!");
            nomeAmministratoreSquadra.setText(utente.getNome()+" "+utente.getCognome());
            emailAmministratoreSquadra.setText(utente.getEmail());
            return;
        } else if(er==1){
            errorScegliSquadra.setText("La società non ha un amministratore di squadra!");
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
        System.out.println("ciao");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazioneInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliNazione(newValue);
            System.out.println("We're in a text property listener, new value: " + newValue);
        });
        squadraInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            ScegliSquadra(newValue);
            System.out.println("We're in a text property listener, new value: " + newValue);
        });
    }
}
