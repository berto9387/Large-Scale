/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GestioneListaPreferitiMongoDataAccess;
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
    
    @FXML
    protected Label infoLabel;

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
        ScreenController.showPageCalciatore("InfoTrasferimenti", calciatore);
    }

    @FXML
    void handlerCronologiaInfortuni(ActionEvent event) {
        ScreenController.showPageCalciatore("InfoInfortuni", calciatore);
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
    
    @FXML
    void handlerAggiungiGiocatoreLista(ActionEvent event) {
        
        int err = GestioneListaPreferitiMongoDataAccess.aggiungiGiocatoreLista(calciatore, ScreenController.getUtente());

        if(err == 0){
            System.out.println("Giocatore inserito correttamente");
            infoLabel.setText("Giocatore inserito correttamente");
        }
        if(err == 1){
            System.out.println("Giocatore già presente in lista");
            infoLabel.setText("Giocatore già presente in lista");
        }
        if(err == 2){
            System.out.println("Errore durante l'aggiungi, riprovare");
            infoLabel.setText("Errore durante l'aggiungi, riprovare");
        }
    }
    
    @FXML
    void handlerTornaAllaRicerca(ActionEvent event) {
        ScreenController.showPage("RicercaCalciatori");
    }
}
