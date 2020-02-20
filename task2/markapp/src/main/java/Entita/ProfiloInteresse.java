/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

/**
 *
 * @author tony_
 */
public class ProfiloInteresse {
    private String _id;
    private String ruolo;
    private int etaMinima;
    private int etaMassima;
    private String descrizioneCaratteristiche;

    public ProfiloInteresse(String _id, String ruolo, int etaMinima, int etaMassima, String descrizioneCaratteristiche) {
        this._id = _id;
        this.ruolo = ruolo;
        this.etaMinima = etaMinima;
        this.etaMassima = etaMassima;
        this.descrizioneCaratteristiche = descrizioneCaratteristiche;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public int getEtaMinima() {
        return etaMinima;
    }

    public void setEtaMinima(int etaMinima) {
        this.etaMinima = etaMinima;
    }

    public int getEtaMassima() {
        return etaMassima;
    }

    public void setEtaMassima(int etaMassima) {
        this.etaMassima = etaMassima;
    }

    public String getDescrizioneCaratteristiche() {
        return descrizioneCaratteristiche;
    }

    public void setDescrizioneCaratteristiche(String descrizioneCaratteristiche) {
        this.descrizioneCaratteristiche = descrizioneCaratteristiche;
    }

   
    
    
}
