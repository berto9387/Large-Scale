/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

//import java.sql.Date;
import com.mycompany.gestioneeventi.Evento;
import static com.mycompany.hibernate.GestioneEventiManagerEM.entityManager;
import java.util.*;
import javax.persistence.*;
//import java.util.Set;
 
/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniPartecipanteEM extends GestioneEventiManagerEM{
    
    public static int inserisciPartecipante(PartecipanteDb partecipante) {
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(partecipante);
            entityManager.getTransaction().commit();
            System.out.println("PARTECIPANTE Added");
        }
        catch(PersistenceException ex)
        {
            System.out.println("Attenzione utente esistente");
            return 0;
        }
        catch(Exception ex)
        {
            return 0;
        }
        
       finally
        {
            entityManager.close();  
        }
        return 1;
    
    }
    
    public static PartecipanteDb loginPartecipante(String email,String password)
    {
        PartecipanteDb partecipante = null;
        
        String sql;
        ArrayList<PartecipanteDb> listaPartecipanti;
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            sql="select p from PartecipanteDb p where p.email=:Email and p.password=:Password";//0.0.1
                TypedQuery<PartecipanteDb> query= entityManager.createQuery(sql,PartecipanteDb.class);//01.2
                query=query.setParameter("Email", email);
                query=query.setParameter("Password", password);
                //listaOrganizzatore=(ArrayList)entityManager.createQuery(sql).getResultList();
                listaPartecipanti=(ArrayList)query.getResultList();
                if(listaPartecipanti.isEmpty())
                {
                     System.out.println("EMAIL O PASSWORD SBAGLIATE");

                } else {

                    partecipante = listaPartecipanti.get(0);

                    System.out.println("COGNOME = " + partecipante.getCognome());
                }
        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in logging in!");
        } finally{
            entityManager.close();
        }
        
        return partecipante;
    }
    
    public static ArrayList<Evento> ricercaEventi(PartecipanteDb partecipante, String Citta){
        
        String sql;
        List<EventoDb> listaEventi;
        ArrayList<Evento> ev=new ArrayList<>();
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            if(Citta.equals("")){ //Caso in cui un partecipante vuole visualizzare gli eventi a cui è iscritto                
                    sql="select e from EventoDb e where  e.data>=current_date() and e.id not in (select d.id from PartecipanteDb p join p.book d  where  p.id="+partecipante.getId()+")";   
                    
            } else{ //Caso in cui un partecipante vuole visualizzare tutti gli eventi                
                sql="select e from EventoDb e where e.luogo='"+Citta+"' and e.data>=current_date() and e.id not in (select d.id from PartecipanteDb p join p.book d  where  p.id="+partecipante.getId()+")";                
            }
            
            TypedQuery<EventoDb> query= entityManager.createQuery(sql, EventoDb.class);
            listaEventi = query.getResultList();
            
            for (EventoDb evento : listaEventi) {
                    ev.add( new Evento((int)evento.getId(), evento.getNome(), evento.getLuogo(), evento.getData(), 
                                        evento.getOra(), evento.getPosti(), evento.getTipologia(), evento.getDescrizione(), 
                                        (int)evento.getOrganizzatore().getId(), evento.getNumero_partecipanti()));
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

    public static ArrayList<Evento> ricercaPrenotazioni(PartecipanteDb partecipante) {
        
        ArrayList<Evento> ev=new ArrayList<>();
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Date todayDate = new Date();
            for (EventoDb evento : partecipante.getBook()) {
                if(todayDate.before(evento.getData()))
                    ev.add( new Evento((int)evento.getId(), evento.getNome(), evento.getLuogo(), evento.getData(), 
                                        evento.getOra(), evento.getPosti(), evento.getTipologia(), evento.getDescrizione(), 
                                        (int)evento.getOrganizzatore().getId(), evento.getNumero_partecipanti()));
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

    public static void iscrizioneEvento(PartecipanteDb partecipante, String id) {
        String sql;
        
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            sql="select e from EventoDb e where e.id=:id";           
            TypedQuery<EventoDb> query= entityManager.createQuery(sql, EventoDb.class);
            query=query.setParameter("id", Long.parseLong(id));
            EventoDb ev =query.getSingleResult();
            partecipante.addBook(ev);
            entityManager.merge(partecipante);
            entityManager.getTransaction().commit();
            System.out.println("Iscrizione avvenuta con successo");
            
            
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in insert events!");
        } 
        finally{
            entityManager.close();  
        }
    }

    public static void annullaIscrizioneEvento(PartecipanteDb p) {

        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(p);
            entityManager.getTransaction().commit();
            System.out.println("iscrizione annullata");
            
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in  delete an event!");
            
        } 
        finally{
            entityManager.close();  
        }
        
    }
}
