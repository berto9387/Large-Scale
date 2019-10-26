/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import java.sql.*;

/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniOrganizzatoreEM extends GestioneEventiManagerEM{
    
    
    public void inserisciOrganizzatore(String Nome, String Cognome, Date Data, String Email, String Username, String Phone, String Password){
        // long id, String nome, String cognome, Date data_nascita, String email, String password, String username, String phone
        OrganizzatoreDb organizzatore = new OrganizzatoreDb();
        organizzatore.setNome(Nome);
        organizzatore.setCognome(Cognome);
        organizzatore.setData_nascita(Data);
        organizzatore.setEmail(Email);
        organizzatore.setUsername(Username);
        organizzatore.setPhone(Phone);
        organizzatore.setPassword(Password);
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(organizzatore);
            entityManager.getTransaction().commit();
            System.out.println("ORGANIZZATORE Added");
            
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in updating a book!");
        } 
        finally{
            entityManager.close();
        }
    }
}
