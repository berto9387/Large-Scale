/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

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
    
    protected static int controllaEsistenza(String Email,int Ruolo){
        String sql;
        ArrayList<OrganizzatoreDb> listaOrganizzatore;
        ArrayList<PartecipanteDb> listaPartecipanti;
        entityManager = factory.createEntityManager();
        int risultato=0;
        if(Ruolo==1){
            sql="select p from partecipante p where p.email:= email";
            listaPartecipanti=(ArrayList)entityManager.createQuery(sql).getResultList();
            if(listaPartecipanti.isEmpty())
            {
                System.out.println("EMAIL NON PRESENTE");
                risultato= 1;
            }
                
        }else{
            sql="select o from organizzatore o where o.email:=email";
            listaOrganizzatore=(ArrayList)entityManager.createQuery(sql).getResultList();
            if(listaOrganizzatore.isEmpty())
            {
                 System.out.println("EMAIL NON PRESENTE");
                risultato= 1;
            }
        }
        
       entityManager.close(); 
      return risultato;
    }
        
}
