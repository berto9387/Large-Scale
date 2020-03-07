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
public class ValoriStatisticheIstogramma {
    private IntegerProperty numeroGiocatoriPerStatistica;
    private IntegerProperty valoreStatistica;
    /**
     * 
     * @param numeroGiocatoriPerStatistica
     * @param valoreStatistica 
     */
    public ValoriStatisticheIstogramma(int numeroGiocatoriPerStatistica,int valoreStatistica){
            this.numeroGiocatoriPerStatistica= new SimpleIntegerProperty(numeroGiocatoriPerStatistica);
            this.valoreStatistica = new SimpleIntegerProperty(valoreStatistica);
    }
    
    public int getnumeroGiocatoriPerStatistica(){
    
        return numeroGiocatoriPerStatistica.get();
    }
    public int getvaloreStatistica(){
        return valoreStatistica.get();
    }
    
}
