/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;

/**
 *
 * @author berto
 * gestisce la pagina fxml che appartiene all'admin che sceglie 
 * gli amministratori di squadra
 */
public class GestioneAmministratoreSquadraController implements Initializable{
     @FXML
    private Text scegliNazioneTesto;

    @FXML
    private JFXTextField nazioneInput;

    @FXML
    private JFXTextField squadraInput;

    @FXML
    private Text scegliSquadraTesto;

    @FXML
    void ScegliSquadra(String newValue) {
        if(newValue.isEmpty())
            scegliSquadraTesto.setVisible(false);
        else{
            scegliSquadraTesto.setVisible(true);
        }
    }

    @FXML
    void cercaAmministratoreSquadra(ActionEvent event) {
        
    }

    @FXML
    void scegliNazione(String newValue) {
        if(newValue.isEmpty())
                scegliNazioneTesto.setVisible(false);
        else{
                scegliNazioneTesto.setVisible(true);
        }
        
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
