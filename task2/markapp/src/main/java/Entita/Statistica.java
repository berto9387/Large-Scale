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
public class Statistica {
    
    private String stagione;
    private String competizione;
    private String societa;
    private int presenze;
    private int puntiAPartita;
    private int goal;
    private int assit;
    private int ammonizioni;
    private int doppieAmmonizioni;
    private int espulsioni;
    private int minutiGiocati;

    public Statistica(String stagione, String competizione, String societa, int presenze, int puntiAPartita, int goal, int assit, int ammonizioni, int doppieAmmonizioni, int espulsioni, int minutiGiocati) {
        this.stagione = stagione;
        this.competizione = competizione;
        this.societa = societa;
        this.presenze = presenze;
        this.puntiAPartita = puntiAPartita;
        this.goal = goal;
        this.assit = assit;
        this.ammonizioni = ammonizioni;
        this.doppieAmmonizioni = doppieAmmonizioni;
        this.espulsioni = espulsioni;
        this.minutiGiocati = minutiGiocati;
    }

    public String getStagione() {
        return stagione;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public String getCompetizione() {
        return competizione;
    }

    public void setCompetizione(String competizione) {
        this.competizione = competizione;
    }

    public String getSocieta() {
        return societa;
    }

    public void setSocieta(String societa) {
        this.societa = societa;
    }

    public int getPresenze() {
        return presenze;
    }

    public void setPresenze(int presenze) {
        this.presenze = presenze;
    }

    public int getPuntiAPartita() {
        return puntiAPartita;
    }

    public void setPuntiAPartita(int puntiAPartita) {
        this.puntiAPartita = puntiAPartita;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getAssit() {
        return assit;
    }

    public void setAssit(int assit) {
        this.assit = assit;
    }

    public int getAmmonizioni() {
        return ammonizioni;
    }

    public void setAmmonizioni(int ammonizioni) {
        this.ammonizioni = ammonizioni;
    }

    public int getDoppieAmmonizioni() {
        return doppieAmmonizioni;
    }

    public void setDoppieAmmonizioni(int doppieAmmonizioni) {
        this.doppieAmmonizioni = doppieAmmonizioni;
    }

    public int getEspulsioni() {
        return espulsioni;
    }

    public void setEspulsioni(int espulsioni) {
        this.espulsioni = espulsioni;
    }

    public int getMinutiGiocati() {
        return minutiGiocati;
    }

    public void setMinutiGiocati(int minutiGiocati) {
        this.minutiGiocati = minutiGiocati;
    }
    
    
}
