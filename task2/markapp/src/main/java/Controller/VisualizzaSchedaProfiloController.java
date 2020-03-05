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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


/**
 *
 * @author Gianluca
 */
public class VisualizzaSchedaProfiloController implements Initializable {
    @FXML
    private TextField ruoloTextField;

    @FXML
    private TextField etaMinimaTextField;
    @FXML
    private TextField etaMassimaTextField;
    @FXML
    private TextArea areaDescrizioneCaratteristiche;
    
        public void inizializzaSchedaProfilo(String ruolo,int etaMinima,int etaMassima,String descrizioneCaratteristiche)
    {
        ruoloTextField.setText(ruolo);
        etaMinimaTextField.setText(Integer.toString(etaMinima));
        etaMassimaTextField.setText(Integer.toString(etaMassima));
        this.areaDescrizioneCaratteristiche.setText(descrizioneCaratteristiche);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }     
    
}
