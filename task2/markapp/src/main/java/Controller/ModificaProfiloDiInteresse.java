/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.*;
import Entita.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import task2.markapp.ScreenController;

/**
 *
 * @author Gianluca
 */
public class ModificaProfiloDiInteresse extends GestioneProfiloGiocatoreInteresseAllenatoreController{
    @FXML
    private Label idScheda;
    private GestioneProfiloGiocatoreInteresseAllenatoreController container;
    public void inizializzaSchedaModifica(String ruolo,int etaMinima,int etaMassima,
            String idScheda,String descrizioneCaratteristiche,
            GestioneProfiloGiocatoreInteresseAllenatoreController container)
    {
        sceltaRuolo.setValue(ruolo);
        etaMinimaTextField.setText(Integer.toString(etaMinima));
        etaMassimaTextField.setText(Integer.toString(etaMassima));
        this.idScheda.setText(idScheda);
        
    }
    @FXML
    private void aggiornaScheda(ActionEvent event){
          
        Utente utente= ScreenController.getUtente();
        String idSocieta=utente.getSocieta().getId();
        String idProfiloInteresse=idScheda.getText();
        String ruolo = (String)sceltaRuolo.getValue();
        int etaMinima= Integer.parseInt(etaMinimaTextField.getText());
        int etaMassima = Integer.parseInt(etaMassimaTextField.getText());
        String descrizioneCaratteristiche= areaDescrizioneCaratteristiche.getText();
        ProfiloInteresseMongoDataAccess.modificaListaProfiliInteresse(idSocieta,idProfiloInteresse,
                ruolo,etaMinima,etaMassima,descrizioneCaratteristiche); 
    }
    @FXML
    private ModificaProfiloDiInteresse eliminaScheda(ActionEvent event){
        Utente utente= ScreenController.getUtente();
        String idSocieta=utente.getSocieta().getId();
        String idProfiloInteresse=idScheda.getText();
        ProfiloInteresseMongoDataAccess.eliminaProfiloInteresse(idSocieta, idProfiloInteresse);
        return this;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sceltaRuolo.setItems(ScreenController.getRuoloInCampo());
        incrementMin.setId("incrementMin");
        decrementMin.setId("decrementMin");
        incrementMax.setId("incrementMax");
        decrementMax.setId("decrementMax");
        
    } 
}
