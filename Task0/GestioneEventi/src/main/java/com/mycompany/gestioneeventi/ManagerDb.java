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
      String sql = "insert into evento (nome,luogo,data,ora,posti,tipologia,descrizione,organizzatore)"+ "values ('"+nome+"','"+Luogo+"','"+data+"','"+Luogo+"',"+Posti+",'"+Tipologia+"','"+Descrizione+"',"+id+")";
      
      try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = con.createStatement();
          int rs = stmt.executeUpdate(sql);
         
         con.close();
      } catch (ClassNotFoundException | SQLException ex) {
          ex.printStackTrace();
          return 0;
      }
      return 1;
     

      
   }
      
   public ArrayList<Evento> ricercaEventi(int id){
        String sql;
        int esito=0;
        sql="select * from evento where organizzatore="+id+" and data>=current_date()";
        
        
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
  
}
