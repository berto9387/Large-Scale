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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import task2.markapp.ScreenController;

/**
 *
 * @author tony_
 */
public class GeneralSchedaGiocatoreController implements Initializable{
    
    protected Calciatore calciatore;
    
    @FXML
    protected Button btnInfoPrincipali;
    
    @FXML
    protected Button btnCronologiaInfortuni;

    @FXML
    protected Button btnGraficiStatistiche;

    @FXML
    protected Button btnCronologiaTrasferimenti;

    @FXML
    protected Button btnStatisticheStagionali;
    
    @FXML
    protected ImageView imgCalciatore;
    
    @FXML
    protected Label nomeCalciatore;

    public GeneralSchedaGiocatoreController(Calciatore calciatore) {
        this.calciatore = calciatore;
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setCalciatore(Calciatore calciatore){
        this.calciatore = calciatore;
    }
    
    @FXML
    void handlerCronologiaTrasferimenti(ActionEvent event) {
        
        
    }

    @FXML
    void handlerCronologiaInfortuni(ActionEvent event) {

    }

    @FXML
    void handlerGraficiStatistiche(ActionEvent event) {

    }

    @FXML
    void handlerInfoPrincipali(ActionEvent event) {
        ScreenController.showPageCalciatore("InfoPrincipaliCalciatore", calciatore);
    }

    @FXML
    void handlerStatisticheStagionali(ActionEvent event) {
        
        ScreenController.showPageCalciatore("StatisticheStagionali", calciatore);
        
    }
}
