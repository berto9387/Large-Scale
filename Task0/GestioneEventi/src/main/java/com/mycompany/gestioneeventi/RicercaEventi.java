/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import javafx.scene.control.*;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;

/**
 *
 * @author berto
 */
public class RicercaEventi extends GeneralGrafic{
    protected final Label label;
    protected final TextField textField;
    protected final Label label0;
    protected final Button button;
    protected final TreeTableView treeTableView;
    protected final TreeTableColumn treeTableColumn;
    protected final TreeTableColumn treeTableColumn0;
    protected final TreeTableColumn treeTableColumn1;
    protected final TreeTableColumn treeTableColumn2;
    protected final TreeTableColumn treeTableColumn3;
    protected final TreeTableColumn treeTableColumn4;

    public RicercaEventi() {

        label = new Label();
        textField = new TextField();
        label0 = new Label();
        button = new Button();
        treeTableView = new TreeTableView();
        treeTableColumn = new TreeTableColumn();
        treeTableColumn0 = new TreeTableColumn();
        treeTableColumn1 = new TreeTableColumn();
        treeTableColumn2 = new TreeTableColumn();
        treeTableColumn3 = new TreeTableColumn();
        treeTableColumn4 = new TreeTableColumn();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(518.0);
        setPrefWidth(625.0);

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(218.0);
        label.setLayoutY(42.0);
        label.setPrefHeight(39.0);
        label.setPrefWidth(189.0);
        label.setText("RICERCA EVENTI");
        label.setFont(new Font(18.0));

        textField.setLayoutX(238.0);
        textField.setLayoutY(424.0);

        label0.setLayoutX(114.0);
        label0.setLayoutY(427.0);
        label0.setPrefHeight(17.0);
        label0.setPrefWidth(104.0);
        label0.setText("Ricerca per città:");
        label0.setFont(new Font(13.0));

        button.setLayoutX(440.0);
        button.setLayoutY(445.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(7.0);
        button.setPrefWidth(118.0);
        button.setText("RICERCA");
        button.setFont(new Font(13.0));

        treeTableView.setLayoutX(82.0);
        treeTableView.setLayoutY(105.0);
        treeTableView.setPrefHeight(200.0);
        treeTableView.setPrefWidth(461.0);

        treeTableColumn.setPrefWidth(70.4000244140625);
        treeTableColumn.setText("Nome");

        treeTableColumn0.setPrefWidth(80.7999267578125);
        treeTableColumn0.setText("Città");

        treeTableColumn1.setPrefWidth(76.79997253417969);
        treeTableColumn1.setText("Data");

        treeTableColumn2.setPrefWidth(75.0);
        treeTableColumn2.setText("Ora");

        treeTableColumn3.setPrefWidth(75.0);
        treeTableColumn3.setText("Posti");

        treeTableColumn4.setPrefWidth(81.0);
        treeTableColumn4.setText("Tipologia");

        getChildren().add(label);
        getChildren().add(textField);
        getChildren().add(label0);
        getChildren().add(button);
        treeTableView.getColumns().add(treeTableColumn);
        treeTableView.getColumns().add(treeTableColumn0);
        treeTableView.getColumns().add(treeTableColumn1);
        treeTableView.getColumns().add(treeTableColumn2);
        treeTableView.getColumns().add(treeTableColumn3);
        treeTableView.getColumns().add(treeTableColumn4);
        getChildren().add(treeTableView);
    }
}
