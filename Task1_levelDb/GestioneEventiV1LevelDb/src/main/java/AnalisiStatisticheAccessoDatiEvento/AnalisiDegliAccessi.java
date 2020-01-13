/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalisiStatisticheAccessoDatiEvento;

/**
 *
 * @author Gianluca
 */
public class AnalisiDegliAccessi {
    private static GestoreFileDiScrittura gestore;
    private static StatisticaAccessoLettura letturaEventi;

    private static final int NUMEROTHREAD = 1;
    private static final String TIPODIARCHIVIO = "MySql";
    private static final String NOMEFILE = TIPODIARCHIVIO + ".txt";
    private static final String NOMECARTELLA = "DatiStatisticheAccesso";
    public static void main(String args[])
    {
        
        gestore= new GestoreFileDiScrittura(NOMEFILE,NOMECARTELLA);
        letturaEventi= new StatisticaAccessoLettura(NUMEROTHREAD);
        for(int i=0;i<NUMEROTHREAD;i++)
        {
            new LetturaEventiDaArchivio(letturaEventi,TIPODIARCHIVIO,gestore,NUMEROTHREAD).start();
        }
    }
    
    
}
