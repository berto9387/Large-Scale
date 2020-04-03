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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import task2.markapp.ScreenController;

/**
 *
 * @author berto
 */
public class GestioneMembriSquadraController implements Initializable {
    ObservableList<String> ruoli=FXCollections.observableArrayList("Allenatore","Osservatore","Amministratore delegato");
    Utente utente=null;
    @FXML
    private Text nomeSquadra;
    
    @FXML
    private Text titoloUtenteModificare;
    
    @FXML
    private Text errorScegliSquadra;

    @FXML
    private Text ruoloTeamSquadra;

    @FXML
    private Text nomeTeamSquadra;

    @FXML
    private Text emailTeamSquadra;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private Text errorCambia;

    @FXML
    private ChoiceBox<String> scegliTeamSquadra;
    

    @FXML
    void cercaTeamSquadra(ActionEvent event) {
        
        
        utente=new Utente();
        int er=GestioneProfiliMongoDataAccess.trovaUtenteInBaseAlRuolo(utente, ScreenController.getUtente().getSocieta().getNomeSocieta(), 
                ScreenController.getUtente().getSocieta().getNazione(),scegliTeamSquadra.getValue());//nomeSocieta.toLowerCase
        if(er==0){
            if(utente.getId()==null){
                errorScegliSquadra.setText("La società non ha un "+scegliTeamSquadra.getValue()+"!");
                ruoloTeamSquadra.setText("");
                nomeTeamSquadra.setText("");
                emailTeamSquadra.setText("");
                return;
            }
            errorScegliSquadra.setText(scegliTeamSquadra.getValue()+" trovato!");
            ruoloTeamSquadra.setText(scegliTeamSquadra.getValue());
            nomeTeamSquadra.setText(utente.getNome()+" "+utente.getCognome());
            emailTeamSquadra.setText(utente.getEmail());
            return;
        } else if(er==1){
            errorScegliSquadra.setText("La società non esiste!");
        } else{
            errorScegliSquadra.setText("Riprova più tardi!");
        }
        ruoloTeamSquadra.setText("");
        nomeTeamSquadra.setText("");
        emailTeamSquadra.setText("");
    }

    @FXML
    void modificaTeamSquadra(MouseEvent event) {
        if(emailInput.getText().isEmpty()){
            errorCambia.setText("Inserisci un email");
            return;
        }
        if(utente==null){
            errorCambia.setText("Fai la ricerca prima!");
            return;
        }
        int er=GestioneProfiliMongoDataAccess.aggiornaTeamSocieta(utente, emailInput.getText(),scegliTeamSquadra.getValue());//toLowerCase
        if(er==0){
            errorCambia.setText("Aggiornamento riuscito!");
            ruoloTeamSquadra.setText(scegliTeamSquadra.getValue());
            nomeTeamSquadra.setText(utente.getNome()+" "+utente.getCognome());
            emailTeamSquadra.setText(utente.getEmail());
        }else if(er==1){
            errorCambia.setText("Ruolo dell'utente non idoneo!");
        } else if(er==2){
            errorCambia.setText("L'utente non esiste!");
        } else if(er==3){
            errorCambia.setText("problemi con elimina da società");
        } else{
            errorCambia.setText("aggiornamento non riuscito riprova!");
        }
        utente=null;
        errorScegliSquadra.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scegliTeamSquadra.setItems(ruoli);
        scegliTeamSquadra.setValue("Allenatore");
        titoloUtenteModificare.setText(scegliTeamSquadra.getValue());
        scegliTeamSquadra.getSelectionModel().selectedIndexProperty()
        .addListener(new ChangeListener<Number>() {
          public void changed(ObservableValue ov, Number value, Number new_value) {
            titoloUtenteModificare.setText(scegliTeamSquadra.getItems().get(new_value.intValue()));
          }
        });
        nomeSquadra.setText(ScreenController.getUtente().getSocieta().getNomeSocieta().toUpperCase());
         
    }
}
