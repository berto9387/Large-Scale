package it.unipi.task3.applicazioneosservatore;

import Entita.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ScreenController implements Initializable {
    
    @FXML
    private Label label;
    private static Utente utente;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    private static void setUtente(Utente user){
        utente=user;
    }
    private static Utente getUtente(){
        return utente;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
