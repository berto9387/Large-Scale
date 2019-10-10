/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.sql.*;


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
   public int inserisciOrganizzatore(String nome, String cognome, Date data_nascita, String email,String username, String telefono,String password) {
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
   public int inserisciPartecipante(String nome, String cognome, Date data_nascita, String email,String username, String telefono,String password) {
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
   

  
}
