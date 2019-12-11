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
    
   
    
    

    public static void creaConnesione() {
        entityManager = factory.createEntityManager();
    }
    public static void chiudiConnesione() {
        entityManager.close();
    }
       
}
