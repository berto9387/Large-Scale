/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author berto
 */
@Entity(name="EventoDb")
@Table(name="evento")
public class EventoDb {
    @Column(name="id_Evento")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String luogo;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data")
    private Date data;
    private String ora;
    private int posti;
    private String tipologia;
    private String descrizione;
    private int numero_partecipanti;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Organizzatore")
    private OrganizzatoreDb organizzatore;
    @ManyToMany(mappedBy="book")
    private Set<PartecipanteDb> partecipazioni;
   
    //Costruttori della classe
    public EventoDb(){
        //costruttore vuoto
    }

    public EventoDb(long id, String nome, String luogo, Date data, String ora, int posti, String tipologia, String descrizione, int numero_partecipanti, OrganizzatoreDb organizzatore, Set<PartecipanteDb> partecipazioni) {
        
        this.id = id;
        this.nome = nome;
        this.luogo = luogo;
        this.data = data;
        this.ora = ora;
        this.posti = posti;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.numero_partecipanti = numero_partecipanti;
        this.organizzatore = organizzatore;
        this.partecipazioni = partecipazioni;
    }
    
    //hash and equals
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventoDb other = (EventoDb) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    //get and setter
    public long getId(){
        return id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getNumero_partecipanti() {
        return numero_partecipanti;
    }

    public void setNumero_partecipanti(int numero_partecipanti) {
        this.numero_partecipanti = numero_partecipanti;
    }

    public Set<PartecipanteDb> getPartecipazioni() {
        return partecipazioni;
    }

    public void setPartecipazioni(Set<PartecipanteDb> partecipazioni) {
        this.partecipazioni = partecipazioni;
    }

    public OrganizzatoreDb getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(OrganizzatoreDb organizzatore) {
        this.organizzatore = organizzatore;
    }
    
}
