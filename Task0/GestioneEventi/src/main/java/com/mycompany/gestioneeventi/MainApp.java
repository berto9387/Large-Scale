package com.mycompany.gestioneeventi;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        
        LoginPage login = new LoginPage();
        Group root = new Group(login);
        
        Scene scene = new Scene(root,700,500);
        GeneralGrafic.setParameters(scene, root, stage);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("GestioneEventi");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
