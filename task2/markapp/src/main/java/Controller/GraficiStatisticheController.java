/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Dao.StatisticheMongoDataAccess.getStatistichePerIstogramma;
import static Dao.StatisticheMongoDataAccess.statisticaAgregataGiocatoreSocieta;
import static Dao.StatisticheMongoDataAccess.statisticaAgregataSocieta;
import Entita.Calciatore;
import Entita.Societa;
import Entita.Statistica;
import Model.ValoriStatisticheDiagrammaTorta;
import Model.ValoriStatisticheIstogramma;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author berto
 */
public class GraficiStatisticheController extends GeneralSchedaGiocatoreController {

    
    ObservableList<String> stagioni=null;
    ObservableList<String> competizioni=null;
    
    
    @FXML
    private VBox torta1;

    @FXML
    private VBox torta2;

    @FXML
    private VBox torta3;

    @FXML
    private VBox istogramma1;

    @FXML
    private VBox istogramma2;

    @FXML
    private VBox istogramma3;

    @FXML
    private ChoiceBox<String> stagioneTorta;

    @FXML
    private ChoiceBox<String> stagioneIstogramma;

    @FXML
    private ChoiceBox<String> competizioneIstogramma;

    public GraficiStatisticheController(Calciatore calciatore) {
        super(calciatore);
    }

