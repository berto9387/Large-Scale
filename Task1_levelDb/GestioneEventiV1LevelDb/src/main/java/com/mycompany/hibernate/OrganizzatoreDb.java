/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

//import java.sql.Date;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author berto
 */
@Entity(name="OrganizzatoreDb")
@Table(name="organizzatore")
public class OrganizzatoreDb {
    @Column(name="id_Organizzatore")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String cognome;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_nascita")
    private Date data_nascita;
    @Column(name="email",unique=true)
    private String email;
    private String password;
    private String username;
    private String phone;
    @OneToMany(
        mappedBy = "organizzatore",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private Set<EventoDb> eventiCreati=new HashSet<>();
    
    //funzioni utili per la classe
    public void addEvento(EventoDb ev){
        eventiCreati.add(ev);
        ev.setOrganizzatore(this);
    }
    public void removeEvento(EventoDb ev){
        eventiCreati.remove(ev);
        ev.setOrganizzatore(null);
    }
    //costruttori della classe
    public OrganizzatoreDb(){
        //costruttore vuoto
    }
    public OrganizzatoreDb(long id, String nome, String cognome, Date data_nascita, String email, String password, String username, String phone) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
    }

    //hash and equals
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final OrganizzatoreDb other = (OrganizzatoreDb) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
   
    //get and setter    

    public long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setId(long id) {
        this.id = id;
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

    public Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<EventoDb> getEventiCreati() {
        return eventiCreati;
    }

    public void setEventiCreati(Set<EventoDb> eventiCreati) {
        this.eventiCreati = eventiCreati;
    }

    
}
