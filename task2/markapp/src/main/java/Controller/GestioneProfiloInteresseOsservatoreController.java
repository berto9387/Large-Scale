package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Controller.ModificaProfiloDiInteresse;
import Dao.ProfiloInteresseMongoDataAccess;
import Entita.Utente;
import Model.ProfiloInteresseBeans;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import task2.markapp.MainApp;
import task2.markapp.ScreenController;

/**
 * FXML Controller class
 *
 * @author Gianluca
 */
public class GestioneProfiloInteresseOsservatoreController implements Initializable {

    @FXML
    private VBox areaVisualizzaProfili;
    
    private void caricaProfiliDiInteresse()
    {
        Utente utente =ScreenController.getUtente();
        String idSocieta=utente.getSocieta().getId();
        ArrayList<ProfiloInteresseBeans> listaProfiliInteressi=ProfiloInteresseMongoDataAccess.ottieniListaProfiliInteresseBeans(idSocieta);
        for(ProfiloInteresseBeans profilo:listaProfiliInteressi){
            
            try{
                 
             AnchorPane scheda;
             URL fileUrl = MainApp.class.getResource("/fxml/VisualeSchedaProfiloInteresse.fxml");
             FXMLLoader loader = new FXMLLoader(fileUrl);
             
             if(fileUrl == null){ //Qui dentro ci entra anche se ci sono errori nel FXML da caricare
                 System.err.println("Pagina non trovata URL");
             }
             
             scheda = loader.load();
             VisualizzaSchedaProfiloController schedaProfilo = loader.getController();
             schedaProfilo.inizializzaSchedaProfilo(profilo.getRuolo(),profilo.getEtaMinima(),profilo.getEtaMassima()
                     ,profilo.getDescrizioneCaratteristiche());
             areaVisualizzaProfili.getChildren().add(scheda); 
             
         } catch(IOException e){
             e.printStackTrace();
         }
     
       /*     try{
                FXMLLoader loader =new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/fxml/ModificaSchedaProfiloInteresse.fxml"));
                AnchorPane scheda=(AnchorPane)loader.load();
                ModificaProfiloDiInteresse controllerProfilo = loader.getController();
                controllerProfilo.inizializzaSchedaModifica(idSocieta,profilo.getEtaMinima(),profilo.getEtaMassima()
                        ,profilo.getId(),profilo.getDescrizioneCaratteristiche(),areaVisualizzaProfili,scheda);
                areaVisualizzaProfili.getChildren().add(scheda);    
            } catch(Exception e){
             System.err.println(e);
             e.printStackTrace();
         }*/
        }    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        caricaProfiliDiInteresse();
    }    
    
}
