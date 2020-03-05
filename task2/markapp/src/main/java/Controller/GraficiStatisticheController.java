/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author berto
 */
public class GraficiStatisticheController extends GeneralSchedaGiocatoreController {

    

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
    private ChoiceBox<?> stagioneTorta;

    @FXML
    private ChoiceBox<?> stagioneIstogramma;

    @FXML
    private ChoiceBox<?> competizioneIstogramma;

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
        super.initialize(location, resources);
        System.out.println(calciatore.getNome());
    }    
    
}
