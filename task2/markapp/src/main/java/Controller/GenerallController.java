/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import Dao.*;
import java.io.IOException;
import task2.markapp.ScreenController;

/**
 *
 * @author tony_
 */
public class GenerallController implements Initializable{
    @FXML
    private Circle btnClose;
    
    //@FXML
    //protected BorderPane mainPane;
    
    @FXML
    private void handleClose(MouseEvent event) {
        MongoDataAccess.chiudiConnessione();
        System.exit(0);
    }
    
    @FXML
    private void handlerLogout(MouseEvent event) throws IOException{
        
        ScreenController.setUtente(null);
        
        ScreenController.showLogin();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
