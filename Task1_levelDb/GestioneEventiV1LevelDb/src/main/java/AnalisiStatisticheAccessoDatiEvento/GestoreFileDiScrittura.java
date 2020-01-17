/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalisiStatisticheAccessoDatiEvento;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.*;

/**
 *
 * @author Gianluca
 */
public class GestoreFileDiScrittura {
    private BufferedWriter writer;
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
            writer = new BufferedWriter(
                    new FileWriter(folderName+"\\"+nameFile, true) 
                    );
        } catch (Exception ex) {
            Logger.getLogger(StatisticaAccessoLettura.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    private void closeFileConnection() throws IOException
    {
        writer.close();
    }
     
     public synchronized void writeFile(int numeroProcessi,long tempoDAccesso,int numeroEntrate) throws IOException
     {
            openFileConnection();
            writer.write(tempoDAccesso+" "+numeroEntrate);
            writer.newLine();
            closeFileConnection();
     }
}
