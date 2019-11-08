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
    
   
    
    public static ArrayList<Evento> ricercaEventi(OrganizzatoreDb organizzatore){
        try{
            entityManager.getTransaction().begin();
            organizzatore=entityManager.find(OrganizzatoreDb.class, organizzatore.getId());
            entityManager.getTransaction().commit();
        }catch (Exception ex){
            return null;
        }
        
        
        ArrayList<Evento> ev=new ArrayList<>();
        
            for (EventoDb evento : organizzatore.getEventiCreati()) {
                    ev.add( new Evento((int)evento.getId(), evento.getNome(), evento.getLuogo(), evento.getData(), 
                                        evento.getOra(), evento.getPosti(), evento.getTipologia(), evento.getDescrizione(), 
                                        (int)evento.getOrganizzatore().getId(), evento.getNumero_partecipanti()));
            }
        
        return ev;
    }

    public static void creaConnesione() {
        entityManager = factory.createEntityManager();
    }
    public static void chiudiConnesione() {
        entityManager.close();
    }
       
}
