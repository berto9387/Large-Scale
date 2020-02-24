/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 *
 * @author berto
 * usa RicercaGiocatoriMongoDataAccess
 */
public class RicercaGiocatoriController extends GenerallController{
    @FXML
    private Text scegliNomeTesto;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private Text scegliCognomeTesto;

    @FXML
    private JFXTextField cognomeInput;

    @FXML
    private Text errorCercaCalciatore;

    @FXML
    private TableView<?> tabellaCalciatori;

    @FXML
    private TableColumn<?, ?> fotoColumn;

    @FXML
    private TableColumn<?, ?> nomeColumn;

    @FXML
    private TableColumn<?, ?> cognomeColumn;

    @FXML
    private TableColumn<?, ?> posizioneColumn;

    @FXML
    private TableColumn<?, ?> squadraColumn;

    @FXML
    private TableColumn<?, ?> etaColumn;

    @FXML
    private TableColumn<?, ?> nazionalitaColumn;

    @FXML
    void cercaCalciatore(ActionEvent event) {
        if(nomeInput.getText().isEmpty() && cognomeInput.getText().isEmpty()){
            errorCercaCalciatore.setText("Completa almeno uno dei campi!");
            return;
        }
        
    }
    void scegliNome(String newValue) {
        if(newValue.isEmpty())
            scegliNomeTesto.setVisible(false);
        else{
            scegliNomeTesto.setVisible(true);
        }
    }
    void scegliCognome(String newValue) {
        if(newValue.isEmpty())
            scegliCognomeTesto.setVisible(false);
        else{
            scegliCognomeTesto.setVisible(true);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliNome(newValue);
        });
        cognomeInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliCognome(newValue);
        });
    }
}
