/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import Entita.Statistica;
import Model.InformazioniStatistiche;
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
    
    ObservableList<InformazioniStatistiche> values=FXCollections.observableArrayList();
    
    @FXML
    private ImageView imgCalciatore;
    
    @FXML
    private TableView<InformazioniStatistiche> tabellaStatistiche ;

    @FXML
    private TableColumn<InformazioniStatistiche, String> stagioneColumn;

    @FXML
    private TableColumn<InformazioniStatistiche, String> competizioneColumn;

    @FXML
    private TableColumn<InformazioniStatistiche, String> squadraColumn;

    @FXML
    private TableColumn<InformazioniStatistiche, String> presenzeColumn;

    @FXML
    private TableColumn<InformazioniStatistiche, String> puntiPartitaColumn;

    @FXML
    private TableColumn<InformazioniStatistiche, String> goalColumn;
    
    @FXML
    private TableColumn<InformazioniStatistiche, String> assistColumn;
    
    @FXML
    private TableColumn<InformazioniStatistiche, String> ammonizioniColumn;
    
    @FXML
    private TableColumn<InformazioniStatistiche, String> doppieAmmonizioniColumn;
    
    @FXML
    private TableColumn<InformazioniStatistiche, String> espulsioniColumn;
    
    @FXML
    private TableColumn<InformazioniStatistiche, String> minutiGiocatiColumn;
    
    @FXML
    private TableColumn<InformazioniStatistiche, String> partiteNoGoalColumn;
    
    @FXML
    private TableColumn<InformazioniStatistiche, String> retiSubiteColumn;
    
    public StatisticheStagionaliController(Calciatore calciatore) {
        super(calciatore);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Image image = new Image(calciatore.getImage());
            System.out.println(calciatore.getImage());
            imgCalciatore.setImage(image);
        } catch(Exception e){
            e.printStackTrace();
        }
        
        nomeCalciatore.setText(calciatore.getNome());
        
        btnStatisticheStagionali.setStyle(btnStatisticheStagionali.getStyle() + "-fx-border-color: orange;");
        tabellaStatistiche.getItems().clear();
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
        
        List<InformazioniStatistiche> statistiche = getInfoStatistiche(calciatore.getStatistiche());
        for(InformazioniStatistiche statistica :statistiche){
            values.add(statistica);
        }
        tabellaStatistiche.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabellaStatistiche.setItems(values);
    }

    private ArrayList<InformazioniStatistiche> getInfoStatistiche(ArrayList<Statistica> statistiche) {
        ArrayList<InformazioniStatistiche> ris = new ArrayList<>();
        
        for(Statistica statistica : statistiche){
            InformazioniStatistiche aux = new InformazioniStatistiche(statistica, calciatore.getRuoloPrincipale());
            ris.add(aux);
        }
        
        return ris;
    }
}
