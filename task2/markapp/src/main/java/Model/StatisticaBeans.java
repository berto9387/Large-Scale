/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entita.Statistica;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tony_
 */
public class StatisticaBeans {
    private StringProperty stagione;
    private StringProperty competizione;
    private StringProperty squadra;
    private StringProperty presenze;
    private StringProperty puntiPartita;
    private StringProperty goal;
    private StringProperty assist;
    private StringProperty ammonizioni;
    private StringProperty doppieAmmonizioni;
    private StringProperty espulsioni;
    private StringProperty minutiGiocati;
    private StringProperty retiSubite;
    private StringProperty partiteNoGoal;

    public StatisticaBeans() {
        this(new SimpleStringProperty(),new SimpleStringProperty(),new SimpleStringProperty(),
                new SimpleStringProperty(), new SimpleStringProperty(),new SimpleStringProperty(),
                new SimpleStringProperty(), new SimpleStringProperty(), new SimpleStringProperty(),
                new SimpleStringProperty(), new SimpleStringProperty(), new SimpleStringProperty(),
                new SimpleStringProperty());
    }
    
    public StatisticaBeans(Statistica statistica, String ruolo){
        this.stagione = new SimpleStringProperty(statistica.getStagione());
        this.competizione = new SimpleStringProperty(statistica.getCompetizione());
        this.squadra = new SimpleStringProperty(statistica.getSocieta());
        this.presenze = new SimpleStringProperty(Integer.toString(statistica.getPresenze()));
        this.puntiPartita = new SimpleStringProperty(Double.toString(statistica.getPuntiAPartita()));
        this.goal = new SimpleStringProperty(Integer.toString(statistica.getGoal()));
        
        this.ammonizioni = new SimpleStringProperty(Integer.toString(statistica.getAmmonizioni()));
        this.doppieAmmonizioni = new SimpleStringProperty(Integer.toString(statistica.getDoppieAmmonizioni()));
        this.espulsioni = new SimpleStringProperty(Integer.toString(statistica.getEspulsioni()));
        this.minutiGiocati = new SimpleStringProperty(Integer.toString(statistica.getMinutiGiocati()));
        this.retiSubite = new SimpleStringProperty(Integer.toString(statistica.getRetiSubite()));
        this.partiteNoGoal = new SimpleStringProperty(Integer.toString(statistica.getPartiteNoGoal()));
        
        if(ruolo.equals("Portiere")){
            this.retiSubite = new SimpleStringProperty(Integer.toString(statistica.getRetiSubite()));
            this.partiteNoGoal = new SimpleStringProperty(Integer.toString(statistica.getPartiteNoGoal()));
        }else{
            this.assist = new SimpleStringProperty(Integer.toString(statistica.getAssit()));
        }
    }

    public StatisticaBeans(StringProperty stagione, StringProperty competizione, StringProperty squadra, StringProperty presenze, StringProperty puntiPartita, StringProperty goal, StringProperty assist, StringProperty ammonizioni, StringProperty doppieAmmonizioni, StringProperty espulsioni, StringProperty minutiGiocati, StringProperty retiSubite, StringProperty partiteNoGoal) {
        this.stagione = stagione;
        this.competizione = competizione;
        this.squadra = squadra;
        this.presenze = presenze;
        this.puntiPartita = puntiPartita;
        this.goal = goal;
        this.assist = assist;
        this.ammonizioni = ammonizioni;
        this.doppieAmmonizioni = doppieAmmonizioni;
        this.espulsioni = espulsioni;
        this.minutiGiocati = minutiGiocati;
        this.retiSubite = retiSubite;
        this.partiteNoGoal = partiteNoGoal;
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

    public String getCompetizione() {
        return competizione.get();
    }

    public void setCompetizione(String competizione) {
        this.competizione.set(competizione);
    }
    
    public StringProperty competizioneProperty(){
        return competizione;
    }

    public String getSquadra() {
        return squadra.get();
    }

    public void setSquadra(String squadra) {
        this.squadra.set(squadra);
    }
    
    public StringProperty squadraProperty(){
        return squadra;
    }

    public String getPresenze() {
        return presenze.get();
    }

    public void setPresenze(int presenze) {
        this.presenze.set(Integer.toString(presenze));
    }
    
    public StringProperty presenzeProperty(){
        return presenze;
    }

    public String getPuntiPartita() {
        return puntiPartita.get();
    }

    public void setPuntiPartita(double puntiPartita) {
        this.puntiPartita.set(Double.toString(puntiPartita));
    }
    
    public StringProperty puntiPartitaProperty(){
        return puntiPartita;
    }

    public String getGoal() {
        return goal.get();
    }

    public void setGoal(int goal) {
        this.goal.set(Integer.toString(goal));
    }
    
    public StringProperty goalProperty(){
        return goal;
    }

    public String getAssist() {
        return assist.get();
    }

    public void setAssist(int assist) {
        this.assist.set(Integer.toString(assist));
    }
    
    public StringProperty assistProperty(){
        return assist;
    }

    public String getAmmonizioni() {
        return ammonizioni.get();
    }

    public void setAmmonizioni(int ammonizioni) {
        this.ammonizioni.set(Integer.toString(ammonizioni));
    }
    
    public StringProperty ammonizioniProperty(){
        return ammonizioni;
    }

    public String getDoppieAmmonizioni() {
        return doppieAmmonizioni.get();
    }

    public void setDoppieAmmonizioni(int doppieAmmonizioni) {
        this.doppieAmmonizioni.set(Integer.toString(doppieAmmonizioni));
    }
    
    public StringProperty doppieAmmonizioniProperty(){
        return doppieAmmonizioni;
    }

    public String getEspulsioni() {
        return espulsioni.get();
    }

    public void setEspulsioni(int espulsioni) {
        this.espulsioni.set(Integer.toString(espulsioni));
    }
    
    public StringProperty espulsioniProperty(){
        return espulsioni;
    }

    public String getMinutiGiocati() {
        return minutiGiocati.get();
    }

    public void setMinutiGiocati(int minutiGiocati) {
        this.minutiGiocati.set(Integer.toString(minutiGiocati));
    }
    
    public StringProperty minutiGiocatiProperty(){
        return minutiGiocati;
    }

    public String getRetiSubite() {
        return retiSubite.get();
    }

    public void setRetiSubite(int retiSubite) {
        this.retiSubite.set(Integer.toString(retiSubite));
    }
    
    public StringProperty retiSubiteProperty(){
        return retiSubite;
    }

    public String getPartiteNoGoal() {
        return partiteNoGoal.get();
    }

    public void setPartiteNoGoal(int partiteNoGoal) {
        this.partiteNoGoal.set(Integer.toString(partiteNoGoal));
    }
    
    public StringProperty partiteNoGoalProperty(){
        return partiteNoGoal;
    }
    
    
}
