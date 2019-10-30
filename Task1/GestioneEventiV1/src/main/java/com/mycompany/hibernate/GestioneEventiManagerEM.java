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
    
   
    
    public static ArrayList<Evento> ricercaEventi(OrganizzatoreDb organizzatore,boolean Ruolo,String Citta){
        
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
/*
--------------------------COMMENTI DI GIANLUCA-------------------------------
01) La funzione permette di verificare se esiste già un account con l'email
    passata come parametro restituisce 1 se l'email non è presente mentre
    restituisco 0 se l'email non è presente.

    01.1 
        Scrivo una query che andrà a cercare l'email passata dal parametro
        posto di andare a concatenare la stringa Email andrò ad utilizzare
        la convenzione email:=Email
    01.2
        Creo una TypedQuery
    01.3
        vado a settare il parametro Email di Where email:=Email con la
        il parametro Email tramite la funzione setParameter().
*/