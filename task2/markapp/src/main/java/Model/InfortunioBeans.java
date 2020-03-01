/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entita.Infortunio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tony_
 */
public class InfortunioBeans {
    private StringProperty stagione;
    private StringProperty tipoInfortunio;
    private StringProperty inizio;
    private StringProperty fine;
    private StringProperty durata;
    private StringProperty partitePerse;

    public InfortunioBeans(Infortunio infortunio) {
        this.stagione = new SimpleStringProperty(infortunio.getStagione());
        this.tipoInfortunio = new SimpleStringProperty(infortunio.getTipoInfortunio());
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.inizio = new SimpleStringProperty(df.format(infortunio.getInizioInfortunio()));
        this.fine = new SimpleStringProperty(df.format(infortunio.getFineInfortunio()));
        
        this.durata = new SimpleStringProperty(Integer.toString(infortunio.getGiorniInfortunio()));
        this.partitePerse = new SimpleStringProperty(Integer.toString(infortunio.getPartitePerse()));
        
    }

    public StringProperty stagioneProperty() {
        return stagione;
    }

    public StringProperty tipoInfortunioProperty() {
        return tipoInfortunio;
    }

    public StringProperty inizioProperty() {
        return inizio;
    }

    public StringProperty fineProperty() {
        return fine;
    }

    public StringProperty durataProperty() {
        return durata;
    }

    public StringProperty partitePerseProperty() {
        return partitePerse;
    }
    
    
}
