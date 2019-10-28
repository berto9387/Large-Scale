/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import static com.mycompany.hibernate.GestioneEventiManagerEM.entityManager;
import java.sql.Date;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniOrganizzatoreEM extends GestioneEventiManagerEM{
    
    
    public static int inserisciOrganizzatore(OrganizzatoreDb organizzatore){
        // long id, String nome, String cognome, Date data_nascita, String email, String password, String username, String phone
        
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(organizzatore);
            entityManager.getTransaction().commit();
            System.out.println("ORGANIZZATORE Added");
            
        }catch(PersistenceException ex)
        {
            System.out.println("Attenzione utente esistente");
            return 0;
        }
        catch(Exception ex)
        {
            System.out.println("Riprova,qualcosa è andato storto!");
            return 0;
        }
        
       finally
        {
            entityManager.close();  
        }
        return 1;
    }
    
    public static OrganizzatoreDb loginOrganizzatore(String email, String password){
        OrganizzatoreDb organizzatore = null;
        
        String sql;
        ArrayList<OrganizzatoreDb> listaOrganizzatore;
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            sql="SELECT o FROM OrganizzatoreDb o WHERE o.email=:Email AND o.password=:Password";
                TypedQuery<OrganizzatoreDb> query= entityManager.createQuery(sql,OrganizzatoreDb.class);
                query=query.setParameter("Email", email);
                query=query.setParameter("Password", password);
                //listaOrganizzatore=(ArrayList)entityManager.createQuery(sql).getResultList();
                listaOrganizzatore=(ArrayList)query.getResultList();
                if(listaOrganizzatore.isEmpty())
                {
                     System.out.println("EMAIL O PASSWORD SBAGLIATE");

                } else {

                    organizzatore = listaOrganizzatore.get(0);

                    System.out.println("COGNOME = " + organizzatore.getCognome());
                }
        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in logging in!");
        } finally{
            
            entityManager.close();
        }
        
        return organizzatore;
    }
    
    public static int creaEvento(OrganizzatoreDb organizzatore) {
        int errore = 1;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(organizzatore);
            entityManager.getTransaction().commit();
            System.out.println("EVENTO Added");
            
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in creating an event!");
            errore = 0;
        } 
        finally{
            entityManager.close();  
        }
        
        return errore;
    }
}
