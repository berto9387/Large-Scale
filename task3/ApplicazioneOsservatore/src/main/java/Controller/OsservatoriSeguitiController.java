package Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OsservatoriSeguitiController extends GeneralController{

    @FXML
    private Text scegliNomeTesto;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private Text scegliSquadraTesto;

    @FXML
    private JFXTextField SquadraInput;

    @FXML
    private Text errorCercaOsservatore;

    @FXML
    private TableView<?> tabellaCalciatori;

    @FXML
    private TableColumn<?, ?> nomeColumn;

    @FXML
    private TableColumn<?, ?> cognomeColumn;

    @FXML
    private TableColumn<?, ?> squadraColumn;

    @FXML
    private TableColumn<?, ?> calciatoriColumn;

    @FXML
    private TableColumn<?, ?> eliminaColumn;

    @FXML
    private Text errorCercaOsservatoreSeguiti;

    @FXML
    private VBox progressIndicatorContainer;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    void cercaOsservatore(ActionEvent event) {

    }
    
    @FXML
    void cercaOsservatoreSeguito(ActionEvent event) {

    }
}
