/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

import java.util.Date;

/**
 *
 * @author tony_
 */
public class Trasferimento {
    
    private String stagione;
    private Date dataTrasferimento;
    private String venditore;
    private String acquirente;
    private int valoreMercato;
    private String riscatto;

    public Trasferimento(String stagione, Date dataTrasferimento, String venditore, String acquirente, int valoreMercato, String riscatto) {
        this.stagione = stagione;
        this.dataTrasferimento = dataTrasferimento;
        this.venditore = venditore;
        this.acquirente = acquirente;
        this.valoreMercato = valoreMercato;
        this.riscatto = riscatto;
    }

    public Trasferimento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public String getStagione() {
        return stagione;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public Date getDataTrasferimento() {
        return dataTrasferimento;
    }

    public void setDataTrasferimento(Date dataTrasferimento) {
        this.dataTrasferimento = dataTrasferimento;
    }

    public String getVenditore() {
        return venditore;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

    public String getAcquirente() {
        return acquirente;
    }

    public void setAcquirente(String acquirente) {
        this.acquirente = acquirente;
    }

    public int getValoreMercato() {
        return valoreMercato;
    }

    public void setValoreMercato(int valoreMercato) {
        this.valoreMercato = valoreMercato;
    }

    public String getRiscatto() {
        return riscatto;
    }

    public void setRiscatto(String riscatto) {
        this.riscatto = riscatto;
    }
    
    
}

