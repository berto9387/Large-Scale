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
public class StatisticaAccessoLettura {
    private long timestampInizioLetturaPrimoThread;
    private long timestampFineLetturaUltimoThread;
    private final int numeroMaxThreadInLettura;
    private int numeroThreadFineLettura;
    public StatisticaAccessoLettura(int numeroMaxThreadInLettura)
    {
        timestampInizioLetturaPrimoThread=0;
        timestampFineLetturaUltimoThread=0;
        this.numeroMaxThreadInLettura=numeroMaxThreadInLettura;
        numeroThreadFineLettura=0;
    }
    
    public synchronized void notificaInizioLettura()
    {
        if(timestampInizioLetturaPrimoThread==0)
            timestampInizioLetturaPrimoThread=System.currentTimeMillis();
    }
    public synchronized long notificaFineLettura()
    {
        ++numeroThreadFineLettura;
        if(numeroThreadFineLettura==numeroMaxThreadInLettura)
        {
            timestampFineLetturaUltimoThread=System.currentTimeMillis();
            return (timestampFineLetturaUltimoThread-timestampInizioLetturaPrimoThread);
            
        }
        return (long)-1;
    }
}
