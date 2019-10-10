/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author Gianluca
 */
public class GeneralGrafic extends AnchorPane{
    protected static Scene mainScene;
    protected static Group mainGroup;
    protected static Stage mainStage;
    public static void setParameters(Scene scene,Group group,Stage stage)
    {
        mainScene=scene;
        mainGroup=group;
        mainStage=stage;
    }
}
