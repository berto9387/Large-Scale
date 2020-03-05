/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import Entita.Statistica;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author berto
 */
public class GraficiStatisticheController extends GeneralSchedaGiocatoreController {

    
    ObservableList<String> stagioni=null;
    ObservableList<String> competizioni=null;
    
    
    
    @FXML
    private PieChart tortaCartellini;

    @FXML
    private PieChart tortaAssist;
    

    @FXML
    private PieChart tortaGoal;

    @FXML
    private BarChart<?, ?> istogrammaCartellini;

    @FXML
    private BarChart<?, ?> istogrammaAssist;

    @FXML
    private BarChart<?, ?> istogrammaGoal;

    @FXML
    private ChoiceBox<String> stagioneTorta;

    @FXML
    private ChoiceBox<String> stagioneIstogramma;

    @FXML
    private ChoiceBox<String> competizioneIstogramma;

    public GraficiStatisticheController(Calciatore calciatore) {
        super(calciatore);
    }

    

    @FXML
    void creaDiagrammaInstogramma(ActionEvent event) {
        infoLabel.setText("CIAO");
        return;
    }

    @FXML
    void creaDiagrammaTorta(ActionEvent event) {
        infoLabel.setText("CIAO");
        return;
    }

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);;
        HashSet<String> stagioniList = new HashSet<>();
        HashSet<String> competizioneList = new HashSet<>();
        for(Statistica aux : calciatore.getStatistiche()){
            String appoggioSta=aux.getStagione();
            String appoggioCom=aux.getCompetizione();
            stagioniList.add(appoggioSta);
            competizioneList.add(appoggioCom);
        }
        this.stagioni=FXCollections.observableArrayList(stagioniList);
        stagioneTorta.setItems(stagioni);
        stagioneIstogramma.setItems(stagioni);
        this.competizioni=FXCollections.observableArrayList(competizioneList);
        competizioneIstogramma.setItems(competizioni);
    }  
    
    
    
}
