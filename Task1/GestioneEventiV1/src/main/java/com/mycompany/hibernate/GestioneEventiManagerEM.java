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
        ArrayList<OrganizzatoreDb> listaOrganizzatore = new ArrayList<>();
        ArrayList<PartecipanteDb> listaPartecipanti=new ArrayList<>();
        
        if(Ruolo==1){
            sql="select * from partecipante where email='"+Email+"'";
            listaPartecipanti=(ArrayList)entityManager.createQuery(sql).getResultList();
            if(listaOrganizzatore.isEmpty())
                return 1;
                
        }else{
            sql="select * from organizzatore where email='"+Email+"'";
            listaOrganizzatore=(ArrayList)entityManager.createQuery(sql).getResultList();
            if(listaOrganizzatore.isEmpty())
                return 1;
        }
        
        
      return 0;
    }
        
}
