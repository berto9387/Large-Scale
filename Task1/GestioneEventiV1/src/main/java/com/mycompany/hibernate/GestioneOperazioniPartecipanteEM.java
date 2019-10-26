/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import java.sql.Date;
import java.util.*;
import java.util.Set;

/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniPartecipanteEM extends GestioneEventiManagerEM{
    
    public  int inserisciPartecipante(String nome, String cognome, java.sql.Date data_nascita, String email,String username, String telefono,String password) {
        PartecipanteDb partecipanteDaInserire = new PartecipanteDb();
       /* if(controllaEsistenza(email, 1)==0)
        {
            return 0;
        }*/
        partecipanteDaInserire.setNome(nome);
        partecipanteDaInserire.setCognome(cognome);
        partecipanteDaInserire.setData_nascita(data_nascita);
        partecipanteDaInserire.setUsername(username);
        partecipanteDaInserire.setPhone(telefono);
        partecipanteDaInserire.setPassword(password);
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(partecipanteDaInserire);
            entityManager.getTransaction().commit();
            System.out.println("PARTECIPANTE Added");
            
        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("A problem occured in updating a book!");
        } 
       finally
        {
            entityManager.close();  
        }
        return 1;
    
    }
}
