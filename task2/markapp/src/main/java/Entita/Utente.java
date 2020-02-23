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
public class Utente {
    
    private String _id;
    private String nome;
    private String cognome;
    private String email;
    private String ruolo;
    private Societa societa;

    public Utente() {
    }
    
    public Utente(String _id, String nome, String cognome, String email, String ruolo) {
        this._id = _id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
    }
    public Utente(String _id, String nome, String cognome, String email, String ruolo, Societa societa) {
        this._id = _id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
        this.societa=societa;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Societa getSocieta() {
        return societa;
    }

    public void setSocieta(Societa societa) {
        this.societa = societa;
    }
    
    
    @Override
    public String toString() {
        return "Utente{" + "_id=" + _id + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo + '}';
    }

 

    

    
    
    
}
