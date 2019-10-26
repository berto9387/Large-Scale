/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import javax.persistence.*;

/**
 *
 * @author berto
 */
public class GestioneEventiManagerEM {
    private EntityManagerFactory factory;
    private EntityManagerFactory entityManager;
    
    public void setup(){
        factory=Persistence.createEntityManagerFactory("task_1");
    }
    public void exit(){
        factory.close();
    }
}
