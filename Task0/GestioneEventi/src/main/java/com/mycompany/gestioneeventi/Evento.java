/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import java.util.Date;

/**
 *
 * @author berto
 */
public class Evento {
    int id;
    String Nome;
    String Luogo;
    Date Data;
    int Posti;
    String Tipologia;
    String Descrizione;
    int Organizzatore;
    protected Evento(){
    
    }
    protected Evento(int id,String Nome,String Luogo, Date Data,int Posti, String Tipologia, String Descrizione,int Organizzatore){
        this.id = id;
        this.Nome = Nome;
        this.Luogo = Luogo;        
        this.Data = Data;
        this.Posti = Posti;
        this.Tipologia = Tipologia;
        this.Descrizione = Descrizione;
        this.Organizzatore=Organizzatore;
    }
    
}
