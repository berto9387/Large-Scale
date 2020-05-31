/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author berto
 */
public class InformazioniOsservatore {
    private Long idOsservatore;
    private StringProperty nome;
    private StringProperty cognome;
    private StringProperty squadra;
    private StringProperty numeroCalciatoriSeguiti;
    private Boolean seguito;
    
    public InformazioniOsservatore() {
        this(new SimpleStringProperty(),new SimpleStringProperty(),
                new SimpleStringProperty(),new SimpleStringProperty());
    }

    public InformazioniOsservatore(StringProperty nome, StringProperty cognome, StringProperty squadra, StringProperty calciatori) {
        
        this.nome = nome;
        this.cognome = cognome;
        this.squadra = squadra;
        this.numeroCalciatoriSeguiti = calciatori;
    }
    
    

    public Long getIdOsservatore() {
        return idOsservatore;
    }

    public void setIdOsservatore(Long idOsservatore) {
        this.idOsservatore=idOsservatore;
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
    
    public String getCognome() {
        return cognome.get();
    }

    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    public StringProperty cognomeProperty(){
        return cognome;
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
    
    public String getCalciatoriSeguiti() {
        return numeroCalciatoriSeguiti.get();
    }

    public void setCalciatoriSeguiti(String numero) {
        this.numeroCalciatoriSeguiti.set(numero);
    }

    public StringProperty numeroCalciatoriProperty(){
        return numeroCalciatoriSeguiti;
    }

    public Boolean getSeguito() {
        return seguito;
    }

    public void setSeguito(Boolean seguito) {
        this.seguito = seguito;
    }
    
    
    
}
