/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.RicercaGiocatoriMongoDataAccess;
import Entita.Calciatore;
import Model.InformazioniRicercaCalciatore;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.RangeSlider;
import task2.markapp.ScreenController;

/**
 *
 * @author berto
 * usa RicercaGiocatoriMongoDataAccess
 */

public class RicercaGiocatoriController extends GenerallController{
    ObservableList<InformazioniRicercaCalciatore> values=FXCollections.observableArrayList();
    private  ObservableList<String> statoContratto=FXCollections.observableArrayList
        ("In scadenza","Svincolato","In contratto");
    
    @FXML
    private Text scegliNomeTesto;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private Text scegliCognomeTesto;

    @FXML
    private JFXTextField cognomeInput;

    @FXML
    private Text errorCercaCalciatore;

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
    private TableColumn<InformazioniRicercaCalciatore, String> nazionalitaColumn;
    
    @FXML
    private JFXTextField inputCampionato;
    @FXML
    private JFXTextField inputStagione;
    @FXML
    private JFXTextField inputSquadra;

    @FXML
    private ChoiceBox<String> inputPosizione;

    @FXML
    private JFXSlider inputMediaInfortuniStagionali;

    @FXML
    private JFXSlider inputMediaGoalStagionali;

    @FXML
    private JFXSlider inputMediaAssistStagionali;

    @FXML
    private JFXSlider inputMediaGoalSubiti;

    @FXML
    private JFXSlider inputMediaCartellini;

    @FXML
    private RangeSlider inputValoreDiMercato;

    @FXML
    private RangeSlider inputAltezza;

    @FXML
    private RangeSlider inputEta;

    @FXML
    private ChoiceBox<String> inputContratto;
    
    @FXML
    private HBox soloPortiere;
    
     @FXML
    private VBox progressIndicatorContainer;
     
    @FXML
    private HBox containerAssist;
     
    @FXML
    private HBox containerGoal;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    void cercaCalciatore(ActionEvent event) {
        errorCercaCalciatore.setText("");
        tabellaCalciatori.getItems().clear();
        if(nomeInput.getText().isEmpty() && cognomeInput.getText().isEmpty()){
            errorCercaCalciatore.setText("Completa almeno uno dei campi!");
            return;
        }
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriMongoDataAccess.ricerca(nomeInput.getText(),cognomeInput.getText());

                return infos;
            }

        };
        task.setOnSucceeded(evt -> {
            progressIndicatorContainer.setVisible(false);
            if(task.getValue().isEmpty()){
                errorCercaCalciatore.setText("Nessun calciatore trovato");
                return;
            }
                
            for(InformazioniRicercaCalciatore info :task.getValue()){
                values.add(info);
            }
            tabellaCalciatori.setItems(values);
            
            autoResizeColumns(tabellaCalciatori);
        });
        progressIndicatorContainer.setVisible(true);
        new Thread(task).start();
        

        
    }
    void scegliNome(String newValue) {
        if(newValue.isEmpty())
            scegliNomeTesto.setVisible(false);
        else{
            scegliNomeTesto.setVisible(true);
        }
    }
    void scegliCognome(String newValue) {
        if(newValue.isEmpty())
            scegliCognomeTesto.setVisible(false);
        else{
            scegliCognomeTesto.setVisible(true);
        }
    }
@FXML
    void ricercaAvanzata(ActionEvent event) {
        errorCercaCalciatore.setText("");
        tabellaCalciatori.getItems().clear();
        String regex = "^(\\d{2}\\/\\d{2})$";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(inputStagione.getText());
        if(!matcher.find()){
            errorCercaCalciatore.setText("Formato stagione: '18/19'");
        }
        progressIndicatorContainer.setVisible(true);
        Task<List<InformazioniRicercaCalciatore>> task = new Task<List<InformazioniRicercaCalciatore>>() {

            @Override
            protected List<InformazioniRicercaCalciatore> call() throws Exception {
                
                long startTime = System.nanoTime();
                List<InformazioniRicercaCalciatore> infos=RicercaGiocatoriMongoDataAccess.ricercaAvanzataStat(
                    inputCampionato.getText(),inputStagione.getText(),inputSquadra.getText(),inputPosizione.getValue(),
                    (int)inputValoreDiMercato.getLowValue(),(int)inputValoreDiMercato.getHighValue(),
                    (int)inputAltezza.getLowValue(),(int)inputAltezza.getHighValue(),
                    (int)inputEta.getLowValue(),(int)inputEta.getHighValue(),inputContratto.getValue(),
                    inputMediaGoalStagionali.getValue(),inputMediaAssistStagionali.getValue(),
                    inputMediaGoalSubiti.getValue(),inputMediaCartellini.getValue()
                );
                long elapsedTime = System.nanoTime() - startTime;
                System.out.println(elapsedTime/1000000);
                return infos;
            }
            
        };
        task.setOnSucceeded(evt -> {
            progressIndicatorContainer.setVisible(false);
            if(task.getValue()==null){
                errorCercaCalciatore.setText("Usa dei filtri migliori!");
                return;
                
            }
            if(task.getValue().isEmpty()){
                errorCercaCalciatore.setText("Nessun calciatore trovato!");
                return;
            }    
            for(InformazioniRicercaCalciatore info :task.getValue()){
                values.add(info);
            }
            tabellaCalciatori.setItems(values);
            autoResizeColumns(tabellaCalciatori);
        });
        
        progressIndicatorContainer.setVisible(true);
        new Thread(task).start();
            
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliNome(newValue);
        });
        cognomeInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliCognome(newValue);
        });
        
        fotoColumn.setPrefWidth(150);
        fotoColumn.setCellValueFactory(new PropertyValueFactory<InformazioniRicercaCalciatore, ImageView>("image"));
        nomeColumn.setCellValueFactory(cellData->cellData.getValue().nomeProperty());
        posizioneColumn.setCellValueFactory(cellData->cellData.getValue().ruoloProperty());
        squadraColumn.setCellValueFactory(cellData->cellData.getValue().squadraProperty());
        etaColumn.setCellValueFactory(cellData->cellData.getValue().etaProperty());
        nazionalitaColumn.setCellValueFactory(cellData->cellData.getValue().nazionalitaProperty());
        
        tabellaCalciatori.setRowFactory(tv -> { //Funzione per individuare doppio click su un elemento della tabella
            TableRow<InformazioniRicercaCalciatore> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    
                    InformazioniRicercaCalciatore calciatoreTarget = row.getItem();
                    System.out.println("Giocatore selezionato: "+ calciatoreTarget.getIdCalciatore());
                    
                    Calciatore calciatore = new Calciatore();
                    
                    calciatore = RicercaGiocatoriMongoDataAccess.ricercaPerId(calciatoreTarget.getIdCalciatore());
                    if(calciatore != null)
                        ScreenController.showPageCalciatore("InfoPrincipaliCalciatore", calciatore);
                }
            });
            return row ;
        });
        //ricerca avanzata
        inputPosizione.setItems(ScreenController.getRuoloInCampo());
        inputContratto.setItems(statoContratto);
        //evento choice box per la selezione del portiere
        inputPosizione.getSelectionModel().selectedItemProperty()
        .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) ->{
            if("Portiere".equals(inputPosizione.getValue())){
                soloPortiere.setVisible(true);
                containerAssist.setVisible(false);
                containerGoal.setVisible(false);
            } else {
                soloPortiere.setVisible(false);
                containerAssist.setVisible(true);
                containerGoal.setVisible(true);
            }
        });
    }
}
