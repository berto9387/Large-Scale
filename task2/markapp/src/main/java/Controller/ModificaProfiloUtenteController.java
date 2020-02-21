/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GestioneProfiliMongoDataAccess;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import task2.markapp.ScreenController;
import static task2.markapp.ScreenController.utente;

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
        nomeUtenteLabel.setText(utente.getNome() + " " + utente.getCognome());
        emailAttualeLabel.setText(utente.getEmail());
    }
    
    @FXML
    void handleEliminaAccount(MouseEvent event) throws IOException {
        
        int err = GestioneProfiliMongoDataAccess.eliminaAccount(utente.getEmail(), utente.getRuolo(), utente.getId());
        if(err == 0)
            ScreenController.showLogin();
        
        else
            errEliminaAccountLabel.setVisible(true);
        
    }

    @FXML
    void handleModificaDati(MouseEvent event) {

    }
}
