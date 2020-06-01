package Controller;

import Dao.RicercaOsservatoriNeo4jDataAccess;
import Model.InformazioniOsservatore;
import com.jfoenix.controls.JFXTextField;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class OsservatoriSeguitiController extends GeneralController{
    ObservableList<InformazioniOsservatore> values=FXCollections.observableArrayList();
    @FXML
    private Text scegliNomeTesto;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private Text scegliSquadraTesto;

    @FXML
    private JFXTextField SquadraInput;

    @FXML
    private Text errorCercaOsservatore;

    @FXML
    private TableView<InformazioniOsservatore> tabellaCalciatori;

    @FXML
    private TableColumn<InformazioniOsservatore, String> nomeColumn;

    @FXML
    private TableColumn<InformazioniOsservatore, String> cognomeColumn;

    @FXML
    private TableColumn<InformazioniOsservatore, String> squadraColumn;

    @FXML
    private TableColumn<InformazioniOsservatore, String> calciatoriColumn;

    @FXML
    private Text errorConsigliami;
    
    @FXML
    private TableColumn<InformazioniOsservatore, Void> eliminaColumn;
    Callback<TableColumn<InformazioniOsservatore, Void>, TableCell<InformazioniOsservatore, Void>> cellFactory = (final TableColumn<InformazioniOsservatore, Void> param) -> {
        final TableCell<InformazioniOsservatore, Void> cell = new TableCell<InformazioniOsservatore, Void>() {
            
            Button btn = new Button();          
            
            {
                    btn.getStyleClass().add("bottoneElimina");
                    Image image = new Image("/img/user.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    
                    btn.setGraphic(imageView);
                    btn.setOnAction((ActionEvent event) -> {   
                        InformazioniOsservatore data = getTableView().getItems().get(getIndex());
                        int er;
                        if(Objects.equals(data.getSeguito(), Boolean.TRUE))
                            er=RicercaOsservatoriNeo4jDataAccess.noFollow(ScreenController.getUtente().getEmail(),data.getIdOsservatore());
                        else
                            er=RicercaOsservatoriNeo4jDataAccess.follow(ScreenController.getUtente().getEmail(),data.getIdOsservatore());
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
    @FXML
    private Text errorCercaOsservatoreSeguiti;

    @FXML
    private VBox progressIndicatorContainer;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    void cercaOsservatore(ActionEvent event) {
        errorCercaOsservatore.setText("");
        tabellaCalciatori.getItems().clear();
        if(nomeInput.getText().isEmpty() && SquadraInput.getText().isEmpty()){
            errorCercaOsservatore.setText("Completa almeno uno dei campi!");
            return;
        }
        Task<List<InformazioniOsservatore>> task = new Task<List<InformazioniOsservatore>>() {

            @Override
            protected List<InformazioniOsservatore> call() throws Exception {
                List<InformazioniOsservatore> infos=RicercaOsservatoriNeo4jDataAccess.cercaOsservatori(nomeInput.getText(),SquadraInput.getText());

                return infos;
            }

        };
        task.setOnSucceeded(evt -> {
            progressIndicatorContainer.setVisible(false);
            if(task.getValue().isEmpty()){
                errorCercaOsservatore.setText("Nessun osservatore trovato");
                return;
            }
                
            for(InformazioniOsservatore info :task.getValue()){
                values.add(info);
            }
            tabellaCalciatori.setItems(values);
            
            //autoResizeColumns(tabellaCalciatori);
        });
        progressIndicatorContainer.setVisible(true);
        new Thread(task).start();
    }
     @FXML
    void consigliami(ActionEvent event) {
        errorConsigliami.setText("");
        tabellaCalciatori.getItems().clear();
        Task<List<InformazioniOsservatore>> task = new Task<List<InformazioniOsservatore>>() {

            @Override
            protected List<InformazioniOsservatore> call() throws Exception {
                List<InformazioniOsservatore> infos=RicercaOsservatoriNeo4jDataAccess.cercaConsigli(ScreenController.getUtente().getEmail());

                return infos;
            }

        };
        task.setOnSucceeded(evt -> {
            progressIndicatorContainer.setVisible(false);
            if(task.getValue().isEmpty()){
                errorConsigliami.setText("Nessun osservatore trovato");
                return;
            }
                
            for(InformazioniOsservatore info :task.getValue()){
                values.add(info);
            }
            tabellaCalciatori.setItems(values);
            
            //autoResizeColumns(tabellaCalciatori);
        });
        progressIndicatorContainer.setVisible(true);
        new Thread(task).start();
    }
    @FXML
    void cercaOsservatoreSeguito(ActionEvent event) {
        //errorCercaOsservatoreSeguiti.setText("");
        tabellaCalciatori.getItems().clear();
        
        RicercaOsservatoriNeo4jDataAccess.cercaOsservatoriSeguiti(ScreenController.getUtente().getEmail()).forEach((info) -> {
            values.add(info);
        });
        tabellaCalciatori.setItems(values);
        //autoResizeColumns(tabellaCalciatori); 
        
    }
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliNome(newValue);
        });
        SquadraInput.textProperty().addListener((Observable, oldValue, newValue) -> {
            scegliSquadra(newValue);
        });
        
        nomeColumn.setCellValueFactory(cellData->cellData.getValue().nomeProperty());
        cognomeColumn.setCellValueFactory(cellData->cellData.getValue().cognomeProperty());
        squadraColumn.setCellValueFactory(cellData->cellData.getValue().squadraProperty());
        calciatoriColumn.setCellValueFactory(cellData->cellData.getValue().numeroCalciatoriProperty());
        eliminaColumn.setCellFactory(cellFactory);
        
        
        
    }
}
