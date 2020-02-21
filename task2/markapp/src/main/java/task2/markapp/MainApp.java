package task2.markapp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application {

    Stage primaryStage;
    private BorderPane rootLayout;

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage=stage;
        primaryStage.setTitle("markApp");
        ScreenController.setMain(this);
        ScreenController.showLogin();
        
    }

    public Stage getStage()
    {
        return primaryStage;
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
