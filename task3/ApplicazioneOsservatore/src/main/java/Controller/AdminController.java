/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import Entita.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author Gianluca
 */
public class AdminController extends GeneralController{
    @FXML
    private ImageView imgAdmin;

    @FXML
    private Label nomeAdmin;

    @FXML
    private Label emailAdmin;

    @FXML
    private HBox gestioneUtenteButton;
    
    

    @FXML
    void apriGestioneUtente(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utente utente = ScreenController.getUtente();
        nomeAdmin.setText(utente.getNome() + " " + utente.getCognome());
        emailAdmin.setText(utente.getEmail());
    }
    
    @FXML
    public void handleGestioneUtenti(MouseEvent event){
        
        ScreenController.showPage("GestioneUtenti");
        
    }

    @FXML
    void handleModificaProfilo(MouseEvent event) {
        ScreenController.showPage("ModificaProfiloPersonale");
    }
    
    @FXML
    void handleAggiornaDb(MouseEvent event) {
        ScreenController.showPage("AggiornaDb");
    }
}
