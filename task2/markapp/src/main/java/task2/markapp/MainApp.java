package task2.markapp;

import Dao.MongoDataAccess;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    Stage primaryStage;
    private Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    private BorderPane rootLayout;

    public Rectangle2D getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Rectangle2D screenSize) {
        this.screenSize = screenSize;
    }
    
    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        MongoDataAccess.apriConnessione();
        primaryStage=stage;
        primaryStage.setTitle("markApp");
        primaryStage.initStyle(StageStyle.UNDECORATED);
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
