/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.sql.*;
import java.util.*;


public class DAO {

   
   protected static final String DB_URL ="jdbc:mysql://localhost:3306/task_0?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

   protected static final String DRIVER ="com.mysql.cj.jdbc.Driver";
   protected static final String USER = "root";
   protected static final String PASS = "";
   
   
    protected static int controllaEsistenza(String Email,int Ruolo){
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
    
    
       public static ArrayList<Evento> ricercaEventi(int id,boolean Ruolo,String Citta){
       //se citta coincide con partecipante trovo gli eventi al quale partecipa l'utente
        String sql;
        System.out.println(Ruolo);
        if(Ruolo==true){
            if(Citta.equals(""))
                sql="select * from evento where organizzatore="+id+" and data>=current_date()";
            else
               sql="select * from evento where organizzatore="+id+" and data>=current_date() and luogo='"+Citta+"'" ; 
        } else{
            if(Citta.equals("")){ //Caso in cui un partecipante vuole visualizzare gli eventi a cui è iscritto                
                sql="select * from evento where data>=current_date()";                
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
    
    public static void modificaEmail(String emailDaModificare,int idUtente,String tabellaUtenteDaModificare)//01
    {
         try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement ps= con.prepareStatement("UPDATE" + tabellaUtenteDaModificare 
                            + "SET email = ?"
                            + "WHERE id = ?"))
        {
            ps.setString(1,emailDaModificare);
            ps.setInt(2, idUtente);
            ps.executeUpdate();
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
 }       
    
    
    public static void modificaPassword(String passwordDaModificare,int idUtente,String tabellaUtenteDaModificare) //01
    {
         try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement ps= con.prepareStatement("UPDATE" + tabellaUtenteDaModificare 
                            + "SET password = ?"
                            + "WHERE id = ?");)
        {
            ps.setString(1,passwordDaModificare);
            ps.setInt(2, idUtente);
            ps.executeUpdate();
        }
        catch (Exception ex){System.err.println(ex.getMessage());}      
    
    
    }
   
    
   public static void  eliminaUtente(int idUtenteDaEliminare,String tabellaUtenteDaModificare)
   {
    {
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement ps= con.prepareStatement("DELETE FROM" +tabellaUtenteDaModificare
                            + "WHERE id = ?"))
        {
            ps.setInt(1,idUtenteDaEliminare);
            ps.executeUpdate();
            
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
  } 
   
   
   }
    
}

/*
                        COMMENTI DI GIANLUCA
01)  Funzioni che permettono di modificare l'email e la seconda di modificare la passwor 
     di un utente (organizzatore/partecipante).
        

*/