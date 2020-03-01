/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

import java.util.ArrayList;

/**
 *
 * @author tony_
 */
public class Societa {
    
    private String id;
    private String nomeSocieta;
    private String nazione;
    private String allenatore;
    private String ammDelegato;
    private String osservatore;

    public Societa() {
    }

    public Societa(String id, String nomeSocieta, String nazione, String allenatore, String ammDelegato, String osservatore) {
        this.id = id;
        this.nomeSocieta = nomeSocieta;
        this.nazione = nazione;
        this.allenatore = allenatore;
        this.ammDelegato = ammDelegato;
        this.osservatore = osservatore;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeSocieta() {
        return nomeSocieta;
    }

    public void setNomeSocieta(String nomeSocieta) {
        this.nomeSocieta = nomeSocieta;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getAllenatore() {
        return allenatore;
    }

    public void setAllenatore(String allenatore) {
        this.allenatore = allenatore;
    }

    public String getAmmDelegato() {
        return ammDelegato;
    }

    public void setAmmDelegato(String ammDelegato) {
        this.ammDelegato = ammDelegato;
    }

    public String getOsservatore() {
        return osservatore;
    }

    public void setOsservatore(String osservatore) {
        this.osservatore = osservatore;
    }

    
    
    
}
