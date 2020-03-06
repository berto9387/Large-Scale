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

import javafx.fxml.*;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import task2.markapp.ScreenController;

/**
 *
 * @author Gianluca
 */
public class ModificaProfiloDiInteresse extends GestioneProfiloGiocatoreInteresseAllenatoreController{
    @FXML
    private Label idScheda;
    private AnchorPane schedaFXML;
    private String valoreRuolo;
    
    public void inizializzaSchedaModifica(String ruolo,int etaMinima,int etaMassima,
            String idScheda,String descrizioneCaratteristiche,
            VBox areaModificaProfili,AnchorPane schedaFXML)
    {
        sceltaRuolo.setItems(ScreenController.getRuoloInCampo());
        valoreRuolo=ruolo;
        
        sceltaRuolo.setValue(valoreRuolo);
        etaMinimaTextField.setText(Integer.toString(etaMinima));
        etaMassimaTextField.setText(Integer.toString(etaMassima));
        this.idScheda.setText(idScheda);
        this.areaModificaProfili=areaModificaProfili;
        this.schedaFXML=schedaFXML;
        this.areaDescrizioneCaratteristiche.setText(descrizioneCaratteristiche);
    }
    @FXML
    private void aggiornaScheda(MouseEvent event){
        
          
        Utente utente= ScreenController.getUtente();
        String idSocieta=utente.getSocieta().getId();
        String idProfiloInteresse=idScheda.getText();
        
        String ruolo = (String)sceltaRuolo.getValue();
        int etaMinima= Integer.parseInt(etaMinimaTextField.getText());
        int etaMassima = Integer.parseInt(etaMassimaTextField.getText());
        String descrizioneCaratteristiche= areaDescrizioneCaratteristiche.getText();
        ProfiloInteresseMongoDataAccess.modificaListaProfiliInteresse(idSocieta,idProfiloInteresse,
                ruolo,etaMinima,etaMassima,descrizioneCaratteristiche);
        ScreenController.mostraPopUp("Profilo aggiornato con successo");
    }
    @FXML
    private ModificaProfiloDiInteresse eliminaScheda(MouseEvent event){
        Utente utente= ScreenController.getUtente();
        String idSocieta=utente.getSocieta().getId();
        String idProfiloInteresse=idScheda.getText();
        ProfiloInteresseMongoDataAccess.eliminaProfiloInteresse(idSocieta, idProfiloInteresse);
        areaModificaProfili.getChildren().remove(schedaFXML);
        return this;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        incrementMin.setId("incrementMin");
        decrementMin.setId("decrementMin");
        incrementMax.setId("incrementMax");
        decrementMax.setId("decrementMax");
        
    } 
}
