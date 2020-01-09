/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalisiStatisticheAccessoDatiEvento;

import java.io.PrintWriter;

import java.util.logging.*;

/**
 *
 * @author Gianluca
 */
public class GestoreFileDiScrittura {
    private PrintWriter writer;
    private final String nameFile;
    private final String folderName;
 
    public GestoreFileDiScrittura(String nameFile,String folderName)
    {
        
        this.nameFile=nameFile;
        this.folderName=folderName;
    }
    
    private void openFileConnection()
    {
        try {
            writer = new PrintWriter(folderName+"\\"+nameFile);
        } catch (Exception ex) {
            Logger.getLogger(StatisticaAccessoLettura.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    private void closeFileConnection()
    {
        writer.close();
    }
     
     public synchronized void writeFile(int numeroProcessi,long tempoDAccesso)
     {
            openFileConnection();
            writer.print("L'accesso all'archivio con "+ numeroProcessi+ " processi concorrenziali "+ 
                    "ha il seguente tempo d'esecuzione: " + tempoDAccesso+" millisecondi"); 
            writer.print("\n");
            closeFileConnection();
     }
}
