/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entita.Calciatore;
import Entita.Statistica;
import com.sun.xml.internal.ws.util.StringUtils;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author tony_
 */
public class InfoPrincipaliCalciatoreController extends GeneralSchedaGiocatoreController{
    
    @FXML
    private Label dataNascita;

    @FXML
    private Label luogoNascita;

    @FXML
    private Label nazionalita;

    @FXML
    private Label procuratore;

    @FXML
    private Label altezza;

    @FXML
    private Label ruolo;

    @FXML
    private Label squadraAttuale;

    @FXML
    private Label minutiGiocatiStagioneAttuale;

    @FXML
    private Label goalStagioneAttuale;

    @FXML
    private Label cartelliniGialliStagioneAttuale;

    @FXML
    private Label cartelliniRossiStagioneAttuale;
    
    @FXML
    private Label assist_goalSubitiLabel;
    
    @FXML
    private Label assist_goalSubitiStagioneAttuale;
    
    @FXML
    private Label partiteNoGoalLabel;
    
    @FXML
    private Label partiteNoGoalStagioneAttuale;

    @FXML
    private Label valoreMercato;
    
    @FXML
    private HBox infoPrestito;

    @FXML
    private Label inPrestitoDa;

    @FXML
    private Label contrattoFinoAl;
    
    

    public InfoPrincipaliCalciatoreController(Calciatore calciatore) {
        super(calciatore);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Image image = new Image(calciatore.getLinkFoto());
            imgCalciatore.setImage(image);
        } catch(Exception e){
            e.printStackTrace();
        }
        
        nomeCalciatore.setText(calciatore.getNome());
        
        btnInfoPrincipali.setStyle(btnInfoPrincipali.getStyle() + "-fx-border-color: orange;");
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
        dataNascita.setText(df.format(calciatore.getDataNascita()));
        
        luogoNascita.setText(calciatore.getLuogoNascita());
        
        nazionalita.setText(calciatore.getNazionalita());
        
        procuratore.setText(calciatore.getProcuratore());
        
        altezza.setText(Integer.toString(calciatore.getAltezza()) + "cm");
        
        ruolo.setText(calciatore.getRuoloPrincipale());
        
        squadraAttuale.setText(calciatore.getSquadra());
        
        minutiGiocatiStagioneAttuale.setText(Integer.toString(calciatore.getMinutiGiocatiStagioneAttuale()));
        
        goalStagioneAttuale.setText(Integer.toString(calciatore.getGoalStagioneAttuale()));
        
        cartelliniGialliStagioneAttuale.setText(Integer.toString(calciatore.getCartelliniGialliStagioneAttuale()));
        
        cartelliniRossiStagioneAttuale.setText(Integer.toString(calciatore.getCartelliniRossiStagioneAttuale()));
        
        String valoreFormattato = String.format("%,d", calciatore.getValoreMercato());
        valoreMercato.setText(valoreFormattato + "€");
        
        if(calciatore.getRuoloPrincipale().equals("Portiere")){
            assist_goalSubitiLabel.setText("Goal subiti: ");
            assist_goalSubitiStagioneAttuale.setText(Integer.toString(calciatore.getGoalSubitiStagioneAttuale()));
            
            partiteNoGoalLabel.setVisible(true);
            partiteNoGoalStagioneAttuale.setText(Integer.toString(calciatore.getPartiteNoGoalStagioneAttuale()));
            
        }else{
            assist_goalSubitiLabel.setText("Assist: ");
            assist_goalSubitiStagioneAttuale.setText(Integer.toString(calciatore.getAssistStagioneAttuale()));
        }
        
        if(calciatore.getInPrestitoDa() != null){//GIOCATORE IN PRESTITO
            infoPrestito.setVisible(true);
            inPrestitoDa.setText(calciatore.getInPrestitoDa());
            contrattoFinoAl.setText(df.format(calciatore.getContrattoFinoAl()));
            
        }
        
        for(Statistica s : calciatore.getStatistiche())
            System.out.println(s.getSocieta());
    }

}
