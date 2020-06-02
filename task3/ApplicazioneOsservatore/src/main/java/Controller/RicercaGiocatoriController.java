/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.RicercaGiocatoriNeo4jDataAccess;
import Dao.RicercaOsservatoriNeo4jDataAccess;
import Model.InformazioniRicercaCalciatore;
import com.jfoenix.controls.JFXTextField;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    private ChoiceBox<String> posizioneInput;
    
    private boolean numClick = false;
    
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
    private TableColumn<InformazioniRicercaCalciatore, Void> seguiColumn;
    Callback<TableColumn<InformazioniRicercaCalciatore, Void>, TableCell<InformazioniRicercaCalciatore, Void>> cellFactory = (final TableColumn<InformazioniRicercaCalciatore, Void> param) -> {
        final TableCell<InformazioniRicercaCalciatore, Void> cell = new TableCell<InformazioniRicercaCalciatore, Void>() {
            
            Button btn = new Button();          
            
            {
                    btn.getStyleClass().add("bottoneElimina");
                    Image image = new Image("/img/follow.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    
                    btn.setGraphic(imageView);
                    btn.setOnAction((ActionEvent event) -> {   
                        InformazioniRicercaCalciatore data = getTableView().getItems().get(getIndex());
                        int er;
                        er=RicercaGiocatoriNeo4jDataAccess.follow(ScreenController.getUtente().getEmail(),data.getIdCalciatore());
                        if(er==0){
                            getTableView().getItems().remove(getIndex());
                            getTableView().refresh();
                        }
                    });
                
                
            }            
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    
                    setGraphic(btn);                    
                } else {
                    setGraphic(null);
                }
            }
        };
        return cell;
        };

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
        
        fotoColumn.setPrefWidth(150);
        fotoColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nomeColumn.setCellValueFactory(cellData->cellData.getValue().nomeProperty());
        posizioneColumn.setCellValueFactory(cellData->cellData.getValue().ruoloProperty());
        squadraColumn.setCellValueFactory(cellData->cellData.getValue().squadraProperty());
        etaColumn.setCellValueFactory(cellData->cellData.getValue().etaProperty());
        seguitoDaColumn.setCellValueFactory(cellData->cellData.getValue().seguitoDaProperty().asObject());
        nazionalitaColumn.setCellValueFactory(cellData->cellData.getValue().nazionalitaProperty());
        valoreMercatoColumn.setCellValueFactory(cellData->cellData.getValue().valoreMercatoProperty().asObject());
        seguiColumn.setCellFactory(cellFactory);
        
        //posizioneInput.setItems(ScreenController.getRuoloInCampo());
        posizioneInput.getItems().add("Cerca per posizione");
        posizioneInput.setValue("Cerca per posizione");
        posizioneInput.setOnMouseClicked(ev ->{
            
            if(numClick == false)
            {
                posizioneInput.getItems().remove(0);
                posizioneInput.getItems().addAll(ScreenController.getRuoloInCampo());
                numClick = !numClick;
            } else
            {
                if(posizioneInput.getValue() == null){
                    posizioneInput.getItems().removeAll(ScreenController.getRuoloInCampo());
                    posizioneInput.getItems().add("Cerca per posizione");
                    posizioneInput.setValue("Cerca per posizione");
                    numClick = !numClick;
                }
            }
            
        });
        
    }
    
    @FXML
    void cercaBandiere(ActionEvent event) {
        tabellaCalciatori.getItems().clear();
        
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriNeo4jDataAccess.ricercaBandiere(ScreenController.getUtente().getEmail());

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
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriNeo4jDataAccess.ricercaCalciatori(nomeInput.getText(),squadraInput.getText(), ScreenController.getUtente().getEmail());

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
        tabellaCalciatori.getItems().clear();
        
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriNeo4jDataAccess.ricercaConsigliati(ScreenController.getUtente().getEmail());

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
    void cercaPerPosizione(ActionEvent event) {
        tabellaCalciatori.getItems().clear();
        
        
        if(posizioneInput.getValue().isEmpty()){
            return;
        }
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriNeo4jDataAccess.ricercaPosizione(posizioneInput.getValue(), ScreenController.getUtente().getEmail());

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
            
            posizioneInput.getItems().removeAll(ScreenController.getRuoloInCampo());
            posizioneInput.getItems().add("Cerca per posizione");
            posizioneInput.setValue("Cerca per posizione");
        
            numClick = !numClick;

            //autoResizeColumns(tabellaCalciatori);
        });
        progressIndicatorContainer.setVisible(true);
        new Thread(task).start();
        
        
    }

    @FXML
    void cercaTop(ActionEvent event) {
        tabellaCalciatori.getItems().clear();
        
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriNeo4jDataAccess.ricercaTop(ScreenController.getUtente().getEmail());

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
