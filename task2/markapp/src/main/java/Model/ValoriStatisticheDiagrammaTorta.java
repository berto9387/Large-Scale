/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Gianluca
 */
public class ValoriStatisticheDiagrammaTorta {
    private IntegerProperty numeroReti;
    private IntegerProperty numeroAssist;
    private IntegerProperty numeroCartellini;
    private IntegerProperty numeroGoalSubiti;
    
    public ValoriStatisticheDiagrammaTorta(int numeroReti,int numeroAssist,int numeroCartellini,int numeroGoalSubiti){
       this.numeroReti = new SimpleIntegerProperty(numeroReti);
       this.numeroAssist= new SimpleIntegerProperty(numeroAssist);
       this.numeroCartellini = new SimpleIntegerProperty(numeroCartellini);
       this.numeroGoalSubiti = new SimpleIntegerProperty(numeroGoalSubiti);
    }
    
    public int getNumeroReti(){
        return numeroReti.get();
    }
    public int getNumeroAssist(){
        return numeroAssist.get();
    }
    public int getNumeroCartellini(){
        return numeroCartellini.get();
    }
    public int getNumeroGoalSubiti(){
        return numeroGoalSubiti.get();
    }
}
