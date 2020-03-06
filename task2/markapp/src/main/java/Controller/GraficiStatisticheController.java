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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
    private VBox torta1;

    @FXML
    private VBox torta2;

    @FXML
    private VBox torta3;

    @FXML
    private VBox istogramma1;

    @FXML
    private VBox istogramma2;

    @FXML
    private VBox istogramma3;

    @FXML
    private ChoiceBox<String> stagioneTorta;

    @FXML
    private ChoiceBox<String> stagioneIstogramma;

    @FXML
    private ChoiceBox<String> competizioneIstogramma;

    public GraficiStatisticheController(Calciatore calciatore) {
        super(calciatore);
    }

    private void aggiungiTorta(VBox torta, String title){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(title);
        torta.getChildren().add(chart);
    }

    @FXML
    void creaDiagrammaInstogramma(ActionEvent event) {
        aggiungiTorta(torta1, "va caca");
        aggiungiTorta(torta2, "vacaca");
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
