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
    
    public static int inserisciPartecipante(PartecipanteDb partecipante) {
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(partecipante);
            entityManager.getTransaction().commit();
            System.out.println("PARTECIPANTE Added");
        }
        catch(PersistenceException ex)
        {
            System.out.println("Attenzione utente esistente");
            return 0;
        }
        catch(Exception ex)
        {
            return 0;
        }
        
       finally
        {
            entityManager.close();  
        }
        return 1;
    
    }
    
    public static PartecipanteDb loginPartecipante(String email,String password)
    {
        PartecipanteDb partecipante = null;
        
        String sql;
        ArrayList<PartecipanteDb> listaPartecipanti;
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            sql="select p from PartecipanteDb p where p.email=:Email and p.password=:Password";//0.0.1
                TypedQuery<PartecipanteDb> query= entityManager.createQuery(sql,PartecipanteDb.class);//01.2
                query=query.setParameter("Email", email);
                query=query.setParameter("Password", password);
                //listaOrganizzatore=(ArrayList)entityManager.createQuery(sql).getResultList();
                listaPartecipanti=(ArrayList)query.getResultList();
                if(listaPartecipanti.isEmpty())
                {
                     System.out.println("EMAIL O PASSWORD SBAGLIATE");

                } else {

                    partecipante = listaPartecipanti.get(0);

                    System.out.println("COGNOME = " + partecipante.getCognome());
                }
        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in logging in!");
        } finally{
            entityManager.close();
        }
        
        return partecipante;
    }
}
