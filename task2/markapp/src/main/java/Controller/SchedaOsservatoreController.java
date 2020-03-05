/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.GestioneReportMongoDataAccess;
import Entita.Calciatore;
import Entita.Report;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;
import task2.markapp.ScreenController;

/**
 *
 * @author tony_
 */
public class SchedaOsservatoreController extends GeneralSchedaGiocatoreController{

    @FXML
    private Rating vecchioRating;
    
    @FXML
    private Rating nuovoRating;
    
    @FXML
    private TextArea nuovoCommento;
    
    @FXML
    private Label vecchioCommento;
    
    @FXML
    private Label nessunCommento;
    
    @FXML 
    private VBox vBoxVecchioReport;
    
    @FXML 
    private VBox vBoxNuovoReport;
    
    @FXML
    private Label vecchioTimeStamp;
    
    @FXML
    private HBox btnsOsservatore;
    
    @FXML
    private Button btnInserisciModificaReport;
    
    @FXML
    private Button btnInviaReport;
    
    @FXML
    private Button btnEliminaReport;
    
    private int intRating;
    
    private Report report;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    public SchedaOsservatoreController(Calciatore calciatore) {
        super(calciatore);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        super.initialize(location, resources);
        
        report = GestioneReportMongoDataAccess.cercaReport(ScreenController.getUtente(), calciatore);
        
        if(ScreenController.getUtente().getRuolo().equals("osservatore") || ScreenController.getUtente().getRuolo().equals("Osservatore")){
            btnsOsservatore.setVisible(true);
            btnEliminaReport.setVisible(true);
        }
        
        if(report != null){ //C'era già un report
            
            //Rendiamo visibili i campi relativi al report già presente
            vBoxVecchioReport.setVisible(true);
            vBoxNuovoReport.setVisible(false);
            aggiornaCampiReport(report.getCommento(), report.getTimeStampAggiunto(), report.getRating());
            vecchioRating.setRating(report.getRating());
            btnInserisciModificaReport.setText("  MODIFICA REPORT");
            
        }else {
            
            settaCampiNoReport();
            //Altrimenti 
            
        }
        
        btnInviaReport.setDisable(true);
        intRating = (int) nuovoRating.getRating();
        
    }
    
    @FXML
    public void handlerInviaReport(){
        if(nuovoCommento.getText().equals("")){
            System.out.println("Inserire un commento");
            return;
        }
        
        report = new Report(new Date(), nuovoCommento.getText(), intRating);
        
        int err = GestioneReportMongoDataAccess.aggiungiReport(report, ScreenController.getUtente(), calciatore);
        
        if(err == 0){
            System.out.println("Report inserito correttamente");
            vBoxVecchioReport.setVisible(true);
            vBoxNuovoReport.setVisible(false);
            aggiornaCampiReport(report.getCommento(), report.getTimeStampAggiunto(), report.getRating());
            nessunCommento.setVisible(false);
            
            btnInserisciModificaReport.setDisable(false);
            btnInserisciModificaReport.setText("  MODIFICA REPORT");
            btnInviaReport.setDisable(true);
        }else {
            System.out.println("Errore durante l'inserimento");
        }
    }
    
    @FXML
    public void handlerInserisciModificaReport(){
        
        //Salviamo l'eventuale valore del rating
        nuovoRating.ratingProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                
                intRating = newValue.intValue();
            }
            
        
        });
        
        vBoxVecchioReport.setVisible(false);
        vBoxNuovoReport.setVisible(true);
        
        
        nessunCommento.setVisible(false);
        nuovoCommento.setStyle(nuovoCommento.getStyle() + "-fx-font-family: Roboto; -fx-font-size: 18;");
        if(vecchioCommento.getText()!= null)
            nuovoCommento.setText(vecchioCommento.getText()); //Rendiamo invisibile il campo per aggiungere il testo
        
        btnInviaReport.setDisable(false);
        btnInserisciModificaReport.setDisable(true);
        
        nuovoCommento.requestFocus();
    }
    
    @FXML
    public void handlerEliminaReport(){
        
        int err = GestioneReportMongoDataAccess.eliminaReport(report, ScreenController.getUtente(), calciatore);
        
        if(err == 0){
            settaCampiNoReport();
        } else{
            System.out.println("Errore durante l'eliminazione");
        }
    }
    
    private void aggiornaCampiReport(String commento, Date timeStamp, int rating){
        vecchioCommento.setText(commento);
        vecchioCommento.setStyle(vecchioCommento.getStyle() + "-fx-font-family: Roboto; -fx-font-size: 18;");
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        vecchioTimeStamp.setText(df.format(timeStamp));
        vecchioTimeStamp.setStyle(vecchioTimeStamp.getStyle() + "-fx-font-family: Roboto; -fx-font-size: 18");
        
        vecchioRating.setRating(rating);
    }

    private void settaCampiNoReport() {
        nessunCommento.setText("Ancora nessun report presente. \n");
        if(ScreenController.getUtente().getRuolo().equals("osservatore") || ScreenController.getUtente().getRuolo().equals("Osservatore"))
            nessunCommento.setText(nessunCommento.getText() + "Per poter inserire un report premere il pulsante INSERISCI REPORT");

        vecchioCommento.setText("");
        nessunCommento.setVisible(true);
        btnInserisciModificaReport.setText("  INSERISCI REPORT");
        btnInviaReport.setDisable(true);

        vBoxVecchioReport.setVisible(false);
        vBoxNuovoReport.setVisible(false);
    }
    
}
