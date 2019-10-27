/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestioneeventi;

import com.mycompany.hibernate.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author Gianluca
 */
public class GeneralGrafic extends BorderPane{
    protected static Scene mainScene;
    protected static Group mainGroup;
    protected static Stage mainStage;
    protected static User utente;
    protected static PartecipanteDb partecipante;
    protected static OrganizzatoreDb organizzatore;
    public static void setParameters(Scene scene,Group group,Stage stage)
    {
        mainScene=scene;
        mainGroup=group;
        mainStage=stage;
        partecipante=null;
        organizzatore=null;
    }
}
/*
------------------------COMMENTI DI GIANLUCA-----------------------------------
La classe general grafic serve per rendere i parametri membro condivisi da 
tutte le classi che implementano le varie interfacce grafiche
partecipante e organizzzatore servono per non dover effettuare ogni volta
le query sugli eventi che uno ha organizzato o si è iscritto.
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!NOTA BENE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
utente per ora rimane per non creare errori con il codice ancora aggiornato e che
usa tale campo.



*/