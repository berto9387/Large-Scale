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
            listaPartecipanti=(ArrayList)query.getResultList();

            if(listaPartecipanti.isEmpty())
            {
                 System.out.println("EMAIL O PASSWORD SBAGLIATE");

            } else {

                partecipante = listaPartecipanti.get(0);

                System.out.println("COGNOME = " + partecipante.getCognome());
            }
            for (EventoDb evento : partecipante.getBook()) {
                evento = entityManager.getReference(evento.getClass(), evento.getId());
                partecipante.addBook(evento);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in logging in!");
        } finally{
            entityManager.close();  
        }
        return partecipante;
    }
    
    public static ArrayList<Evento> ricercaEventi(long idPartecipante, String Citta){
        
        String sql;
        List<EventoDb> listaEventi;
        ArrayList<Evento> ev=new ArrayList<>();
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            if(Citta.equals("")){ //Caso in cui un partecipante vuole visualizzare gli eventi a cui è iscritto                
                    sql="select e from EventoDb e where e.numero_partecipanti<e.posti and  e.data>=current_date() and e.id not in (select d.id from PartecipanteDb p join p.book d  where  p.id="+idPartecipante+")";   
                    
            } else{ //Caso in cui un partecipante vuole visualizzare tutti gli eventi                
                sql="select e from EventoDb e where e.numero_partecipanti<e.posti and e.luogo='"+Citta+"' and e.data>=current_date() and e.id not in (select d.id from PartecipanteDb p join p.book d  where  p.id="+idPartecipante+")";                
            }
            
            TypedQuery<EventoDb> query= entityManager.createQuery(sql, EventoDb.class);
            listaEventi = query.getResultList();
            
            for (EventoDb evento : listaEventi) {
                    ev.add( new Evento((int)evento.getId(), evento.getNome(), evento.getLuogo(), evento.getData(), 
                                        evento.getOra(), evento.getPosti(), evento.getTipologia(), evento.getDescrizione(), 
                                        (int)evento.getOrganizzatore().getId(), evento.getNumero_partecipanti()));
            }
            entityManager.getTransaction().commit();
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in searching events!");
        } finally {
            entityManager.close();  
        }
        return ev;
    }

    public static ArrayList<Evento> ricercaPrenotazioni(PartecipanteDb partecipante) {
        
        ArrayList<Evento> ev=new ArrayList<>();
        
           
           Date todayDate = new Date();
            for (EventoDb evento : partecipante.getBook()) {
                if(todayDate.before(evento.getData()))
                    ev.add( new Evento((int)evento.getId(), evento.getNome(), evento.getLuogo(), evento.getData(), 
                                        evento.getOra(), evento.getPosti(), evento.getTipologia(), evento.getDescrizione(), 
                                        (int)evento.getOrganizzatore().getId(), evento.getNumero_partecipanti()));
            }
           
        
            
        return ev;
    }

    public static int iscrizioneEvento(PartecipanteDb partecipante,long id_Evento) {
        
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            EventoDb ev=entityManager.find(EventoDb.class, id_Evento);
            for(PartecipanteDb partecipanteDaAttaccare:ev.getPartecipazioni()){
                partecipanteDaAttaccare = entityManager.getReference(partecipanteDaAttaccare.getClass(), partecipanteDaAttaccare.getId());
                ev.addPartecipante(partecipanteDaAttaccare);
            }
            partecipante.addBook(ev);
            entityManager.merge(ev);
            entityManager.getTransaction().commit();
            //p=entityManager.find(PartecipanteDb.class, partecipante.getId());
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in insert events!");
            return 0;
            
        }finally{
            entityManager.close();  
        }
        return 1;
    }
    public static void annullaIscrizioneEvento(EventoDb p) {

        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(p);
            entityManager.getTransaction().commit();
            System.out.println("iscrizione annullata");
            
        } catch(Exception ex){
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
            System.out.println("A problem occured in  delete an event!");
            
        }finally{
            entityManager.close();  
        }
        
    }

    public static int eliminaAccount(long id) {
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            PartecipanteDb p=entityManager.find(PartecipanteDb.class, id);
            for(Iterator<EventoDb> it=p.getBook().iterator();it.hasNext();){
                EventoDb evt=it.next();
                it.remove();
                p.removeBook(evt);
            }
            entityManager.remove(p);
            entityManager.getTransaction().commit();
            System.out.println("Utente Eliminato");
        } catch(Exception ex){
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
            System.out.println("Non è stato possibile eliminare l'utente!");
            return 0;
            
        }finally{
            entityManager.close();  
        }
        return 1;
    }

    public static int modificaDati(PartecipanteDb partecipante) {
        int errore=2;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(partecipante);
            entityManager.getTransaction().commit();
            errore=1;
            
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in insert events!");
            errore=2;
        }finally{
            entityManager.close();  
        }
        return errore;
    }

    public static EventoDb trovaEvento(String id) {
        EventoDb ev=null;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            ev=entityManager.find(EventoDb.class, Long.parseLong(id));
            entityManager.getTransaction().commit();
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in insert events!");
            return null;
        }finally{
            entityManager.close();  
        }
        return ev;
    }
    public static PartecipanteDb trovaPartecipante(long id) {
        PartecipanteDb p=null;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            p=entityManager.find(PartecipanteDb.class, id);
            entityManager.getTransaction().commit();
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("A problem occured in insert events!");
            return null;
        }finally{
            entityManager.close();  
        }
        return p;
    }

   
}
