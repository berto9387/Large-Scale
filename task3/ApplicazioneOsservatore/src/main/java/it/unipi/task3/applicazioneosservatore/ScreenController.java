package it.unipi.task3.applicazioneosservatore;

import Entita.Utente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScreenController implements Initializable {
    private static MainApp mainApp;
    private static Utente utente;
    private static Pane view;
    private static Alert popUpAvviso;

     public static void showPage(String fileName){
         
         try{
             System.out.println("sono nella showPage, la stringa passatami è: " + fileName);
             URL fileUrl = MainApp.class.getResource("/fxml/"+ fileName +".fxml");
             
             if(fileUrl == null){ //Qui dentro ci entra anche se ci sono errori nel FXML da caricare
                 System.err.println("URL null: Pagina non trovata");
             }
             
             view = FXMLLoader.load(fileUrl);
             
             BorderPane root = mainApp.getRootLayout();
             BorderPane.setMargin(view, new Insets(10, 10, 10, 10));
             root.setCenter(view);
             
         } catch(Exception e){
             System.err.println("Pagina non trovata");
             e.printStackTrace();
         }
         
     }
    @FXML
    private Label label;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
