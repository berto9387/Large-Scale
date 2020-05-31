package Controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.RangeSlider;

public class OsservatoriSeguitiController {

    @FXML
    private Text scegliNomeTesto;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private Text scegliCognomeTesto;

    @FXML
    private JFXTextField cognomeInput;

    @FXML
    private Text errorCercaCalciatore;

    @FXML
    private TableView<?> tabellaCalciatori;

    @FXML
    private TableColumn<?, ?> fotoColumn;

    @FXML
    private TableColumn<?, ?> nomeColumn;

    @FXML
    private TableColumn<?, ?> posizioneColumn;

    @FXML
    private TableColumn<?, ?> squadraColumn;

    @FXML
    private TableColumn<?, ?> etaColumn;

    @FXML
    private TableColumn<?, ?> nazionalitaColumn;

    @FXML
    private JFXTextField inputCampionato;

    @FXML
    private JFXTextField inputStagione;

    @FXML
    private JFXTextField inputSquadra;

    @FXML
    private ChoiceBox<?> inputPosizione;

    @FXML
    private HBox containerGoal;

    @FXML
    private JFXSlider inputMediaGoalStagionali;

    @FXML
    private HBox containerAssist;

    @FXML
    private JFXSlider inputMediaAssistStagionali;

    @FXML
    private JFXSlider inputMediaCartellini;

    @FXML
    private RangeSlider inputValoreDiMercato;

    @FXML
    private RangeSlider inputAltezza;

    @FXML
    private RangeSlider inputEta;

    @FXML
    private ChoiceBox<?> inputContratto;

    @FXML
    private HBox soloPortiere;

    @FXML
    private JFXSlider inputMediaGoalSubiti;

    @FXML
    private VBox progressIndicatorContainer;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    void cercaCalciatore(ActionEvent event) {

    }

    @FXML
    void ricercaAvanzata(ActionEvent event) {

    }

}