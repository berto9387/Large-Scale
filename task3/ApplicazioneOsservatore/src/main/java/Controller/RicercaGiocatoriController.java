/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.RicercaGiocatoriNeo4jDataAccess;
import Model.InformazioniRicercaCalciatore;
import Model.InformazioniRicercaCalciatoreSeguito;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author Gianluca
 */
public class RicercaGiocatoriController extends GeneralController{
     @FXML
    private Text scegliNomeTesto;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private Text scegliSquadraTesto;

    @FXML
    private JFXTextField squadraInput;

    @FXML
    private TableView<InformazioniRicercaCalciatore> tabellaCalciatori;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, ImageView> fotoColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, String> nomeColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, String> posizioneColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, String> squadraColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, String> etaColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, Integer> valoreMercatoColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, String> nazionalitaColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, Integer> seguitoDaColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, Void> seguiColumn;
    Callback<TableColumn<InformazioniRicercaCalciatoreSeguito, Void>, TableCell<InformazioniRicercaCalciatoreSeguito, Void>> cellFactory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fotoColumn.setPrefWidth(150);
        fotoColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nomeColumn.setCellValueFactory(cellData->cellData.getValue().nomeProperty());
        posizioneColumn.setCellValueFactory(cellData->cellData.getValue().ruoloProperty());
        squadraColumn.setCellValueFactory(cellData->cellData.getValue().squadraProperty());
        etaColumn.setCellValueFactory(cellData->cellData.getValue().etaProperty());
        nazionalitaColumn.setCellValueFactory(cellData->cellData.getValue().nazionalitaProperty());
        valoreMercatoColumn.setCellValueFactory(cellData->cellData.getValue().valoreMercatoProperty().asObject());
        seguiColumn.setCellFactory(cellFactory);
        
    }
    
    private void caricaDati() {
        ObservableList<InformazioniRicercaCalciatore> values=FXCollections.observableArrayList();
        RicercaGiocatoriNeo4jDataAccess.ricercaCalciatori().forEach((info) -> {
            values.add(info);
        });
        
        tabellaCalciatori.setItems(values);
        autoResizeColumns(tabellaCalciatori); 
    }    
}
