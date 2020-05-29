/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AggiornaDataBaseNeo4jDataAccess;
import java.io.File;
import java.util.Scanner;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Gianluca
 */
public class ServiceAggiornaDataBase extends Service<String> {
    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                int numeroCalciatori=0;
                File folder = new File("./calciatori");
                File[] listOfFiles = folder.listFiles();
                File folderSocieta = new File("./societa");
                File[] listOfFilesSocieta = folderSocieta.listFiles();
                numeroCalciatori=listOfFiles.length + listOfFilesSocieta.length;
                int cont=1;
                for (File file : listOfFiles) {
                    updateProgress(++cont, numeroCalciatori);
                    if (file.isFile()) {
                        Scanner myReader = new Scanner(file);
                        String data="";
                        while (myReader.hasNextLine()) {
                          data += myReader.nextLine();
                          
                        }                        
                        myReader.close();
                        int er=0;
                        er=AggiornaDataBaseNeo4jDataAccess.aggiornaCalciatore(data);
                        System.out.println(er);
                    }
                    Thread.sleep(300);
                }
                for (File file : listOfFilesSocieta) {
                    updateProgress(++cont, numeroCalciatori);
                    if (file.isFile()) {
                        Scanner myReader = new Scanner(file);
                        String data="";
                        while (myReader.hasNextLine()) {
                          data += myReader.nextLine();
                          
                        }                        
                        myReader.close();
                        int er=0;
                        er=AggiornaDataBaseNeo4jDataAccess.aggiornaSocieta(data);
                        System.out.println(er);
                    }
                    Thread.sleep(300);
                }                
                return "Ciao";
            }
        };
    }
}
