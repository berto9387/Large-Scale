/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import Entita.Infortunio;
import Model.InfortunioBeans;
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

/**
 *
 * @author tony_
 */
public class InfoInfortuniController extends GeneralSchedaGiocatoreController{
    
    ObservableList<InfortunioBeans> values=FXCollections.observableArrayList();
    
    @FXML
    private TableView<InfortunioBeans> tabellaInfortuni;

    @FXML
    private TableColumn<InfortunioBeans, String> stagioneColumn;

    @FXML
    private TableColumn<InfortunioBeans, String> tipoInfortunioColumn;

    @FXML
    private TableColumn<InfortunioBeans, String> inizioColumn;

    @FXML
    private TableColumn<InfortunioBeans, String> fineColumn;

    @FXML
    private TableColumn<InfortunioBeans, String> durataColumn;

    @FXML
    private TableColumn<InfortunioBeans, String> partitePerseColumn;
    
    
    public InfoInfortuniController(Calciatore calciatore) {
        super(calciatore);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Image image = new Image(calciatore.getLinkFoto());
            imgCalciatore.setImage(image);
        } catch(Exception e){
            e.printStackTrace();
        }
        
        nomeCalciatore.setText(calciatore.getNome());
        stagioneColumn.setCellValueFactory(cellData->cellData.getValue().stagioneProperty());
        tipoInfortunioColumn.setCellValueFactory(cellData->cellData.getValue().tipoInfortunioProperty());
        inizioColumn.setCellValueFactory(cellData->cellData.getValue().inizioProperty());
        fineColumn.setCellValueFactory(cellData->cellData.getValue().fineProperty());
        durataColumn.setCellValueFactory(cellData->cellData.getValue().durataProperty());
        partitePerseColumn.setCellValueFactory(cellData->cellData.getValue().partitePerseProperty());
    
        tabellaInfortuni.getItems().clear();
        
        List<InfortunioBeans> infortuni = getInfoInfortuni(calciatore.getInfortuni());
        
        for(InfortunioBeans infortunio : infortuni){
            values.add(infortunio);
        }
        tabellaInfortuni.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabellaInfortuni.setItems(values);
    }
    
    private ArrayList<InfortunioBeans> getInfoInfortuni(ArrayList<Infortunio> infortuni) {
        ArrayList<InfortunioBeans> ris = new ArrayList<>();
        
        for(Infortunio infortunio : infortuni){
            InfortunioBeans aux = new InfortunioBeans(infortunio);
            ris.add(aux);
        }
        
        return ris;
    }
    
}
