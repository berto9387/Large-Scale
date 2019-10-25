/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import java.sql.Date;
import javax.persistence.*;

/**
 *
 * @author berto
 */
@Entity
@Table(name="evento")
public class EventoDb {
    private long id;
    private String nome;
    private String luogo;
    private Date data;
    private String ora;
    private int posti;
    private String tipologia;
    private String descrizione;
    private int numero_partecipanti;
    
    @Column(name="id_Evento")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getId(){
        return id;
    }

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
    
}
