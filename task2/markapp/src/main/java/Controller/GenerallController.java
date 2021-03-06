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
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import task2.markapp.ScreenController;

/**
 *
 * @author tony_
 */
public class GenerallController implements Initializable{
    @FXML
    private Circle btnClose;
    
    @FXML
    protected HBox bottoneCercaGiocatore;

    @FXML
    protected HBox bottoneListaPreferiti;

    @FXML
    protected HBox bottoneProfiloInteresse;

    @FXML
    protected HBox bottoneProfiloPersonale;
    
    @FXML
    protected HBox bottoneMembriSquadra;
    
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
    public  void autoResizeColumns( TableView<?> table )
    {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().skip(1).limit(6).forEach( (column) ->
        {
            
            //Minimal width = columnheader
            Text t = new Text( column.getText() );
            t.setFont(Font.font("Roboto", 17.0d));
            t.applyCss();
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    t.setFont(Font.font("Roboto", 17.0d));
                    t.applyCss();
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }
            
            //set the new max-widht with some extra space
            column.setPrefWidth( max +3d );
        } );
    }
}
