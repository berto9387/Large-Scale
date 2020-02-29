/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 *
 * @author berto
 */
public class InformazioniRicercaCalciatore {
    private StringProperty idCalciatore;
    private StringProperty nome;
    private StringProperty ruoloPrincipale;
    private StringProperty squadra;
    private StringProperty eta;
    private IntegerProperty valoreMercato;
    private Circle giudizioAllenatore;
    private Circle giudizioDirigenza;
    private Button elimina;
    private ImageView image;
    private StringProperty nazionalita;

    public InformazioniRicercaCalciatore() {
        this(new SimpleStringProperty(),new SimpleStringProperty(),new SimpleStringProperty(),
                new SimpleStringProperty(),new SimpleStringProperty(),new SimpleStringProperty());
    }

    public InformazioniRicercaCalciatore(StringProperty idCalciatore, StringProperty nome, StringProperty ruoloPrincipale, StringProperty squadra, StringProperty dataNascita,  
            StringProperty nazionalita) {
        this.idCalciatore = idCalciatore;
        this.nome = nome;
        this.ruoloPrincipale = ruoloPrincipale;
        this.squadra = squadra;
        this.eta = dataNascita;
        this.nazionalita = nazionalita;
    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day); 

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--; 
        }

        Integer ageInt = age;
        String ageS = ageInt.toString();

        return ageS;  
    }

    public String getIdCalciatore() {
        return idCalciatore.get();
    }

    public void setIdCalciatore(String idCalciatore) {
        this.idCalciatore.set(idCalciatore);
    }
    
    public StringProperty idCalciatoreProperty(){
        return idCalciatore;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty(){
        return nome;
    }
    
    public String getRuoloPrincipale() {
        return ruoloPrincipale.get();
    }

    public void setRuoloPrincipale(String ruoloPrincipale) {
        this.ruoloPrincipale.set(ruoloPrincipale);
    }
    
    public StringProperty ruoloProperty(){
        return ruoloPrincipale;
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
    
    public String getEta() {
        return eta.get();
    }

    public void setEta(LocalDateTime dataNascita) {
        String age=getAge(dataNascita.getYear(),dataNascita.getDayOfMonth(),dataNascita.getDayOfMonth());
        this.eta.set(age);
    }

    public StringProperty etaProperty(){
        return eta;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    
    
    
    
    public String getNazionalita() {
        return nazionalita.get();
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita.set(nazionalita);
    }

    public StringProperty nazionalitaProperty(){
        return nazionalita;
    }
    
    public int getValoreMercato() {
        return valoreMercato.get();
    }

    public void setValoreMercato(int valoreMercato) {
        this.valoreMercato=new SimpleIntegerProperty();        
        this.valoreMercato.set(valoreMercato);
    }

    public IntegerProperty valoreMercatoProperty(){
        return valoreMercato;
    }

    public Circle getGiudizioAllenatore() {
        return giudizioAllenatore;
    }

    public void setGiudizioAllenatore(Circle giudizioAllenatore) {
        this.giudizioAllenatore = giudizioAllenatore;
    }

    public Circle getGiudizioDirigenza() {
        return giudizioDirigenza;
    }

    public void setGiudizioDirigenza(Circle giudizioDirigenza) {
        this.giudizioDirigenza = giudizioDirigenza;
    }

    public Button getElimina() {
        return elimina;
    }

    public void setElimina(Button elimina) {
        this.elimina = elimina;
    }
    
}
