/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import javafx.scene.*;
import javafx.stage.*;

/**
 *
 * @author Gianluca
 */
public class GraficLoader {
    public static void Loader(Node pageToDiscard,Node pageToLoad,Group rootGroup)
    {
        rootGroup.getChildren().remove(pageToDiscard);
        rootGroup.getChildren().add(pageToLoad);
        
        
        
    }
}
