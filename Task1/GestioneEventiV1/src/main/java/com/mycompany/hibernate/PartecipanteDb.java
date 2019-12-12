/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.hibernate;
//import java.sql.Date;
import java.util.*;
import javax.persistence.*;
/**
 *
 * @author berto
 */
@Entity(name="PartecipanteDb")
@Table(name="partecipante")
public class PartecipanteDb {
    @Column(name="id_Partecipante")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String nome;

    
    private String cognome;
    private Date data_nascita;
    @Column(name="email",unique=true)
    private String email;
    private String password;
    private String username;
    private String phone;
    
    
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
//    fetch = FetchType.EAGER)
//    @JoinTable(
//            name="partecipa",
//            joinColumns=@JoinColumn(name="id_Partecipante"),
//            inverseJoinColumns=@JoinColumn(name="id_Evento"))
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "partecipa",
        joinColumns = @JoinColumn(name = "id_Partecipante"),
        inverseJoinColumns = @JoinColumn(name = "id_Evento")
    )
    private Set<EventoDb> book;
    
    //funzioni utili
    
    public void addBook(EventoDb ev){
        book.add(ev);
        ev.getPartecipazioni().add(this);
    }
    
    public void removeBook(EventoDb ev){
        book.remove(ev);
        ev.getPartecipazioni().remove(this);
    }
    //costruttori della classe
    public PartecipanteDb(){
        //costruttore vuoto
    }

    public PartecipanteDb(long id, String nome, String cognome, Date data_nascita, String email, String password, String username, String phone, Set<EventoDb> book) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.book = book;
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
        final PartecipanteDb other = (PartecipanteDb) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    //get and setter    
    
    public long getId(){
        return id;
    }
    public void setId(Long id) {
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

    public Set<EventoDb> getBook() {
        return book;
    }

    public void setBook(Set<EventoDb> book) {
        this.book = book;
    }
    
}
