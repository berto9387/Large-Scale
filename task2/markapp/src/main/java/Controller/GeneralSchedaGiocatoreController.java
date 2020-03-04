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
        if(((Button)event.getSource()).getId().equals("btnCronologiaTrasferimenti"))
            ScreenController.showPageCalciatore("InfoTrasferimenti", calciatore);
        else
            ScreenController.showPageCalciatore("InfoTrasferimentiInLista", calciatore);
    }

    @FXML
    void handlerCronologiaInfortuni(ActionEvent event) {
        if(((Button)event.getSource()).getId().equals("btnCronologiaInfortuni"))
            ScreenController.showPageCalciatore("InfoInfortuni", calciatore);
        else
            ScreenController.showPageCalciatore("InfoInfortuniInLista", calciatore);
    }

    @FXML
    void handlerGraficiStatistiche(ActionEvent event) {

    }

    @FXML
    void handlerInfoPrincipali(ActionEvent event) {
        if(((Button)event.getSource()).getId().equals("btnInfoPrincipali"))
            ScreenController.showPageCalciatore("InfoPrincipaliCalciatore", calciatore);
        else
        ScreenController.showPageCalciatore("InfoPrincipaliCalciatoreInLista", calciatore);
    }

    @FXML
    void handlerStatisticheStagionali(ActionEvent event) {
        if(((Button)event.getSource()).getId().equals("btnStatisticheStagionali"))
            ScreenController.showPageCalciatore("StatisticheStagionali", calciatore);
        else
            ScreenController.showPageCalciatore("StatisticheStagionaliInLista", calciatore);
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
    
    @FXML
    void handlerTornaAllaListaPreferiti(ActionEvent event) {
        ScreenController.showPage("ListaGiocatoriInteresse");
    }
    
    @FXML
    void handlerApprovaGiocatore(ActionEvent event) {
        //Codice giocatore approvato 0
        int err = GestioneListaPreferitiMongoDataAccess.valutaGiocatore(calciatore, ScreenController.getUtente(), 0);
        infoLabel.setStyle("-fx-padding: 10px; -fx-border-insets: 10px; -fx-background-insets: 10px;");
        if(err == 0){
            
            infoLabel.setText("Il giocatore è stato approvato");
           
        }
        if(err ==1){
            infoLabel.setText("Il giocatore è già stato approvato");
        }
        if(err == 2){
            infoLabel.setText("Errore durante l'operazione");
        }
    }
    
    @FXML
    void handlerRifiutaGiocatore(ActionEvent event) {
        //Codice giocatore rifiutato 1
        int err = GestioneListaPreferitiMongoDataAccess.valutaGiocatore(calciatore, ScreenController.getUtente(), 1);
        infoLabel.setStyle("-fx-padding: 10px; -fx-border-insets: 10px; -fx-background-insets: 10px;");
        if(err == 0){
            
            infoLabel.setText("Il giocatore è stato rifiutato");
           
        }
        if(err ==1){
            infoLabel.setText("Il giocatore è già stato rifiutato");
        }
        if(err == 2){
            infoLabel.setText("Errore durante l'operazione");
        }
    }
    
    @FXML
    void handlerNessunGiudizio(ActionEvent event) {
        //Codice nessun giudizio 2
        int err = GestioneListaPreferitiMongoDataAccess.valutaGiocatore(calciatore, ScreenController.getUtente(), 2);
        infoLabel.setStyle("-fx-padding: 10px; -fx-border-insets: 10px; -fx-background-insets: 10px;");
        if(err == 0){
            
            infoLabel.setText("Il giocatore non ha un giudizio");
           
        }
        if(err ==1){
            infoLabel.setText("Il giocatore è già senza giudizio");
        }
        if(err == 2){
            infoLabel.setText("Errore durante l'operazione");
        }
    }
}
