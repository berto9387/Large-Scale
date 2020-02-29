/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
/**
 * FXML Controller class
 *
 * @author Gianluca
 */
public class GestioneProfiloGiocatoreInteresseAllenatoreController implements Initializable {
    
    @FXML
    private VBox areaModificaProfili;
    @FXML
    private ChoiceBox sceltaRuolo;
    @FXML
    private TextField etaMinimaTextField;
    @FXML
    private TextField etaMassimaTextField;
    @FXML
    private Button incrementMin;
    @FXML
    private Button decrementMin;
    @FXML
    private Button incrementMax;
    @FXML
    private Button decrementMax;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sceltaRuolo.setItems(null);
    }    
    
}
