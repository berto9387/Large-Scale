/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import static com.mycompany.gestioneeventi.DAO.DRIVER;
import java.sql.*;


/**
 *
 * @author Gianluca
 */
public class GestioneOperazioniDbLatoOrganizzatore extends DAO{
    
    public static int inserisciOrganizzatore(String nome, String cognome, java.sql.Date data_nascita, String email,String username, String telefono,String password) {
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
   
   
      public static User loginOrganizzatore(String email,String password)// GIANLUCA 01)
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
                          rs.getString("email"),rs.getString("username"),rs.getString("phone"),rs.getString("password"),true,rs.getInt("id_Organizzatore"));
              }
          } 
          catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
          return utente;
      }       
 
      

  public static int creaEvento(String nome, String Luogo, java.sql.Date data,String Ora, int Posti,String Tipologia, String Descrizione,int id) {
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
  
  
  public static void eliminaEvento(int idEvento,int idOrganizzatore)//GIANLUCA 02
  {
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement ps= con.prepareStatement("DELETE FROM evento WHERE id_Evento = ? and organizzatore = ?");)
        {
            ps.setInt(1,idEvento);
            ps.setInt(2,idOrganizzatore);
            ps.executeUpdate();
            
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
  }

  
  
 public static void modificaEvento(Evento eventoModificato,int idOrganizzatore)//GIANLUCA 03
 {
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement ps= con.prepareStatement("UPDATE evento "
                            + "SET nome = ?,luogo= ?,data = ?,ora = ?,posti=?,tipologia=?,descrizione=?"
                            + "WHERE id = ? and organizzatore = ?");)
        {
            ps.setString(1,eventoModificato.getNome());
            ps.setString(2,eventoModificato.getLuogo());
            ps.setDate(3, (Date) eventoModificato.getData());
            ps.setString(4,eventoModificato.getOra());
            ps.setInt(5, eventoModificato.getPosti());
            ps.setString(6, eventoModificato.getTipologia());
            ps.setString(7,eventoModificato.getDescrizione());
            ps.setInt(8, eventoModificato.getId());
            ps.setInt(9, idOrganizzatore);
            ps.executeUpdate();
            
            
        }
        catch (Exception ex){System.err.println(ex.getMessage());}
 }
      
}


/*
COMMENTI DI GIANLUCA

01  Funzione che permette di efettuare un login di un organizzatore 
    e restituisce null se non vi è presente un utente che abbia gli stessi
    email e password passati come parametri alla funzione altrimenti restituisco
    i dati dell'organizzatore.
02  Funzione che permette ad un organizzatore di eliminare un suo evento.

03  Funzione che prende in ingresso i dat imodificati di un evento e idOrganizzatore ed esegue
    un update sul server del database.

    

*/