package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import task2.markapp.MainApp;
import task2.markapp.ScreenController;
import static task2.markapp.ScreenController.utente;

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
        nomeAdmin.setText(utente.getNome() + " " + utente.getCognome());
        emailAdmin.setText(utente.getEmail());
    }
    
    @FXML
    public void handlGestioneUtenti(MouseEvent event){
        
        ScreenController.showPage("prova2");
        
    }
    
    

}

