/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.image.ImageView;

/**
 *
 * @author tony_
 */
public class Calciatore extends InformazioniPrincipali{
   
    private String image;
    private int altezza; 
    private String luogoNascita;
    private String piede;
    private String procuratore;   
    private Date inRosaDa;
    private Date scadenza;
    //Solo per giocatori in prestito
    private String inPrestitoDa;
    private Date contrattoFinoAl;
    //
    private ArrayList<String> altriRuoli = new ArrayList<>();
    private ArrayList<Statistica> statistiche = new ArrayList<>();;
    private ArrayList<Trasferimento> trasferimenti = new ArrayList<>();;
    private ArrayList<Infortunio> infortuni = new ArrayList<>();;
    private int goalStagioneAttuale;
    private int cartelliniGialliStagioneAttuale;
    private int cartelliniRossiStagioneAttuale;
    private int minutiGiocatiStagioneAttuale;
    //Solo per i calciatori
    private int assistStagioneAttuale;
    //
    //Solo per portieri
    private int partiteNoGoalStagioneAttuale;
    private int goalSubitiStagioneAttuale;
    //
    
    
    
    //Costruttore per calciatore
    public Calciatore(int altezza, String procuratore, Date inRosaDa, Date scadenza, int cartelliniGialliStagioneAttuale, int cartelliniRossiStagioneAttuale, int partiteGiocateStagioneAttuale, int assistStagioneAttuale, int minutiGiocatiStagioneAttuale, ArrayList<String> altriRuoli, ArrayList<Statistica> statistiche, ArrayList<Trasferimento> trasferimenti, ArrayList<Infortunio> infortuni, String idCalciatore, String nome, String ruoloPrincipale, String squadra, Date dataNascita, String linkFoto, String nazionalita) {
        super(idCalciatore, nome, ruoloPrincipale, squadra, dataNascita, linkFoto, nazionalita);
        this.altezza = altezza;
        this.procuratore = procuratore;
        this.inRosaDa = inRosaDa;
        this.scadenza = scadenza;
        this.cartelliniGialliStagioneAttuale = cartelliniGialliStagioneAttuale;
        this.cartelliniRossiStagioneAttuale = cartelliniRossiStagioneAttuale;
        this.assistStagioneAttuale = assistStagioneAttuale;
        this.minutiGiocatiStagioneAttuale = minutiGiocatiStagioneAttuale;
        this.altriRuoli = altriRuoli;
        this.statistiche = statistiche;
        this.trasferimenti = trasferimenti;
        this.infortuni = infortuni;
    }

    //Costruttore portiere
    public Calciatore(String image, int altezza, String luogoNascita, String piede, String procuratore, Date inRosaDa, Date scadenza, int goalStagioneAttuale, int cartelliniGialliStagioneAttuale, int cartelliniRossiStagioneAttuale, int minutiGiocatiStagioneAttuale, int partiteNoGoalStagioneAttuale, int goalSubitiStagioneAttuale, ArrayList<String> altriRuoli, ArrayList<Statistica> statistiche, ArrayList<Trasferimento> trasferimenti, ArrayList<Infortunio> infortuni, String idCalciatore, String nome, String ruoloPrincipale, String squadra, Date dataNascita, String linkFoto, String nazionalita) {
        super(idCalciatore, nome, ruoloPrincipale, squadra, dataNascita, linkFoto, nazionalita);
        this.image = image;
        this.altezza = altezza;
        this.luogoNascita = luogoNascita;
        this.piede = piede;
        this.procuratore = procuratore;
        this.inRosaDa = inRosaDa;
        this.scadenza = scadenza;
        this.goalStagioneAttuale = goalStagioneAttuale;
        this.cartelliniGialliStagioneAttuale = cartelliniGialliStagioneAttuale;
        this.cartelliniRossiStagioneAttuale = cartelliniRossiStagioneAttuale;
        this.minutiGiocatiStagioneAttuale = minutiGiocatiStagioneAttuale;
        this.partiteNoGoalStagioneAttuale = partiteNoGoalStagioneAttuale;
        this.goalSubitiStagioneAttuale = goalSubitiStagioneAttuale;
        this.altriRuoli = altriRuoli;
        this.statistiche = statistiche;
        this.trasferimenti = trasferimenti;
        this.infortuni = infortuni;
    }

