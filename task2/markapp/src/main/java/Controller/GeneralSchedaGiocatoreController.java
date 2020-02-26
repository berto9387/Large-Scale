/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author tony_
 */
public class GeneralSchedaGiocatoreController implements Initializable{
    
    protected Calciatore calciatore;

    public GeneralSchedaGiocatoreController(Calciatore calciatore) {
        this.calciatore = calciatore;
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    public void setCalciatore(Calciatore calciatore){
        this.calciatore = calciatore;
    }
}