    private void aggiungiTorta(VBox torta,String societa, double datiCalciatore, double datiSocieta, String title){
        String nome=calciatore.getNome();
        
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data(nome, datiCalciatore),
                new PieChart.Data(societa, datiSocieta));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(title);
        torta.getChildren().clear();
        torta.getChildren().add(chart);
    }
    private void aggiungiIstogramma(VBox istogramma,List<ValoriStatisticheIstogramma> dati,String title,
                                    String xAsse,String yAsse,int valoreIndividuale){
              
              
        CategoryAxis x    = new CategoryAxis();
        x.setLabel(xAsse);
        
        NumberAxis y = new NumberAxis();
        y.setLabel(yAsse);
        
        BarChart bc = new BarChart(x, y);
        
        XYChart.Series ds = new XYChart.Series();
        ds.setName(title);
        int cont=0;
        int trovato=0;
        for(ValoriStatisticheIstogramma aux : dati){
            if(valoreIndividuale==aux.getvaloreStatistica())
                trovato=cont;
            ds.getData().add(new XYChart.Data(Integer.toString(aux.getvaloreStatistica()), aux.getnumeroGiocatoriPerStatistica()));
            cont++;
        }
        istogramma.getChildren().clear();
        bc.getData().add(ds);
        Node n = bc.lookup(".data"+trovato+".chart-bar");
        n.setStyle("-fx-bar-fill: yellow");
        istogramma.getChildren().add(bc);
    }
    @FXML
    void creaDiagrammaInstogramma(ActionEvent event) {
        String calciatoreId=calciatore.getIdCalciatore();
        String posizionePrincipale=calciatore.getRuoloPrincipale();
        String stagione=stagioneIstogramma.getValue();
        String competizione=competizioneIstogramma.getValue();
        List<ValoriStatisticheIstogramma> ammonizioni=null;
        List<ValoriStatisticheIstogramma> espulsioni=null;
        List<ValoriStatisticheIstogramma> reti=null;
        List<ValoriStatisticheIstogramma> retiSubiti=null;
        List<ValoriStatisticheIstogramma> assist=null;
        try {
            ammonizioni=getStatistichePerIstogramma(posizionePrincipale,
                stagione,competizione,calciatoreId,"ammonizione");
            espulsioni=getStatistichePerIstogramma(posizionePrincipale,
                stagione,competizione,calciatoreId,"espulsioni");
            reti=getStatistichePerIstogramma(posizionePrincipale,
                stagione,competizione,calciatoreId,"reti");
            retiSubiti=getStatistichePerIstogramma(posizionePrincipale,
                stagione,competizione,calciatoreId,"retiSubite");
            assist=getStatistichePerIstogramma(posizionePrincipale,
                stagione,competizione,calciatoreId,"assist");
        } catch (Exception e) {
            infoLabel.setText("Non è stato possibile recuperare i dati!");
            e.printStackTrace();
            return;
        }
        int numeroAmmonizioni=0;
        int numeroEspolsioni=0;
        int numeroReti=0;
        int numeroRetiSubite=0;
        int numeroAssit=0;
        List<Statistica> stat=calciatore.getStatistiche();
        
        for(Statistica aux : stat){
            if(aux.getCompetizione().equals(competizione) && aux.getStagione().equals(stagione)){
                if(posizionePrincipale.equals("portiere") || posizionePrincipale.equals("Portiere")){
                    numeroAmmonizioni=aux.getAmmonizioni();
                    numeroEspolsioni=aux.getEspulsioni();
                    numeroRetiSubite=aux.getRetiSubite();
                }else{
                    numeroAmmonizioni=aux.getAmmonizioni();
                    numeroAssit=aux.getAssit();
                    numeroReti=aux.getGoal();
                }
            }
        }
        
        for(ValoriStatisticheIstogramma aux : espulsioni){
            System.out.println(aux.getvaloreStatistica()+"__"+aux.getnumeroGiocatoriPerStatistica());
        }
        if(calciatore.getRuoloPrincipale().equals("portiere") || calciatore.getRuoloPrincipale().equals("Portiere")){
            aggiungiIstogramma(istogramma1, ammonizioni, "Ammonizioni", "numero ammonizioni", "numero calciatori",numeroAmmonizioni);
            aggiungiIstogramma(istogramma2, espulsioni, "Espulsioni", "numero espulsioni", "numero calciatori",numeroEspolsioni);
            aggiungiIstogramma(istogramma3, retiSubiti, "Reti subite", "numeri reti subite", "numero calciatori",numeroRetiSubite);
        }
        else{
            aggiungiIstogramma(istogramma1, reti, "Reti", "numero goal", "numero calciatori",numeroReti);
            aggiungiIstogramma(istogramma2, assist, "Assit", "numero assist", "numero calciatori",numeroAssit);
            aggiungiIstogramma(istogramma3, ammonizioni, "Ammonizioni", "numero ammonizioni", "numero calciatori",numeroAmmonizioni);
        }
        
                    
    }

    @FXML
    void creaDiagrammaTorta(ActionEvent event) {
        String calciatoreId=calciatore.getIdCalciatore();
        ArrayList<Statistica> societaList=calciatore.getStatistiche();
        String societa="altri";
        for(Statistica aux :societaList){
            if(aux.getStagione().equals(stagioneTorta.getValue())){
                societa=aux.getSocieta();
                break;
            }
        }
        String stagione=stagioneTorta.getValue();
        ValoriStatisticheDiagrammaTorta datiCalciatore=null;
        ValoriStatisticheDiagrammaTorta datiSocieta=null;
        try {
            datiCalciatore=statisticaAgregataGiocatoreSocieta(societa,stagione,calciatoreId);
            datiSocieta=statisticaAgregataSocieta(societa, stagione, calciatoreId);
        } catch (Exception ex) {
            infoLabel.setText("Non è stato possibile recuperare i dati!");
            ex.printStackTrace();
            return;
        }
        if(calciatore.getRuoloPrincipale().equals("portiere") || calciatore.getRuoloPrincipale().equals("Portiere")){
           aggiungiTorta(torta1,societa, datiCalciatore.getNumeroCartellini(),datiSocieta.getNumeroCartellini(), "Cartellini presi");
           aggiungiTorta(torta2,societa, datiCalciatore.getNumeroGoalSubiti(),datiSocieta.getNumeroGoalSubiti(), "Goal subiti");
        } else{
            aggiungiTorta(torta1,societa,datiCalciatore.getNumeroReti(),datiSocieta.getNumeroReti(), "Goal fatti");
            aggiungiTorta(torta2,societa, datiCalciatore.getNumeroAssist(),datiSocieta.getNumeroAssist(), "Assist fatti");
            aggiungiTorta(torta3,societa, datiCalciatore.getNumeroCartellini(),datiSocieta.getNumeroCartellini(), "Cartellini presi");
        }
        
        
        
    }

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);;
        HashSet<String> stagioniList = new HashSet<>();
        HashSet<String> competizioneList = new HashSet<>();
        for(Statistica aux : calciatore.getStatistiche()){
            String appoggioSta=aux.getStagione();
            String appoggioCom=aux.getCompetizione();
            stagioniList.add(appoggioSta);
            competizioneList.add(appoggioCom);
        }
        this.stagioni=FXCollections.observableArrayList(stagioniList);
        stagioneTorta.setItems(stagioni);
        stagioneIstogramma.setItems(stagioni);
        this.competizioni=FXCollections.observableArrayList(competizioneList);
        competizioneIstogramma.setItems(competizioni);
    }  
    
    
    
}
