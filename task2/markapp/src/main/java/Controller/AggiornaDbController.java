/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author berto
 */
public class AggiornaDbController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Text testoErrore;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        final ServiceAggiornaDatabase aggiorna = new ServiceAggiornaDatabase();

        //Here you tell your progress indicator is visible only when the service is runing
        progressIndicator.progressProperty().bind(aggiorna.progressProperty());
        aggiorna.setOnSucceeded((WorkerStateEvent workerStateEvent) -> {
            testoErrore.setText("Aggiornamento riuscito");
        });

        aggiorna.setOnFailed((WorkerStateEvent workerStateEvent) -> {
            testoErrore.setText("Aggiornamento non riuscito, riprova...");
        });
        aggiorna.restart();
    }
     
    
}
