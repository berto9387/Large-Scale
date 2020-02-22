package Controller;

import Entita.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import task2.markapp.ScreenController;

public class AdminController extends GenerallController{

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
        
        
        
    }
    @FXML
    public void handleGestioneAmministratoreSquadra(MouseEvent event){
        
        ScreenController.showPage("GestioneAmministratoreSquadra");
        
    }
    @FXML
    void handleModificaProfilo(MouseEvent event) {
        ScreenController.showPage("ModificaProfiloPersonale");
    }
    
    

}

