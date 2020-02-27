
package task2.markapp;

import Controller.GeneralSchedaGiocatoreController;
import Controller.InfoPrincipaliCalciatoreController;
import Entita.Calciatore;
import Entita.Utente;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.IOException;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author berto
 */
public class ScreenController {
    private static MainApp mainApp;
    private static Utente utente;
    private static Pane view;
    private static ObservableList<String> ruoloInCampo=FXCollections.observableArrayList
        ("Punta centrale","Terzino destro","Mediano","Ala sinistra","NA","Seconda punta",
                "Centrocampista di destra","Terzino sinistro","Centrale","Ala destra","Trequartista",
                "Portiere","Difensore centrale","Centrocampista di sinistra");

    
    public static void setMain(MainApp main){
        mainApp=main;
    }
    public static void setUtente(Utente user){
        utente=user;
    }
    public static Utente getUtente(){
        return utente;
    }
    public static ObservableList<String> getRuoloInCampo(){
        return ruoloInCampo;
    }
    public static void showLogin() throws IOException{
        Stage stage=mainApp.getStage();
        Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/LoginSignUp.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setScene(scene);
        //stage.setMaximized(false);
        stage.centerOnScreen();
        
        stage.show();
    }
    
     public static void showHomePage(String homePageUtente) throws IOException{
        Stage stage=mainApp.getStage();
        Parent root = null;
        root = FXMLLoader.load(MainApp.class.getResource("/fxml/"+ homePageUtente +".fxml"));
        
        mainApp.setRootLayout((BorderPane) root);
        stage.setScene(new Scene(root, mainApp.getScreenSize().getWidth(), mainApp.getScreenSize().getHeight()));
        
        stage.centerOnScreen();
        stage.show();
    }
     
     public static void showPage(String fileName){
         
         try{
             //System.out.println("sono nella showPage, la stringa passatami è: " + fileName);
             URL fileUrl = MainApp.class.getResource("/fxml/"+ fileName +".fxml");
             
             if(fileUrl == null){ //Qui dentro ci entra anche se ci sono errori nel FXML da caricare
                 System.err.println("Pagina non trovata");
             }
             
             view = FXMLLoader.load(fileUrl);
             
             BorderPane root = mainApp.getRootLayout();
             BorderPane.setMargin(view, new Insets(10, 10, 10, 10));
             root.setCenter(view);
             
         } catch(IOException e){
             System.err.println("Pagina non trovata");
         }
         
     }
     
     public static void showPageCalciatore(String fileName, Calciatore calciatore){
         
         try{
             
             //System.out.println("sono nella showPage, la stringa passatami è: " + fileName);
             URL fileUrl = MainApp.class.getResource("/fxml/"+ fileName +".fxml");
             FXMLLoader loader = new FXMLLoader(fileUrl);
             
             if(fileUrl == null){ //Qui dentro ci entra anche se ci sono errori nel FXML da caricare
                 System.err.println("Pagina non trovata URL");
             }
             
             
             loader.setControllerFactory(c -> {
                return new InfoPrincipaliCalciatoreController(calciatore);
             });
             view = loader.load();
             
             BorderPane root = mainApp.getRootLayout();
             BorderPane.setMargin(view, new Insets(10, 10, 10, 10));
             root.setCenter(view);
             
         } catch(IOException e){
             e.printStackTrace();
         }
     }
     
}
