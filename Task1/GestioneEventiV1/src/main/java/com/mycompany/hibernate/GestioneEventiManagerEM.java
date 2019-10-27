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
    
    protected static int controllaEsistenza(String Email,int Ruolo){//01 GIANLUCA
        String sql;
        ArrayList<OrganizzatoreDb> listaOrganizzatore;
        ArrayList<PartecipanteDb> listaPartecipanti;
        entityManager = factory.createEntityManager();
        int risultato=0;
        if(Ruolo==1){
   
           // sql="select p from PartecipanteDb p where p.email:=Email";

            sql="select p from PartecipanteDb p where p.email=:Email";//01.1
            TypedQuery<PartecipanteDb> query= entityManager.createQuery(sql,PartecipanteDb.class);//01.2
            query=query.setParameter("Email", Email);//01.3
            
            //listaPartecipanti=(ArrayList)entityManager.createQuery(sql).getResultList();
            listaPartecipanti=(ArrayList)query.getResultList();//01.4
            if(listaPartecipanti.isEmpty())
            {
                System.out.println("EMAIL NON PRESENTE");
                risultato= 1;
            }
                
        }else{

            //sql="select o from OrgazizzatoreDb o where o.email='"+Email+"'";

            sql="select o from OrganizzatoreDb o where o.email=:Email";
            TypedQuery<OrganizzatoreDb> query= entityManager.createQuery(sql,OrganizzatoreDb.class);
            query=query.setParameter("Email", Email);
            //listaOrganizzatore=(ArrayList)entityManager.createQuery(sql).getResultList();
            listaOrganizzatore=(ArrayList)query.getResultList();
            if(listaOrganizzatore.isEmpty())
            {
                 System.out.println("EMAIL NON PRESENTE");
                risultato= 1;
            }
        }
        
       entityManager.close(); 
      return risultato;
    }
    
    public static ArrayList<Evento> ricercaEventi(OrganizzatoreDb organizzatore,boolean Ruolo,String Citta){
        
        String sql;
        System.out.println(Ruolo);
        ArrayList<EventoDb> listaEventi= new ArrayList<>();
        ArrayList<Evento> ev=new ArrayList<>();
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            if(Ruolo==true){
                if(Citta.equals(""))
                    sql="select e from EventoDb e where e.organizzatore="+organizzatore.getId()+" and data>=current_date()";
                else
                   sql="select e from EventoDb e where e.organizzatore="+organizzatore.getId()+" and data>=current_date() and luogo='"+Citta+"'" ; 

                TypedQuery<EventoDb> query= entityManager.createQuery(sql, EventoDb.class);
                listaEventi = (ArrayList)query.getResultList();

    //Evento(int id,String Nome,String Luogo, Date Data,String Ora,int Posti, String Tipologia, String Descrizione,int Organizzatore,int NumeroPartecipanti){
                for (EventoDb evento : listaEventi) {
                    ev.add( new Evento((int)evento.getId(), evento.getNome(), evento.getLuogo(), evento.getData(), 
                                        evento.getOra(), evento.getPosti(), evento.getTipologia(), evento.getDescrizione(), 
                                        (int)evento.getOrganizzatore().getId(), evento.getNumero_partecipanti()));
                }

            } else{
                if(Citta.equals("")){ //Caso in cui un partecipante vuole visualizzare gli eventi a cui Ã¨ iscritto                
                    sql="select * from evento where data>=current_date()";                
                } else{ //Caso in cui un partecipante vuole visualizzare tutti gli eventi                
                    sql="select * from evento where luogo='"+Citta+"' and data>=current_date()";                
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in searching events!");
        } 
        finally{
            entityManager.close();  
        }
        return ev;
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