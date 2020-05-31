/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

/**
 *
 * @author Gianluca
 */
public class Societa {
        private String id;
        private String nomeSocieta;
        private String nazione;
        private String osservatore;

    public Societa() {
    }

    public Societa(String id, String nomeSocieta, String nazione, String osservatore) {
        this.id = id;
        this.nomeSocieta = nomeSocieta;
        this.nazione = nazione;
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


    public String getOsservatore() {
        return osservatore;
    }

    public void setOsservatore(String osservatore) {
        this.osservatore = osservatore;
    }
}
