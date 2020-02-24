/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javafx.fxml.FXML;
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
}
