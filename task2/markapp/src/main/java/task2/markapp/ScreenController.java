
package task2.markapp;

import Entita.Utente;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author berto
 */
public class ScreenController {
    public static MainApp mainApp;
    public static Utente utente;
    
    
    
    public static void setMain(MainApp main){
        mainApp=main;
    }
    public static void showLogin() throws IOException{
        Stage stage=mainApp.getStage();
        Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/LoginSignUp.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    } 
     public static void showHomePage() throws IOException{
        Stage stage=mainApp.getStage();
        Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/AmministratoreSistema.fxml"));
        
        
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    } 
}
