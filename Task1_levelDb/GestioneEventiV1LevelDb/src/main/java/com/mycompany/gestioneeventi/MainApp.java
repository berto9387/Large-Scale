package com.mycompany.gestioneeventi;

import com.mycompany.hibernate.GestioneEventiManagerEM;
import java.util.TimeZone;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainApp extends Application {
    public TimeZone getTimeZone() { return TimeZone.getDefault(); } 
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getTimeZone());
        GestioneEventiManagerEM.setup();
        LoginPage login = new LoginPage();
        Group root = new Group(login);
        
        Scene scene = new Scene(root,750,700);
        GeneralGrafic.setParameters(scene, root, stage);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setFill(Color.ORANGE);
        stage.setTitle("GestioneEventi");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    GestioneEventiManagerEM.exit();
                    System.out.println("bye bye");
                    System.exit(0);
                }
            });
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
