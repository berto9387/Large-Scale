/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import static com.mycompany.gestioneeventi.DAO.DRIVER;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniDbLatoPartecipante extends DAO{

    
    
   public static int inserisciPartecipante(String nome, String cognome, java.sql.Date data_nascita, String email,String username, String telefono,String password) {
       if(controllaEsistenza(email, 1)==0){
          return 0;
      }
      String sql = "insert into partecipante (nome,cognome,data_nascita,email,password,username,phone)"+ "values ('"+nome+"','"+cognome+"','"+data_nascita+"','"+email+"','"+password+"','"+username+"','"+telefono+"')";
      
      try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = con.createStatement();
          int rs = stmt.executeUpdate(sql);
         
         con.close();
      } catch (ClassNotFoundException | SQLException ex) {
          ex.printStackTrace();
      }
      return 1;   
   }   
   
   
    public static User loginPartecipante(String email,String password)
      {
          User utente=null;
          try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("SELECT * FROM partecipante WHERE email=? AND password =? "))
          {
              ps.setString(1, email);
              ps.setString(2,password);
              ResultSet rs = ps.executeQuery();
              if(rs.next())
              {
                  java.util.Date data_nascita=rs.getDate("data_nascita");
                  utente= new User(rs.getString("nome"),rs.getString("cognome"),data_nascita,
                          rs.getString("email"),rs.getString("username"),rs.getString("phone"),rs.getString("password"),false,rs.getInt("id_Partecipante"));
              }
          } 
          catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
          return utente;
      }
    
    
    public static ArrayList<Evento> ricercaPrenotazioni(int id,boolean Ruolo){
       //se citta coincide con partecipante trovo gli eventi al quale partecipa l'utente
        String sql;
        System.out.println(Ruolo);
        if(Ruolo==false){
                            
                sql="SELECT E.* FROM evento E INNER JOIN partecipa P ON E.id_Evento = P.evento WHERE P.Utente="+id+" and data>=current_date()";                
            
        }else{
            return null;
        }
        
        
        ArrayList<Evento> ev=new ArrayList<>();
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement(sql))
          {
              ResultSet rs = ps.executeQuery();
              while(rs.next())
              {
                  java.util.Date data = rs.getDate("data");
                  Evento s1= new Evento(rs.getInt("id_Evento"),rs.getString("nome"),rs.getString("luogo"),data,
                          rs.getString("ora"),rs.getInt("posti"),rs.getString("tipologia"),rs.getString("descrizione"),rs.getInt("organizzatore"),
                            rs.getInt("numero_partecipanti"));
                  ev.add(s1);
              }
          } 
          catch (Exception ex) {
            System.err.println("questo è l'errore" + ex.getMessage());
            
        }
          return ev;
    }
    

    
   public static void updateNumeroPartecipantiEvento(int numero_aggiornato_partecipanti,int id_evento)
   {
           try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("UPDATE evento SET numero_partecipanti = ? WHERE id_Evento=?"))
           {
               ps.setInt(1,numero_aggiornato_partecipanti);
               ps.setInt(2, id_evento);
               ps.executeUpdate();
           } 
           catch (Exception ex){System.err.println(ex.getMessage());}
           
   
   }
  
   
   public static void iscrizioneEvento(int id_evento,int id_partecipante)
   {
       
       int numero_partecipanti = 0;
       
       if(controlloGiaIscrittoAllEvento(id_evento,id_partecipante))
           return;
       
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("SELECT numero_partecipanti FROM evento WHERE id_Evento = ?")){
            
            ps.setInt(1, id_evento);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                numero_partecipanti = rs.getInt("numero_partecipanti");
            }
            
        } catch (Exception ex){System.err.println(ex.getMessage());}
        
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("INSERT INTO partecipa(utente,evento) VALUES(?,?) "))
        {
            ps.setInt(1,id_partecipante);
            ps.setInt(2,id_evento);
            ps.executeUpdate();
            updateNumeroPartecipantiEvento(++numero_partecipanti,id_evento);
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
        
        
   }
   
   public static void annullaIscrizioneEvento(int id_utente, int id_evento){
       
       int numero_partecipanti = 0;
       try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("SELECT numero_partecipanti FROM evento WHERE id_Evento = ?")){
            
            ps.setInt(1, id_evento);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                numero_partecipanti = rs.getInt("numero_partecipanti");
            }
            
       } catch (Exception ex){System.err.println(ex.getMessage());}
       
       try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("DELETE FROM partecipa WHERE utente = ? and evento = ?"))
        {
            int num;
            ps.setInt(1,id_utente);
            ps.setInt(2,id_evento);
            num=ps.executeUpdate();
            if(num==0)
                return;
            updateNumeroPartecipantiEvento(--numero_partecipanti,id_evento);
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
   }
   
   
   public static boolean controlloGiaIscrittoAllEvento(int id_evento,int id_partecipante)
   {
       
       try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("SELECT * FROM partecipa WHERE evento = ? and utente = ? ")){
            
            ps.setInt(1, id_evento);
            ps.setInt(2, id_partecipante);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            }
            
        } catch (Exception ex){System.err.println(ex.getMessage());}
       return false;
   }
   
   public static int modificaDati(User partecipante, String email, String nuovaPassword, String vecchiaPassword){
       
         try(Connection con0 = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps0= con0.prepareStatement("SELECT * FROM partecipante WHERE id_Partecipante = ? and password = ? ")){
             
             ps0.setInt(1, partecipante.id);
             ps0.setString(2, vecchiaPassword);
             ResultSet rs = ps0.executeQuery();
             
             if(rs.next()){
                 
                 try(Connection con1 = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps1= con1.prepareStatement("UPDATE partecipante SET email = ?, password = ? WHERE id_Partecipante = ? ")){
                     
                     System.err.println(nuovaPassword);
                     
                     if(email.equals(""))
                         email = partecipante.email;
                     if(nuovaPassword.equals("")){
                         nuovaPassword = partecipante.password;
                     }
                     
                     System.err.println(nuovaPassword);
                     
                     ps1.setString(1, email);
                     ps1.setString(2, nuovaPassword);
                     ps1.setInt(3, partecipante.id);
                     
                     int res = ps1.executeUpdate();
                     
                     if(res == 0){
                         return 0;
                     }
                     
                 }catch (Exception ex){System.err.println(ex.getMessage());}
             } else{
                 return 0;
             }
             
         }catch (Exception ex){System.err.println(ex.getMessage());}
         
         return 1;
   }

    static int eliminaAccount(User partecipante) {
        
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps = con.prepareStatement("DELETE FROM partecipante WHERE id_Partecipante = ?")){
             
            ps.setInt(1, partecipante.id);
             
            int res = ps.executeUpdate();
                     
            if(res == 0){
                return 0;
            }
                     
             
         }catch (Exception ex){System.err.println(ex.getMessage());}
         
        return 1;
         
    }
    
}
