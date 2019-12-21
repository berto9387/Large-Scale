/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalisiStatisticheAccessoDatiEvento;
import com.mycompany.gestioneeventi.LevelDbManager;
import com.mycompany.hibernate.*;

import java.util.*;


/**
 *
 * @author Gianluca
 */
public class LetturaEventiDaArchivio extends Thread{
    private final StatisticaAccessoLettura statistica;
    private final String tipoDiArchivio;
    private final PartecipanteDb partecipante;
    public LetturaEventiDaArchivio(StatisticaAccessoLettura statistica,String tipoDiArchivio)
    {
        this.statistica=statistica;
        this.tipoDiArchivio=tipoDiArchivio;
        partecipante= new PartecipanteDb(0, "", "", null, "", "", "", "", new HashSet<>());
        
    }
    
    @Override
    public void run()
    {
        long tempoEsecuzione;
        GestioneOperazioniPartecipanteEM.setup();
        if(tipoDiArchivio.equals("MySql"))
        {
            tempoEsecuzione=letturaDaMysql();
        }
        else
        {
            tempoEsecuzione=letturaDaLevelDb();
        }
        if(tempoEsecuzione>0)
        {
            System.out.println("-----------Il tempo di lettura da "+tipoDiArchivio +"è: " + tempoEsecuzione+ " --------------");
        }
        GestioneOperazioniPartecipanteEM.exit();
    }
    
    public long letturaDaMysql()
    {
        statistica.notificaInizioLettura();      
        GestioneOperazioniPartecipanteEM.ricercaEventi(0,"");
        return statistica.notificaFineLettura();
    }
    
    public long letturaDaLevelDb()
    {        
        statistica.notificaInizioLettura();      
        LevelDbManager.RicercaEventi("", partecipante);
        return statistica.notificaFineLettura();
    }
}
