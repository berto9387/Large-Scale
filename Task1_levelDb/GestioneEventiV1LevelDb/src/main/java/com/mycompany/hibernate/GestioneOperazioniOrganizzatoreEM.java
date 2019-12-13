/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import com.mycompany.gestioneeventi.Evento;
import static com.mycompany.hibernate.GestioneEventiManagerEM.entityManager;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniOrganizzatoreEM extends GestioneEventiManagerEM{
    
    public static OrganizzatoreDb loginOrganizzatore(String email, String password){
        OrganizzatoreDb organizzatore = null;
        
        String sql;
        ArrayList<OrganizzatoreDb> listaOrganizzatore;
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            sql="SELECT o FROM OrganizzatoreDb o WHERE o.email=:Email AND o.password=:Password";
            TypedQuery<OrganizzatoreDb> query= entityManager.createQuery(sql,OrganizzatoreDb.class);
            query=query.setParameter("Email", email);
            query=query.setParameter("Password", password);
            //listaOrganizzatore=(ArrayList)entityManager.createQuery(sql).getResultList();
            listaOrganizzatore=(ArrayList)query.getResultList();
            if(listaOrganizzatore.isEmpty())
            {
                 System.out.println("EMAIL O PASSWORD SBAGLIATE");

            } else {

                organizzatore = listaOrganizzatore.get(0);

                System.out.println("COGNOME = " + organizzatore.getCognome());
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
            System.out.println("A problem occured in logging in!");
        } finally{
            entityManager.close();
        }
        
        return organizzatore;
    }
    
    public static int creaEvento(OrganizzatoreDb organizzatore) {
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(organizzatore);
            entityManager.getTransaction().commit();
            System.out.println("EVENTO Added");
            
            
        } catch(Exception ex){
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
            System.out.println("A problem occured in creating an event!");
            return 0;
            
        }
        finally{
            entityManager.close();  
        }
        return 1;
    }
    
    public static int modificaEvento(long id,int posti) {
        int errore = 1;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            EventoDb evt=entityManager.find(EventoDb.class, id);
            evt.setPosti(posti);
            entityManager.merge(evt);
            entityManager.getTransaction().commit();
            System.out.println("EVENTO Modificato");
            
        } catch(Exception ex){
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
            System.out.println("A problem occured in updating an event!");
            errore = 0;
        }
        finally{
            entityManager.close();  
        }
        
        return errore;
    }
    public static int eliminaEvento(long id) {
        int errore = 1;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            EventoDb ev=entityManager.find(EventoDb.class, id);
            for(Iterator<PartecipanteDb> it=ev.getPartecipazioni().iterator();it.hasNext();){
                PartecipanteDb p=it.next();
                it.remove();
                ev.removePartecipante(p);
            }
            ev.removeOrganizzatore();
            entityManager.remove(ev);
            entityManager.getTransaction().commit();
            System.out.println("EVENTO Cancellato");
            
        } catch(Exception ex){
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            System.out.println("A problem occured in  delete an event!");
            errore = 0;
        }
        finally{
            entityManager.close();  
        }
        return errore;
    }

    public static int inserisciEvento(EventoDb evento) {
        int errore=1;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(evento);
            entityManager.getTransaction().commit();
            System.out.println("Evento Added");
            
        }catch(PersistenceException ex)
        {
            System.out.println("Attenzione evento esistente");
            errore=0;
        }
        catch(Exception ex)
        {
            entityManager.getTransaction().rollback();
            System.out.println("Riprova,qualcosa è andato storto!");
            errore=0;
        }
        
        finally{
            entityManager.close();  
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
        }
        finally{
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
}
