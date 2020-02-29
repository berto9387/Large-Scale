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
    private double puntiAPartita;
    private int goal;
    private int assit; //I portieri non hanno questo campo
    private int ammonizioni;
    private int doppieAmmonizioni;
    private int espulsioni;
    private int minutiGiocati;
    //Campi che hanno solo i portieri
    private int retiSubite;
    private int partiteNoGoal;

    
    //Costruttore per calciatori non portieri
    public Statistica(String stagione, String competizione, String societa, int presenze, int goal, int assit, int ammonizioni, int doppieAmmonizioni, int espulsioni, int minutiGiocati) {
        this.stagione = stagione;
        this.competizione = competizione;
        this.societa = societa;
        this.presenze = presenze;
        this.goal = goal;
        this.assit = assit;
        this.ammonizioni = ammonizioni;
        this.doppieAmmonizioni = doppieAmmonizioni;
        this.espulsioni = espulsioni;
        this.minutiGiocati = minutiGiocati;
    }
    
    //Construttore per portieri
    public Statistica(String stagione, String competizione, String societa, int presenze, int goal, int ammonizioni, int doppieAmmonizioni, int espulsioni, int minutiGiocati, int retiSubite, int partiteNoGoal) {
        this.stagione = stagione;
        this.competizione = competizione;
        this.societa = societa;
        this.presenze = presenze;
        this.puntiAPartita = puntiAPartita;
        this.goal = goal;
        this.ammonizioni = ammonizioni;
        this.doppieAmmonizioni = doppieAmmonizioni;
        this.espulsioni = espulsioni;
        this.minutiGiocati = minutiGiocati;
        this.retiSubite = retiSubite;
        this.partiteNoGoal = partiteNoGoal;
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

    public double getPuntiAPartita() {
        return puntiAPartita;
    }

    public void setPuntiAPartita(double puntiAPartita) {
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

    public int getRetiSubite() {
        return retiSubite;
    }

    public void setRetiSubite(int retiSubite) {
        this.retiSubite = retiSubite;
    }

    public int getPartiteNoGoal() {
        return partiteNoGoal;
    }

    public void setPartiteNoGoal(int partiteNoGoal) {
        this.partiteNoGoal = partiteNoGoal;
    }
    
    
    
}
