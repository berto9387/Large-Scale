
package task2.markapp;

import Controller.GeneralSchedaGiocatoreController;
import Controller.GraficiStatisticheController;
import Controller.InfoInfortuniController;
import Controller.InfoPrincipaliCalciatoreController;
import Controller.InfoTrasferimentiController;
import Controller.SchedaOsservatoreController;
import Controller.StatisticheStagionaliController;
import Entita.Calciatore;
import Entita.Utente;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.IOException;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private static Alert popUpAvviso;
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
     
     public static void showPageCalciatore(String fileName, Calciatore calciatore){
         
         try{
             
             System.out.println("sono nella showPage, la stringa passatami è: " + fileName);
             
             URL fileUrl = MainApp.class.getResource("/fxml/"+ fileName +".fxml");
             FXMLLoader loader = new FXMLLoader(fileUrl);
             
             if(fileUrl == null){ //Qui dentro ci entra anche se ci sono errori nel FXML da caricare
                 System.err.println("Pagina non trovata URL");
             }
             
             switch(fileName){
                 case "InfoPrincipaliCalciatore":
                 case "InfoPrincipaliCalciatoreInLista":
                    loader.setControllerFactory(c -> {
                       return new InfoPrincipaliCalciatoreController(calciatore);
                    });
                    break;
                 case "StatisticheStagionali":
                     case "StatisticheStagionaliInLista":
                     loader.setControllerFactory(c -> {
                       return new StatisticheStagionaliController(calciatore);
                    });
                    break;
                 case "InfoTrasferimenti":
                 case "InfoTrasferimentiInLista":
                     loader.setControllerFactory(c -> {
                       return new InfoTrasferimentiController(calciatore);
                    });
                    break;
                 case "InfoInfortuni":
                 case "InfoInfortuniInLista":
                     loader.setControllerFactory(c -> {
                       return new InfoInfortuniController(calciatore);
                    });
                    break;
                case "GraficiStatistiche":
                case "GraficiStatisticheInLista":
                    loader.setControllerFactory(c -> {
                       return new GraficiStatisticheController(calciatore);
                    });
                    break;
                case "SchedaOsservatore":
                     loader.setControllerFactory(c -> {
                       return new SchedaOsservatoreController(calciatore);
                    });
                    break;
            }
             view = loader.load();
             
             BorderPane root = mainApp.getRootLayout();
             BorderPane.setMargin(view, new Insets(10, 10, 10, 10));
             root.setCenter(view);
             
         } catch(IOException e){
             e.printStackTrace();
         }
     }
     
     /**
      * Funzione che permette di mostrare un popUp d'avviso
      * @param testoPopUp 
      */
     public static void mostraPopUp(String testoPopUp)
     {
         popUpAvviso= new Alert(AlertType.INFORMATION);
         popUpAvviso.setHeaderText(null);
         popUpAvviso.setContentText(testoPopUp);
         popUpAvviso.showAndWait();
         
     }
    /**
     * Funzione di utilità per controllare se un elemento è presente sulla scena attuale
     * @param node
     * @return true se node è presente sulla scena, false altrimenti
     */
    public static boolean existObject(Node node) {
        Parent root = (Parent) mainApp.getRootLayout().getCenter();
        for(Node ciao : root.getChildrenUnmodifiable()){
            System.out.println(ciao.getId());
        }
//        System.out.println("LO CONTIENEEE ??? " + root.getChildren().contains(node));
//        return root.getChildren().contains(node);
        Node rootOfNode = node;
        while (rootOfNode.getParent() != null) {
            rootOfNode = rootOfNode.getParent();
        }
        System.out.println(root == rootOfNode);
        return root == rootOfNode;
    }
     
}
