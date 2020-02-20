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
    private ArrayList<ProfiloInteresse> profiliInteresse = new ArrayList<>();
    private ArrayList<InformazioniPrincipali> giocatoriPreferiti = new ArrayList<>();

    public Societa() {
    }
    
    public Societa(String id, String nomeSocieta, String nazione, ArrayList<ProfiloInteresse> profiliInteresse, ArrayList<InformazioniPrincipali> giocatoriPreferiti) {
        this.id = id;
        this.nomeSocieta = nomeSocieta;
        this.nazione = nazione;
        this.profiliInteresse = profiliInteresse;
        this.giocatoriPreferiti = giocatoriPreferiti;
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

    public ArrayList<ProfiloInteresse> getProfiliInteresse() {
        return profiliInteresse;
    }

    public void setProfiliInteresse(ArrayList<ProfiloInteresse> profiliInteresse) {
        this.profiliInteresse = profiliInteresse;
    }

    public ArrayList<InformazioniPrincipali> getGiocatoriPreferiti() {
        return giocatoriPreferiti;
    }

    public void setGiocatoriPreferiti(ArrayList<InformazioniPrincipali> giocatoriPreferiti) {
        this.giocatoriPreferiti = giocatoriPreferiti;
    }

    public void addGiocatorePreferito(InformazioniPrincipali nuovoGiocatorePreferito) {
        this.giocatoriPreferiti.add(nuovoGiocatorePreferito);
    }
    public void addProfiloInteresse(ProfiloInteresse nuovoProfiloInteresse) {
        this.profiliInteresse.add(nuovoProfiloInteresse);
    }
    
    
}
