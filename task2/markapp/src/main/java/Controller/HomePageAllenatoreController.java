/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import task2.markapp.ScreenController;

/**
 *
 * @author berto
 * usa ProfiloInteresseMongoDataAccess
 * 
 */
public class HomePageAllenatoreController extends GenerallController{
    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView imgAdmin;

    @FXML
    private Label nomeAllenatore;

    @FXML
    private Label emailAllenatore;
    
    @FXML   
    void handlerVisualizzaListaPreferiti(MouseEvent event){
        ScreenController.showPage("ListaGiocatoriInteresse");
    } 
    
    @FXML 
    void handlerAggiungiProfiloDiInteresse(MouseEvent event){
        
    }
    
    @FXML
    void handlerRicercaGiocatori(MouseEvent event) {
        ScreenController.showPage("RicercaCalciatori");
    }
    @FXML
    void handleModificaProfiloPersonale(MouseEvent event) {
        ScreenController.showPage("ModificaProfiloPersonale");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utente utente = ScreenController.getUtente();
        nomeAllenatore.setText(utente.getNome() + " " + utente.getCognome());
        emailAllenatore.setText(utente.getEmail());
    }
}
