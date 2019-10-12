/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class ManagerDb implements DAO {
    
    private int controllaEsistenza(String Email,int Ruolo){
        String sql;
        int esito=0;
        if(Ruolo==1){
            sql="select * from partecipante where email='"+Email+"'";
        }else{
            sql="select * from organizzatore where email='"+Email+"'";
        }
        
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next()){
                esito=1;
            } 
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
          ex.printStackTrace();
        }
      return esito;
    }
   public int inserisciOrganizzatore(String nome, String cognome, java.sql.Date data_nascita, String email,String username, String telefono,String password) {
      if(controllaEsistenza(email, 0)==0){
          return 0;
      } 
      String sql = "insert into organizzatore (nome,cognome,data_nascita,email,password,username,phone)"+ "values ('"+nome+"','"+cognome+"','"+data_nascita+"','"+email+"','"+password+"','"+username+"','"+telefono+"')";
      
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
   public int inserisciPartecipante(String nome, String cognome, java.sql.Date data_nascita, String email,String username, String telefono,String password) {
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
   
      public User loginPartecipante(String email,String password)
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
      public User loginOrganizzatore(String email,String password)
      {
          User utente=null;
          try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("SELECT * FROM organizzatore WHERE email=? AND password =? "))
          {
              ps.setString(1, email);
              ps.setString(2,password);
              ResultSet rs = ps.executeQuery();
              if(rs.next())
              {
                  java.util.Date data_nascita=rs.getDate("data_nascita");
                  utente= new User(rs.getString("nome"),rs.getString("cognome"),data_nascita,
                          rs.getString("email"),rs.getString("username"),rs.getString("phone"),rs.getString("password"),true,rs.getInt("id_Organizatore"));
              }
          } 
          catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
          return utente;
      }
      
      
      public int creaEvento(String nome, String Luogo, java.sql.Date data,String Ora, int Posti,String Tipologia, String Descrizione,int id) {
      String sql = "insert into evento (nome,luogo,data,ora,posti,tipologia,descrizione,organizzatore)"+ "values ('"+nome+"','"+Luogo+"','"+data+"','"+Ora+"',"+Posti+",'"+Tipologia+"','"+Descrizione+"',"+id+")";
      
      try {
         //Class.forName(DRIVER);
         Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = con.createStatement();
          int rs = stmt.executeUpdate(sql);
         
         con.close();
      } catch (SQLException ex) {
          ex.printStackTrace();
          return 0;
      }
      return 1;
     

      
   }
      
   public ArrayList<Evento> ricercaEventi(int id,boolean Ruolo,String Citta){
       
        String sql;
        System.out.println(Ruolo);
        if(Ruolo==true){
            if(Citta == "")
                sql="select * from evento where organizzatore="+id+" and data>=current_date()";
            else
               sql="select * from evento where organizzatore="+id+" and data>=current_date() and luogo='"+Citta+"'" ; 
        } else{
            if(Citta == ""){ //Caso in cui un partecipante vuole visualizzare gli eventi a cui è iscritto
                
                sql="SELECT E.* FROM evento E INNER JOIN partecipa P WHERE P.Utente="+id+" and data>=current_date()";
                
            } else{ //Caso in cui un partecipante vuole visualizzare tutti gli eventi
                
                sql="select * from evento where luogo='"+Citta+"' and data>=current_date()";
                
            }
        }
        
        
        ArrayList<Evento> ev=new ArrayList<>();
          try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement(sql))
          {
              ResultSet rs = ps.executeQuery();
              while(rs.next())
              {
                  java.util.Date data=rs.getDate("data");
                  Evento s1= new Evento(rs.getInt("id"),rs.getString("nome"),rs.getString("luogo"),data,
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

   public void updateNumeroPartecipantiEvento(int numero_aggiornato_partecipanti,int id_evento)
   {
           try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("UPDATE evento SET numero_partecipanti = ? WHERE id=?"))
           {
               ps.setInt(1,numero_aggiornato_partecipanti);
               ps.setInt(2, id_evento);
               ps.executeUpdate();
           } 
           catch (Exception ex){System.err.println(ex.getMessage());}
           
   
   }
  
   public void iscrizioneEvento(int id_evento,int id_partecipante)
   {
       
       int numero_partecipanti = 0;
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("SELECT numero_partecipanti FROM evento WHERE id = ?")){
            
            ps.setInt(1, id_evento);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                numero_partecipanti = rs.getInt("numero_partecipanti");
            }
            
        } catch (Exception ex){System.err.println(ex.getMessage());}
        
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("INSERT INTO partecipa(Utente,evento) VALUES(?,?) "))
        {
            ps.setInt(1,id_partecipante);
            ps.setInt(2,id_evento);
            ps.executeUpdate();
            updateNumeroPartecipantiEvento(++numero_partecipanti,id_evento);
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
        
        
   }
   
   public void annullaIscrizioneEvento(int id_utente, int id_evento){
       
       int numero_partecipanti = 0;
       try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("SELECT numero_partecipanti FROM evento WHERE id = ?")){
            
            ps.setInt(1, id_evento);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                numero_partecipanti = rs.getInt("numero_partecipanti");
            }
            
       } catch (Exception ex){System.err.println(ex.getMessage());}
       
       try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                  PreparedStatement ps= con.prepareStatement("DELETE FROM partecipa WHERE Utente = ? and evento = ?"))
        {
            ps.setInt(1,id_utente);
            ps.setInt(2,id_evento);
            ps.executeUpdate();
            updateNumeroPartecipantiEvento(--numero_partecipanti,id_evento);
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
   }
   
   
}