    public Calciatore() {
        
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getPiede() {
        return piede;
    }

    public void setPiede(String piede) {
        this.piede = piede;
    }
    
     public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public String getProcuratore() {
        return procuratore;
    }

    public void setProcuratore(String procuratore) {
        this.procuratore = procuratore;
    }

    public Date getInRosaDa() {
        return inRosaDa;
    }

    public void setInRosaDa(Date inRosaDa) {
        this.inRosaDa = inRosaDa;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public String getInPrestitoDa() {
        return inPrestitoDa;
    }

    public void setInPrestitoDa(String inPrestitoDa) {
        this.inPrestitoDa = inPrestitoDa;
    }

    public Date getContrattoFinoAl() {
        return contrattoFinoAl;
    }

    public void setContrattoFinoAl(Date contrattoFinoAl) {
        this.contrattoFinoAl = contrattoFinoAl;
    }

    public int getGoalStagioneAttuale() {
        return goalStagioneAttuale;
    }

    public void setGoalStagioneAttuale(int goalStagioneAttuale) {
        this.goalStagioneAttuale = goalStagioneAttuale;
    }

    public int getCartelliniGialliStagioneAttuale() {
        return cartelliniGialliStagioneAttuale;
    }

    public void setCartelliniGialliStagioneAttuale(int cartelliniGialliStagioneAttuale) {
        this.cartelliniGialliStagioneAttuale = cartelliniGialliStagioneAttuale;
    }

    public int getCartelliniRossiStagioneAttuale() {
        return cartelliniRossiStagioneAttuale;
    }

    public void setCartelliniRossiStagioneAttuale(int cartelliniRossiStagioneAttuale) {
        this.cartelliniRossiStagioneAttuale = cartelliniRossiStagioneAttuale;
    }

    public int getPartiteNoGoalStagioneAttuale() {
        return partiteNoGoalStagioneAttuale;
    }

    public void setPartiteNoGoalStagioneAttuale(int partiteNoGoalStagioneAttuale) {
        this.partiteNoGoalStagioneAttuale = partiteNoGoalStagioneAttuale;
    }

    public int getGoalSubitiStagioneAttuale() {
        return goalSubitiStagioneAttuale;
    }

    public void setGoalSubitiStagioneAttuale(int goalSubitiStagioneAttuale) {
        this.goalSubitiStagioneAttuale = goalSubitiStagioneAttuale;
    }

    public int getAssistStagioneAttuale() {
        return assistStagioneAttuale;
    }

    public void setAssistStagioneAttuale(int assistStagioneAttuale) {
        this.assistStagioneAttuale = assistStagioneAttuale;
    }

    public int getMinutiGiocatiStagioneAttuale() {
        return minutiGiocatiStagioneAttuale;
    }

    public void setMinutiGiocatiStagioneAttuale(int minutiGiocatiStagioneAttuale) {
        this.minutiGiocatiStagioneAttuale = minutiGiocatiStagioneAttuale;
    }

    public ArrayList<String> getAltriRuoli() {
        return altriRuoli;
    }

    public void setAltriRuoli(ArrayList<String> altriRuoli) {
        this.altriRuoli = altriRuoli;
    }

    public ArrayList<Statistica> getStatistiche() {
        return statistiche;
    }

    public void setStatistiche(ArrayList<Statistica> statistiche) {
        this.statistiche = statistiche;
    }

    public ArrayList<Trasferimento> getTrasferimenti() {
        return trasferimenti;
    }

    public void setTrasferimenti(ArrayList<Trasferimento> trasferimenti) {
        this.trasferimenti = trasferimenti;
    }

    public ArrayList<Infortunio> getInfortuni() {
        return infortuni;
    }

    public void setInfortuni(ArrayList<Infortunio> infortuni) {
        this.infortuni = infortuni;
    }

    public void addTrasferimento(Trasferimento nuovoTrasferimento) {
        this.trasferimenti.add(nuovoTrasferimento);
    }

    public void addStatistica(Statistica nuovaStatistica) {
        this.statistiche.add(nuovaStatistica);
    }

    public void addRuolo(String nuovoRuolo) {
        this.altriRuoli.add(nuovoRuolo);
    }

    public void addInfortunio(Infortunio nuovoInfortunio) {
        this.infortuni.add(nuovoInfortunio);
    }
    
    
    

    
}
