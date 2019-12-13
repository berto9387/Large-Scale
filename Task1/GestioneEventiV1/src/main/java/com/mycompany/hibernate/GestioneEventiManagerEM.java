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
    
   public static int registrazione(OrganizzatoreDb organizzatore,PartecipanteDb partecipante){
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
    
    public static ArrayList<Evento> ricercaEventi(OrganizzatoreDb organizzatore){
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            organizzatore=entityManager.find(OrganizzatoreDb.class, organizzatore.getId());
            entityManager.getTransaction().commit();
        }catch (Exception ex){
            return null;
        } finally{
            entityManager.close();
        }
        
        
        ArrayList<Evento> ev=new ArrayList<>();
        
            for (EventoDb evento : organizzatore.getEventiCreati()) {
                    ev.add( new Evento((int)evento.getId(), evento.getNome(), evento.getLuogo(), evento.getData(), 
                                        evento.getOra(), evento.getPosti(), evento.getTipologia(), evento.getDescrizione(), 
                                        (int)evento.getOrganizzatore().getId(), evento.getNumero_partecipanti()));
            }
        
        return ev;
    }

    public static void creaConnessione() {
        entityManager = factory.createEntityManager();
    }
    public static void chiudiConnessione() {
        entityManager.close();
    }
       
}
