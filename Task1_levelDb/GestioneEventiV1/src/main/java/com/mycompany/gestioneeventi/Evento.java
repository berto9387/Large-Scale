/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.util.Date;
import javafx.beans.property.*;

/**
 *
 * @author berto
 */
public class Evento {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty Nome;
    private final SimpleStringProperty Luogo;
    private final SimpleObjectProperty<Date> Data;
    private final SimpleStringProperty Ora;
    private final SimpleIntegerProperty Posti;
    private final SimpleStringProperty Tipologia;
    private final SimpleStringProperty Descrizione;
    private final SimpleIntegerProperty Organizzatore;
    private final SimpleIntegerProperty NumeroPartecipanti;
   
    public Evento(int id,String Nome,String Luogo, Date Data,String Ora,int Posti, String Tipologia, String Descrizione,int Organizzatore,int NumeroPartecipanti){
        this.id = new SimpleIntegerProperty(id);
        this.Nome = new SimpleStringProperty(Nome);
        this.Luogo = new SimpleStringProperty(Luogo);        
        this.Data = new SimpleObjectProperty<>(Data);
        this.Ora = new SimpleStringProperty(Ora);
        this.Posti = new SimpleIntegerProperty(Posti);
        this.Tipologia = new SimpleStringProperty(Tipologia);
        this.Descrizione = new SimpleStringProperty(Descrizione);
        this.Organizzatore= new SimpleIntegerProperty(Organizzatore);
        this.NumeroPartecipanti= new SimpleIntegerProperty(NumeroPartecipanti);
    }
    
    public int getId(){
        return id.get();
    }
    public String getNome(){
        return Nome.get();
    }
    public String getLuogo(){
        return Luogo.get();
    }
    public Date getData(){
        return Data.get();
    }
    public String getOra(){
        return Ora.get();
    }
    public int getPosti(){
        return Posti.get();
    }
    public String getTipologia(){
        return Tipologia.get();
    }
    public String getDescrizione(){
        return Descrizione.get();
    }
    public int getOrganizzatore(){
        return Organizzatore.get();
    }
    public int getNumeroPartecipanti()
    {
        return NumeroPartecipanti.get();
    }
}
