/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

//import java.sql.Date;
import static com.mycompany.hibernate.GestioneEventiManagerEM.entityManager;
import java.util.*;
import javax.persistence.*;
//import java.util.Set;
 
/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniPartecipanteEM extends GestioneEventiManagerEM{
    
    public static int inserisciPartecipante(String nome, String cognome, java.util.Date data_nascita, String email,String username, String telefono,String password) {
        PartecipanteDb partecipanteDaInserire = new PartecipanteDb();
       if(controllaEsistenza(email, 1)==0)
        {
            return 0;
        }
       
        partecipanteDaInserire.setEmail(email);
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
            System.out.println(data_nascita);
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
    
    public static PartecipanteDb loginPartecipante(String email,String password)
    {
        entityManager = factory.createEntityManager();
        ArrayList<PartecipanteDb> listaPartecipanti;
        PartecipanteDb partecipante;
        String sql="select p from PartecipanteDb p where p.email=:Email and p.password=:Password";//01.1
        TypedQuery<PartecipanteDb> query= entityManager.createQuery(sql,PartecipanteDb.class);//01.2
        query=query.setParameter("Email", email);
        query=query.setParameter("Password",password);
        listaPartecipanti=(ArrayList)query.getResultList();
        if(listaPartecipanti.isEmpty())
        {
            partecipante=null;
            System.out.println("oggetto null");
        }
        else
            partecipante=listaPartecipanti.get(0);
        
        entityManager.close();
        return partecipante;
    }
}
