/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entita.Trasferimento;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tony_
 */
public class TrasferimentiBeans {
    private StringProperty stagione;
    private StringProperty dataTrasferimento;
    private StringProperty venditore;
    private StringProperty acquirente;
    private StringProperty valoreMercato;
    private StringProperty riscatto;

    public TrasferimentiBeans(Trasferimento trasferimento) {
        this.stagione = new SimpleStringProperty(trasferimento.getStagione());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.dataTrasferimento = new SimpleStringProperty(df.format(trasferimento.getDataTrasferimento()));
        this.venditore = new SimpleStringProperty(trasferimento.getVenditore());
        this.acquirente = new SimpleStringProperty(trasferimento.getAcquirente());
        this.valoreMercato = new SimpleStringProperty(String.format("%,d", trasferimento.getValoreMercato()));
        this.riscatto = new SimpleStringProperty(trasferimento.getRiscatto());
    }

    public String getStagione() {
        return stagione.get();
    }

    public void setStagione(String stagione) {
        this.stagione.set(stagione);
    }
    
    public StringProperty stagioneProperty(){
        return stagione;
    }

    public String getData() {
        return dataTrasferimento.get();
    }

    public void setData(Date data) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.dataTrasferimento.set(df.format(data));
    }
    
    public StringProperty dataTrasferimentoProperty(){
        return dataTrasferimento;
    }

    public String getVenditore() {
        return venditore.get();
    }

    public void setVenditore(String venditore) {
        this.venditore.set(venditore);
    }
    
    public StringProperty venditoreProperty(){
        return venditore;
    }

    public String getAcquirente() {
        return acquirente.get();
    }

    public void setAcquirente(String acquirente) {
        this.acquirente.set(acquirente);
    }
    
    public StringProperty acquirenteProperty(){
        return acquirente;
    }

    public String getValoreMercato() {
        return valoreMercato.get();
    }

    public void setValoreDiMercato(int valoreMercato) {
        this.valoreMercato.set(Integer.toString(valoreMercato));
    }
    
    public StringProperty valoreMercatoProperty(){
        return valoreMercato;
    }

    public String getRiscatto() {
        return riscatto.get();
    }

    public void setRiscatto(String riscatto) {
        this.riscatto.set(riscatto);
    }
    
    public StringProperty riscattoProperty(){
        return riscatto;
    }
    
    
}
