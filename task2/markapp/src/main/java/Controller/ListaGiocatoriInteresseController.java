package Controller;

import Dao.RicercaGiocatoriMongoDataAccess;
import Entita.Calciatore;
import Model.InformazioniRicercaCalciatore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import task2.markapp.ScreenController;

public class ListaGiocatoriInteresseController extends GenerallController {

    @FXML
    private TableView<InformazioniRicercaCalciatore> listaInteresseTabella;
    
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
    private TableColumn<InformazioniRicercaCalciatore, Circle> dirigenzaColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, Circle> allenatoreColumn;

    @FXML
    private TableColumn<InformazioniRicercaCalciatore, Void> eliminaColumn;
    Callback<TableColumn<InformazioniRicercaCalciatore, Void>, TableCell<InformazioniRicercaCalciatore, Void>> cellFactory = (final TableColumn<InformazioniRicercaCalciatore, Void> param) -> {
        final TableCell<InformazioniRicercaCalciatore, Void> cell = new TableCell<InformazioniRicercaCalciatore, Void>() {
            
            Button btn = new Button();
            
            
            {
                    btn.getStyleClass().add("bottoneElimina");
                    Image image = new Image("/img/delete1.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);

                    btn.setGraphic(imageView);
                    btn.setOnAction((ActionEvent event) -> {
                        InformazioniRicercaCalciatore data = getTableView().getItems().get(getIndex());
                        int er=RicercaGiocatoriMongoDataAccess.rimuoviCalciatore(data.getIdCalciatore());
                        if(er>0){
                            getTableView().getItems().remove(getIndex());
                            getTableView().refresh();
                        }
                        
                            
                        
                    });
                
                
            }
            
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    InformazioniRicercaCalciatore data = (InformazioniRicercaCalciatore) getTableRow().getItem();
                    if(data!=null && data.getPropostoDa().toLowerCase().equals(ScreenController.getUtente().getRuolo().toLowerCase()))
                        setGraphic(btn);
                    else
                       setGraphic(null); 
                } else {
                    setGraphic(null);
                }
            }
        };
        return cell;
    };
    
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
        dirigenzaColumn.setCellValueFactory(cellData->cellData.getValue().giudizioDirigenzaProperty());
        allenatoreColumn.setCellValueFactory(cellData->cellData.getValue().giudizioAllenatoreProperty());
        eliminaColumn.setCellFactory(cellFactory);
        
        listaInteresseTabella.setRowFactory((TableView<InformazioniRicercaCalciatore> tv) -> { //Funzione per individuare doppio click su un elemento della tabella
            TableRow<InformazioniRicercaCalciatore> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    
                    InformazioniRicercaCalciatore calciatoreTarget = row.getItem();
                    System.out.println("Giocatore selezionato: "+ calciatoreTarget.getIdCalciatore());
                    
                    Calciatore calciatore = new Calciatore();
                    
                    calciatore = RicercaGiocatoriMongoDataAccess.ricercaPerId(calciatoreTarget.getIdCalciatore());
                    if(calciatore != null){
                        calciatore.setReport(calciatoreTarget.getReport());
                        ScreenController.showPageCalciatore("InfoPrincipaliCalciatore", calciatore);
                    }
                        
                }
            });
            return row ;
        });
        caricaDati();
    }

    private void caricaDati() {
        ObservableList<InformazioniRicercaCalciatore> values=FXCollections.observableArrayList();
        RicercaGiocatoriMongoDataAccess.ricercaGiocatoriPreferiti().forEach((info) -> {
            values.add(info);
        });
        
        listaInteresseTabella.setItems(values);
        autoResizeColumns(listaInteresseTabella); 
    }

}
