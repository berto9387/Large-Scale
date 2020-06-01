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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author Gianluca
 */
public class RicercaGiocatoriController extends GeneralController{
    
    ObservableList<InformazioniRicercaCalciatore> values=FXCollections.observableArrayList();
    
    @FXML
    private Text scegliNomeTesto;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private Text scegliSquadraTesto;

    @FXML
    private JFXTextField squadraInput;

    @FXML
    private Text scegliPosizioneTesto;

    @FXML
    private JFXTextField posizioneInput;
    
    @FXML
    private Text errorCercaCalciatore;
    
    @FXML
    private VBox progressIndicatorContainer;

    @FXML
    private ProgressIndicator progressIndicator;

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
    //Callback<TableColumn<InformazioniRicercaCalciatoreSeguito, Void>, TableCell<InformazioniRicercaCalciatoreSeguito, Void>> cellFactory;

    void scegliNome(String newValue) {
        if(newValue.isEmpty())
            scegliNomeTesto.setVisible(false);
        else{
            scegliNomeTesto.setVisible(true);
        }
    }
    
    void scegliSquadra(String newValue) {
        if(newValue.isEmpty())
            scegliSquadraTesto.setVisible(false);
        else{
            scegliSquadraTesto.setVisible(true);
        }
    }
    
    void scegliPosizione(String newValue) {
        if(newValue.isEmpty())
            scegliPosizioneTesto.setVisible(false);
        else{
            scegliPosizioneTesto.setVisible(true);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliNome(newValue);
        });
        squadraInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliSquadra(newValue);
        });
        posizioneInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliPosizione(newValue);
        });
        
        fotoColumn.setPrefWidth(150);
        fotoColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nomeColumn.setCellValueFactory(cellData->cellData.getValue().nomeProperty());
        posizioneColumn.setCellValueFactory(cellData->cellData.getValue().ruoloProperty());
        squadraColumn.setCellValueFactory(cellData->cellData.getValue().squadraProperty());
        etaColumn.setCellValueFactory(cellData->cellData.getValue().etaProperty());
        seguitoDaColumn.setCellValueFactory(cellData->cellData.getValue().seguitoDaProperty().asObject());
        nazionalitaColumn.setCellValueFactory(cellData->cellData.getValue().nazionalitaProperty());
        valoreMercatoColumn.setCellValueFactory(cellData->cellData.getValue().valoreMercatoProperty().asObject());
        //seguiColumn.setCellFactory(cellFactory);
        
    }
    
    @FXML
    void cercaBandiere(ActionEvent event) {
        tabellaCalciatori.getItems().clear();
        
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriNeo4jDataAccess.ricercaBandiere();

                return infos;
            }

        };
        task.setOnSucceeded(evt -> {
            progressIndicatorContainer.setVisible(false);
            if(task.getValue().isEmpty()){
                return;
            }
                
            for(InformazioniRicercaCalciatore info : task.getValue()){
                values.add(info);
            }
            tabellaCalciatori.setItems(values);
            
            //autoResizeColumns(tabellaCalciatori);
        });
        progressIndicatorContainer.setVisible(true);
        new Thread(task).start();
    }
    
    @FXML
    void cercaCalciatore(ActionEvent event)
    {
        errorCercaCalciatore.setText("");
        tabellaCalciatori.getItems().clear();
        if(nomeInput.getText().isEmpty() && squadraInput.getText().isEmpty()){
            errorCercaCalciatore.setText("Completa almeno uno dei campi!");
            return;
        }
        
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriNeo4jDataAccess.ricercaCalciatori(nomeInput.getText(),squadraInput.getText());

                return infos;
            }

        };
        task.setOnSucceeded(evt -> {
            progressIndicatorContainer.setVisible(false);
            if(task.getValue().isEmpty()){
                errorCercaCalciatore.setText("Nessun calciatore trovato");
                return;
            }
                
            for(InformazioniRicercaCalciatore info : task.getValue()){
                values.add(info);
            }
            tabellaCalciatori.setItems(values);
            
            //autoResizeColumns(tabellaCalciatori);
        });
        progressIndicatorContainer.setVisible(true);
        new Thread(task).start();
    }

    @FXML
    void cercaConsigliati(ActionEvent event) {

    }

    @FXML
    void cercaPerPosizione(ActionEvent event) {

    }

    @FXML
    void cercaTop(ActionEvent event) {

    }
    
//    private void caricaDati() {
//        ObservableList<InformazioniRicercaCalciatore> values=FXCollections.observableArrayList();
//        RicercaGiocatoriNeo4jDataAccess.ricercaCalciatori().forEach((info) -> {
//            values.add(info);
//        });
//        
//        tabellaCalciatori.setItems(values);
//        autoResizeColumns(tabellaCalciatori); 
//    }    
}
