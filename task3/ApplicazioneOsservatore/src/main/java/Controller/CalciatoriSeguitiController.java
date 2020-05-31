/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.RicercaGiocatoriNeo4jDataAccess;
import Model.InformazioniRicercaCalciatoreSeguito;
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
import javafx.util.Callback;

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
    
     @FXML
    private TableColumn<InformazioniRicercaCalciatoreSeguito, Void> eliminaColumn;
    Callback<TableColumn<InformazioniRicercaCalciatoreSeguito, Void>, TableCell<InformazioniRicercaCalciatoreSeguito, Void>> cellFactory = (final TableColumn<InformazioniRicercaCalciatoreSeguito, Void> param) -> {
        final TableCell<InformazioniRicercaCalciatoreSeguito, Void> cell = new TableCell<InformazioniRicercaCalciatoreSeguito, Void>() {
            
            Button btn = new Button();
            
            {
                    btn.getStyleClass().add("bottoneElimina");
                    Image image = new Image("/img/delete1.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);

                    btn.setGraphic(imageView);
                    btn.setOnAction((ActionEvent event) -> {
                        InformazioniRicercaCalciatoreSeguito data = getTableView().getItems().get(getIndex());
                        int er=RicercaGiocatoriNeo4jDataAccess.rimuoviCalciatoreSeguito(data.getIdCalciatore());
                        System.out.println("-----> qui entro");
                        if(er==0){
                            System.out.println("-----> er = 0 !!");
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
        eliminaColumn.setCellFactory(cellFactory);
        
        
        caricaDati();
    }
    
    private void caricaDati() {
        ObservableList<InformazioniRicercaCalciatoreSeguito> values=FXCollections.observableArrayList();
        RicercaGiocatoriNeo4jDataAccess.ricercaGiocatoriSeguiti().forEach((info) -> {
            values.add(info);
        });
        
        listaInteresseTabella.setItems(values);
        autoResizeColumns(listaInteresseTabella); 
    }
}
    

