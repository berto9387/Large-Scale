/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.util.Date;

/**
 *
 * @author tony_
 */
public class User {
    boolean organizzatore;
    String nome;
    String cognome;
    Date data_nascita;
    String email;
    String password;
    String username;
    String telefono;
    protected User(){
    
    }
    protected User(String nome,String cognome,Date data_nascita,String email, String username, String telefono,String password,boolean organizzatore){
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.email = email;
        this.username = username;
        this.telefono = telefono;
        this.password = password;
        this.organizzatore=organizzatore;
    }
}
