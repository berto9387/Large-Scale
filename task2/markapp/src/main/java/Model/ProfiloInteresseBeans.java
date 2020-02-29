/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entita.*;
import java.beans.*;
import javafx.beans.property.*;

/**
 *
 * @author Gianluca
 */
public class ProfiloInteresseBeans {
    private String idProfiloInteresse;
    private StringProperty ruolo;
    private IntegerProperty etaMinima;
    private IntegerProperty etaMassima;
    private StringProperty descrizioneCaratteristiche;
    public ProfiloInteresseBeans(String id,String ruolo,int etaMinima,
            int etaMassima,String descrizioneCaratteristiche){
        idProfiloInteresse=id;
        this.ruolo=new SimpleStringProperty(ruolo);
        this.etaMinima= new SimpleIntegerProperty(etaMinima);
        this.etaMassima=new SimpleIntegerProperty(etaMassima);
        this.descrizioneCaratteristiche=new SimpleStringProperty(descrizioneCaratteristiche);
    }
    public ProfiloInteresseBeans(ProfiloInteresse profiloInteresse){
        
        idProfiloInteresse=profiloInteresse.getId();
        this.ruolo=new SimpleStringProperty(profiloInteresse.getRuolo());
        this.etaMinima= new SimpleIntegerProperty(profiloInteresse.getEtaMinima());
        this.etaMassima=new SimpleIntegerProperty(profiloInteresse.getEtaMassima());
        this.descrizioneCaratteristiche=new SimpleStringProperty(profiloInteresse.getDescrizioneCaratteristiche());
    }
    public String getId(){
        return idProfiloInteresse;
    }
    public String getRuolo(){
        return ruolo.get();
    }
    public int getEtaMinima(){
        return etaMinima.get();
    }
    public int getEtaMassima(){
        return etaMassima.get();
    }
    public String getDescrizioneCaratteristiche(){
        return descrizioneCaratteristiche.get();
    }
    
    
}
