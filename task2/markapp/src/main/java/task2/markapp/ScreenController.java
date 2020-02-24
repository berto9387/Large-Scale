
package task2.markapp;

import Entita.Utente;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author berto
 */
public class ScreenController {
    private static MainApp mainApp;
    private static Utente utente;
    private static Pane view;
    
    
    public static void setMain(MainApp main){
        mainApp=main;
    }
    public static void setUtente(Utente user){
        utente=user;
    }
    public static Utente getUtente(){
        return utente;
    }
    public static void showLogin() throws IOException{
        Stage stage=mainApp.getStage();
        Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/LoginSignUp.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        
        stage.show();
    }
    
     public static void showHomePage(String homePageUtente) throws IOException{
        Stage stage=mainApp.getStage();
        Parent root = null;
        root = FXMLLoader.load(MainApp.class.getResource("/fxml/"+ homePageUtente +".fxml"));
        
        mainApp.setRootLayout((BorderPane) root);
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }
     
     public static void showPage(String fileName){
         
         try{
             //System.out.println("sono nella showPage, la stringa passatami è: " + fileName);
             URL fileUrl = MainApp.class.getResource("/fxml/"+ fileName+".fxml");
             if(fileUrl == null){
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
     
}
