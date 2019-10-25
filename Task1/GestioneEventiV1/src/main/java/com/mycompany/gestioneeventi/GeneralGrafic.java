/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author Gianluca
 */
public class GeneralGrafic extends BorderPane{
    protected static Scene mainScene;
    protected static Group mainGroup;
    protected static Stage mainStage;
    protected static User utente;
    public static void setParameters(Scene scene,Group group,Stage stage)
    {
        mainScene=scene;
        mainGroup=group;
        mainStage=stage;
    }
}
