/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import com.mycompany.gestioneeventi.Evento;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author berto
 */
public class GestioneEventiManagerEM {

    protected static EntityManagerFactory factory;
    protected static EntityManager entityManager;


    public static void setup(){
        factory = Persistence.createEntityManagerFactory("task_1");
        
    }
    
    public static void exit(){
        factory.close();
    }
    
   
    
    

    public static void creaConnessione() {
        entityManager = factory.createEntityManager();
    }
    public static void chiudiConnessione() {
        entityManager.close();
    }
    
    public static int registrazione(OrganizzatoreDb organizzatore,PartecipanteDb partecipante)
    {
        int errore=1;
        if(((organizzatore==null)&&(partecipante==null))||((organizzatore!=null)&&(partecipante!=null)))
        {
            System.err.println("parametri non validi riprova");
            return 0;
            
        }
        try{
            creaConnessione();
            entityManager.getTransaction().begin();
            if(organizzatore!=null)
                entityManager.persist(organizzatore);
            else
                entityManager.persist(partecipante);
            
            entityManager.getTransaction().commit();
        }
        catch(PersistenceException pe)
        {
            pe.printStackTrace();
            errore=0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Errore durante la transizione riprova!");
            errore=0;
        }
        finally
        {
            chiudiConnessione();
        }
        return errore;
    }
       
}
