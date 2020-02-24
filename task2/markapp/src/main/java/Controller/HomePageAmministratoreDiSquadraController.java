/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import task2.markapp.ScreenController;

/**
 *
 * @author berto
 * usa GestioneProfiliMongoDb
 */
public class HomePageAmministratoreDiSquadraController extends GenerallController{
    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView imgAdmin;

    @FXML
    private Label nomeAdmin;

    @FXML
    private Label emailAdmin;

    

    @FXML
    void handleGestioneMembriSquadra(MouseEvent event) {
        ScreenController.showPage("GestioneMembriSquadra");
    }

    @FXML
    void handleModificaProfilo(MouseEvent event) {
        ScreenController.showPage("ModificaProfiloPersonale");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utente utente = ScreenController.getUtente();
        nomeAdmin.setText(utente.getNome() + " " + utente.getCognome());
        emailAdmin.setText(utente.getEmail());
    }
}
