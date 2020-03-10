/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Gianluca
 */
public class ValoriStatisticheDiagrammaTorta {
    private DoubleProperty numeroReti;
    private DoubleProperty numeroAssist;
    private DoubleProperty numeroCartellini;
    private DoubleProperty numeroGoalSubiti;
    
    public ValoriStatisticheDiagrammaTorta(double numeroReti,double numeroAssist,double numeroCartellini,double numeroGoalSubiti){
       this.numeroReti = new SimpleDoubleProperty(numeroReti);
       this.numeroAssist= new SimpleDoubleProperty(numeroAssist);
       this.numeroCartellini = new SimpleDoubleProperty(numeroCartellini);
       this.numeroGoalSubiti = new SimpleDoubleProperty(numeroGoalSubiti);
    }
    
    public double getNumeroReti(){
        return numeroReti.get();
    }
    public double getNumeroAssist(){
        return numeroAssist.get();
    }
    public double getNumeroCartellini(){
        return numeroCartellini.get();
    }
    public double getNumeroGoalSubiti(){
        return numeroGoalSubiti.get();
    }
}
