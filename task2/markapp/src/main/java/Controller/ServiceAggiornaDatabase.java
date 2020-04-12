/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.aggiornaDatabase;
import java.io.File;
import java.util.Scanner;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServiceAggiornaDatabase extends Service<String> {
    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                int numeroCalciatori=0;
                File folder = new File("./calciatori");
                File[] listOfFiles = folder.listFiles();
                numeroCalciatori=listOfFiles.length;
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
                        int er=aggiornaDatabase.aggiorna(data);
                        System.out.println(er);
                    }
                    Thread.sleep(300);
                }
                
                return "Ciao";
            }
        };
    }
}