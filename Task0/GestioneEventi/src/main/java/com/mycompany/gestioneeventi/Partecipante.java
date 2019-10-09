/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.util.*;

/**
 *
 * @author tony_
 */
public class Partecipante extends User{
    
    public Partecipante(String nome, String cognome, Date data_nascita, String email,String username){
        super(nome, cognome, data_nascita, email, username);
    }
}
