/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Dao.ProfiloInteresseMongoDataAccess;
import Entita.*;
import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import task2.markapp.*;
import task2.markapp.ScreenController;
/**
 * FXML Controller class
 *
 * @author Gianluca
 */
public class GestioneProfiloGiocatoreInteresseAllenatoreController implements Initializable {
    
    @FXML
    protected VBox areaModificaProfili;
    @FXML
    protected ChoiceBox sceltaRuolo;
    @FXML
    protected TextField etaMinimaTextField;
    @FXML
    protected TextField etaMassimaTextField;
    @FXML
    protected Button incrementMin;
    @FXML
    protected Button decrementMin;
    @FXML
    protected Button incrementMax;
    @FXML
    protected Button decrementMax;
    @FXML
    protected TextArea areaDescrizioneCaratteristiche;
    
    @FXML
    private void inizializzaListaProfili(){
        Utente utente =ScreenController.getUtente();
        String idSocieta=utente.getSocieta().getId();
        ArrayList<ProfiloInteresseBeans> listaProfiliInteressi=ProfiloInteresseMongoDataAccess.ottieniListaProfiliInteresseBeans(idSocieta);
        for(ProfiloInteresseBeans profilo:listaProfiliInteressi){
            try{
                FXMLLoader loader =new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/fxml/ModificaSchedaProfiloInteresse.fxml"));
                AnchorPane scheda=(AnchorPane)loader.load();
                ModificaProfiloDiInteresse controllerProfilo = loader.getController();
                controllerProfilo.inizializzaSchedaModifica(idSocieta,profilo.getEtaMinima(),profilo.getEtaMassima()
                        ,profilo.getId(),profilo.getDescrizioneCaratteristiche(),areaModificaProfili,scheda);
                areaModificaProfili.getChildren().add(scheda);    
            } catch(Exception e){
             System.err.println("Pagina non trovata");
         }
        }
    }
    @FXML
    private void aggiungiProfiloALista(ActionEvent event){
        Utente utente =ScreenController.getUtente();
        String idAllenatore = utente.getId();
        String idSocieta=utente.getSocieta().getId();
        String ruolo=(String)sceltaRuolo.getValue();
        int etaMinima =Integer.parseInt(etaMinimaTextField.getText());
        int etaMassima = Integer.parseInt(etaMassimaTextField.getText());
        String descrizioneCaratteristiche=areaDescrizioneCaratteristiche.getText();
        
        try{
            String idScheda= ProfiloInteresseMongoDataAccess.aggiungiAListaProfiliInteresse(idSocieta, idAllenatore, ruolo,
                    etaMinima, etaMassima, descrizioneCaratteristiche);
            FXMLLoader loader =new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/ModificaSchedaProfiloInteresse.fxml"));
            AnchorPane scheda=(AnchorPane)loader.load();
            ModificaProfiloDiInteresse controllerProfilo = loader.getController();
            controllerProfilo.inizializzaSchedaModifica(idSocieta,etaMinima,etaMassima,idScheda,descrizioneCaratteristiche,areaModificaProfili,scheda);
            areaModificaProfili.getChildren().add(scheda);
            
        }catch(Exception e){
             System.err.println("Pagina non trovata");
         }
        
    
    }
    @FXML
    protected void incrementaDecrementa(ActionEvent event){
        String id= ((Control)event.getSource()).getId();
        int etaMin=Integer.parseInt(etaMinimaTextField.getText());
        int etaMax =Integer.parseInt(etaMassimaTextField.getText());
        switch(id){
            case "incrementMin":
                if(etaMin<(etaMax-1)){
                    ++etaMin;
                    etaMinimaTextField.setText(Integer.toString(etaMin));
                }
            break;
            case "decrementMin":
                if(etaMin>15){
                    --etaMin;
                    etaMinimaTextField.setText(Integer.toString(etaMin));
                }
            break;
            case "incrementMax":
                if(etaMax<50){
                    ++etaMax;
                    etaMassimaTextField.setText(Integer.toString(etaMax));
                    
                }
            break;
            case "decrementMax":
                if(etaMax>(etaMin+1)){
                    --etaMax;
                    etaMassimaTextField.setText(Integer.toString(etaMin));
                }
            break;
            default:System.err.println("errore nella incrementa");
        }
    }
    
    private void eliminaSchedaDaGrafica(ModificaProfiloDiInteresse scheda){
    
        
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sceltaRuolo.setItems(ScreenController.getRuoloInCampo());
        sceltaRuolo.setValue("Portiere");
        incrementMin.setId("incrementMin");
        decrementMin.setId("decrementMin");
        incrementMax.setId("incrementMax");
        decrementMax.setId("decrementMax");
        inizializzaListaProfili();
    }   
    
}
