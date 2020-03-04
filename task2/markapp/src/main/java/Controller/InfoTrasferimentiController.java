/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import Entita.Trasferimento;
import Model.TrasferimentiBeans;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author tony_
 */
public class InfoTrasferimentiController extends GeneralSchedaGiocatoreController{

    
    ObservableList<TrasferimentiBeans> values=FXCollections.observableArrayList();
    
    @FXML
    private ImageView imgCalciatore;
    
    @FXML
    private TableView<TrasferimentiBeans> tabellaTrasferimenti ;

    @FXML
    private TableColumn<TrasferimentiBeans, String> stagioneColumn;

    @FXML
    private TableColumn<TrasferimentiBeans, String> dataTrasferimentoColumn;

    @FXML
    private TableColumn<TrasferimentiBeans, String> venditoreColumn;

    @FXML
    private TableColumn<TrasferimentiBeans, String> acquirenteColumn;

    @FXML
    private TableColumn<TrasferimentiBeans, String> valoreMercatoColumn;

    @FXML
    private TableColumn<TrasferimentiBeans, String> riscattoColumn;
    
    public InfoTrasferimentiController(Calciatore calciatore) {
        super(calciatore);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        super.initialize(location, resources);
        
        nomeCalciatore.setText(calciatore.getNome());
        stagioneColumn.setCellValueFactory(cellData->cellData.getValue().stagioneProperty());
        dataTrasferimentoColumn.setCellValueFactory(cellData->cellData.getValue().dataTrasferimentoProperty());
        venditoreColumn.setCellValueFactory(cellData->cellData.getValue().venditoreProperty());
        acquirenteColumn.setCellValueFactory(cellData->cellData.getValue().acquirenteProperty());
        valoreMercatoColumn.setCellValueFactory(cellData->cellData.getValue().valoreMercatoProperty());
        riscattoColumn.setCellValueFactory(cellData->cellData.getValue().riscattoProperty());
    
        tabellaTrasferimenti.getItems().clear();
        
        List<TrasferimentiBeans> trasferimenti = getInfoTrasferimenti(calciatore.getTrasferimenti());
        for(TrasferimentiBeans trasferimento : trasferimenti){
            values.add(trasferimento);
        }
        tabellaTrasferimenti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabellaTrasferimenti.setItems(values);
    }
    
    private ArrayList<TrasferimentiBeans> getInfoTrasferimenti(ArrayList<Trasferimento> trasferimenti) {
        ArrayList<TrasferimentiBeans> ris = new ArrayList<>();
        
        for(Trasferimento trasferimento : trasferimenti){
            TrasferimentiBeans aux = new TrasferimentiBeans(trasferimento);
            ris.add(aux);
        }
        
        return ris;
    }
    
}
