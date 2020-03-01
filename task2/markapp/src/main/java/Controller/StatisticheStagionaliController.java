/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import Entita.Statistica;
import Model.StatisticaBeans;
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
public class StatisticheStagionaliController extends GeneralSchedaGiocatoreController{
    
    ObservableList<StatisticaBeans> values=FXCollections.observableArrayList();
    
    @FXML
    private ImageView imgCalciatore;
    
    @FXML
    private TableView<StatisticaBeans> tabellaStatistiche;

    @FXML
    private TableColumn<StatisticaBeans, String> stagioneColumn;

    @FXML
    private TableColumn<StatisticaBeans, String> competizioneColumn;

    @FXML
    private TableColumn<StatisticaBeans, String> squadraColumn;

    @FXML
    private TableColumn<StatisticaBeans, String> presenzeColumn;

    @FXML
    private TableColumn<StatisticaBeans, String> puntiPartitaColumn;

    @FXML
    private TableColumn<StatisticaBeans, String> goalColumn;
    
    @FXML
    private TableColumn<StatisticaBeans, String> assistColumn;
    
    @FXML
    private TableColumn<StatisticaBeans, String> ammonizioniColumn;
    
    @FXML
    private TableColumn<StatisticaBeans, String> doppieAmmonizioniColumn;
    
    @FXML
    private TableColumn<StatisticaBeans, String> espulsioniColumn;
    
    @FXML
    private TableColumn<StatisticaBeans, String> minutiGiocatiColumn;
    
    @FXML
    private TableColumn<StatisticaBeans, String> partiteNoGoalColumn;
    
    @FXML
    private TableColumn<StatisticaBeans, String> retiSubiteColumn;
    
    public StatisticheStagionaliController(Calciatore calciatore) {
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
        competizioneColumn.setCellValueFactory(cellData->cellData.getValue().competizioneProperty());
        squadraColumn.setCellValueFactory(cellData->cellData.getValue().squadraProperty());
        presenzeColumn.setCellValueFactory(cellData->cellData.getValue().presenzeProperty());
        puntiPartitaColumn.setCellValueFactory(cellData->cellData.getValue().puntiPartitaProperty());
        goalColumn.setCellValueFactory(cellData->cellData.getValue().goalProperty());
        ammonizioniColumn.setCellValueFactory(cellData->cellData.getValue().ammonizioniProperty());
        doppieAmmonizioniColumn.setCellValueFactory(cellData->cellData.getValue().doppieAmmonizioniProperty());
        espulsioniColumn.setCellValueFactory(cellData->cellData.getValue().espulsioniProperty());
        minutiGiocatiColumn.setCellValueFactory(cellData->cellData.getValue().minutiGiocatiProperty());
        if(calciatore.getRuoloPrincipale().equals("Portiere")){ //Se è un portiere
            partiteNoGoalColumn.setVisible(true);
            partiteNoGoalColumn.setCellValueFactory(cellData->cellData.getValue().partiteNoGoalProperty());
            retiSubiteColumn.setVisible(true);
            retiSubiteColumn.setCellValueFactory(cellData->cellData.getValue().retiSubiteProperty());
        }else{ //Se non è un portiere ha gli assist
            assistColumn.setVisible(true);
            assistColumn.setCellValueFactory(cellData->cellData.getValue().assistProperty());
        }
    
        tabellaStatistiche.getItems().clear();
        
        List<StatisticaBeans> statistiche = getInfoStatistiche(calciatore.getStatistiche());
        for(StatisticaBeans statistica :statistiche){
            values.add(statistica);
        }
        tabellaStatistiche.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabellaStatistiche.setItems(values);
    }

    private ArrayList<StatisticaBeans> getInfoStatistiche(ArrayList<Statistica> statistiche) {
        ArrayList<StatisticaBeans> ris = new ArrayList<>();
        
        for(Statistica statistica : statistiche){
            StatisticaBeans aux = new StatisticaBeans(statistica, calciatore.getRuoloPrincipale());
            ris.add(aux);
        }
        
        return ris;
    }
}
