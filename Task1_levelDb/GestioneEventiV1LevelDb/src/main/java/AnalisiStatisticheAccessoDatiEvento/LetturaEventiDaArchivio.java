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
    private final GestoreFileDiScrittura gestore;
    private final int numeroProcessi;
    private final int numeroEntrate;
    public LetturaEventiDaArchivio(StatisticaAccessoLettura statistica,String tipoDiArchivio,GestoreFileDiScrittura gestore,int numeroProcessi, int numeroEntrate)
    {
        this.statistica=statistica;
        this.tipoDiArchivio=tipoDiArchivio;
        partecipante= new PartecipanteDb(0, "", "", null, "", "", "", "", new HashSet<>());
        this.gestore=gestore;
        this.numeroProcessi=numeroProcessi;
        this.numeroEntrate=numeroEntrate;
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
            gestore.writeFile(numeroProcessi, tempoEsecuzione,numeroEntrate);
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
        LevelDbManager.RicercaEventi("", partecipante,true);
        return statistica.notificaFineLettura();
    }

     
}
