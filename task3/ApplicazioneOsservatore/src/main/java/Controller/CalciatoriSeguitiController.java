/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InformazioniRicercaCalciatoreSeguito;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author tony_
 */
public class CalciatoriSeguitiController extends GeneralController {
    
    @FXML
    private TableView<InformazioniRicercaCalciatoreSeguito> listaInteresseTabella;
    
    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, ImageView> fotoColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, String> nomeColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, String> posizioneColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, String> squadraColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, String> etaColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, Integer> valoreMercatoColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, String> nazionalitaColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
