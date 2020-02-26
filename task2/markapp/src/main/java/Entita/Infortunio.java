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
public class Infortunio {
    
    private String stagione;
    private String tipoInfortunio;
    private Date inizioInfortunio;
    private Date fineInfortunio;
    private int giorniInfortunio;
    private int partitePerse;

    public Infortunio(String stagione, String tipoInfortunio, Date inizioInfortunio, Date fineInfortunio, int giorniInfortunio, int partitePerse) {
        this.stagione = stagione;
        this.tipoInfortunio = tipoInfortunio;
        this.inizioInfortunio = inizioInfortunio;
        this.fineInfortunio = fineInfortunio;
        this.giorniInfortunio = giorniInfortunio;
        this.partitePerse = partitePerse;
    }

    public Infortunio() {
    }

    public String getStagione() {
        return stagione;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public String getTipoInfortunio() {
        return tipoInfortunio;
    }

    public void setTipoInfortunio(String tipoInfortunio) {
        this.tipoInfortunio = tipoInfortunio;
    }

    public Date getInizioInfortunio() {
        return inizioInfortunio;
    }

    public void setInizioInfortunio(Date inizioInfortunio) {
        this.inizioInfortunio = inizioInfortunio;
    }

    public Date getFineInfortunio() {
        return fineInfortunio;
    }

    public void setFineInfortunio(Date fineInfortunio) {
        this.fineInfortunio = fineInfortunio;
    }

    public int getGiorniInfortunio() {
        return giorniInfortunio;
    }

    public void setGiorniInfortunio(int giorniInfortunio) {
        this.giorniInfortunio = giorniInfortunio;
    }

    public int getPartitePerse() {
        return partitePerse;
    }

    public void setPartitePerse(int partitePerse) {
        this.partitePerse = partitePerse;
    }
    
    
    
}
