package fxml;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public abstract class SceneBase extends Pane {

    protected final Button button;
    protected final Label label;
    protected final Button button0;
    protected final Button button1;

    public SceneBase() {

        button = new Button();
        label = new Label();
        button0 = new Button();
        button1 = new Button();

        setId("data");
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(495.0);
        setPrefWidth(702.0);

        button.setLayoutX(581.0);
        button.setLayoutY(32.0);
        button.setMnemonicParsing(false);
        button.setText("Esci");

        label.setLayoutX(62.0);
        label.setLayoutY(22.0);
        label.setPrefHeight(21.0);
        label.setPrefWidth(346.0);
        label.setText("Menù Utente");
        label.setFont(new Font(35.0));

        button0.setLayoutX(328.0);
        button0.setLayoutY(232.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(31.0);
        button0.setPrefWidth(181.0);
        button0.setText("Ricerca Eventi");

        button1.setLayoutX(98.0);
        button1.setLayoutY(232.0);
        button1.setMnemonicParsing(false);
        button1.setText("Visualizza Partecipazioni");

        getChildren().add(button);
        getChildren().add(label);
        getChildren().add(button0);
        getChildren().add(button1);

    }
}
