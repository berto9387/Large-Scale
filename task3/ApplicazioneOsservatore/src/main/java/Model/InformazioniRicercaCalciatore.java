/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.util.Calendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

/**
 *
 * @author tony_
 */
public class InformazioniRicercaCalciatore extends InformazioniRicercaCalciatoreSeguito{
    
    private IntegerProperty seguitoDa;

    public int getSeguitoDa() {
        return seguitoDa.get();
    }

    public void setSeguitoDa(int seguitoDa) {
        this.seguitoDa=new SimpleIntegerProperty();        
        this.seguitoDa.set(seguitoDa);
    }

    public IntegerProperty seguitoDaProperty(){
        return seguitoDa;
    }
}
