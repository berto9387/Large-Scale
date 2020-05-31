/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.Neo4jDataAccess;
import it.unipi.task3.applicazioneosservatore.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 *
 * @author Gianluca
 */
public class GeneralController implements Initializable {
    @FXML
    private Circle btnClose;
    
    @FXML
    protected HBox bottoneCercaGiocatore;

    @FXML
    protected HBox bottoneCalciatoriSeguiti;

    @FXML
    protected HBox bottoneOsservatoriSeguiti;

    @FXML
    protected HBox bottoneProfiloPersonale;
    
    @FXML
    private void handleClose(MouseEvent event) {

        try {
            Neo4jDataAccess.close();
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(GeneralController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
