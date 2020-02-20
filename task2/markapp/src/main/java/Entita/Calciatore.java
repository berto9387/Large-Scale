/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tony_
 */
public class Calciatore extends InformazioniPrincipali{
   
    private int altezza;    
    private String procuratore;   
    private Date inRosaDa;
    private Date scadenza;
    private int cartelliniGialliStagioneAttuale;
    private int cartelliniRossiStagioneAttuale;
    private int partiteGiocateStagioneAttuale;
    private int assistStagioneAttuale;
    private int minutiGiocatiStagioneAttuale;
    private List<String> altriRuoli;
    private ArrayList<Statistica> statistiche;
    private ArrayList<Trasferimento> trasferimenti;
    private ArrayList<Infortunio> infortuni;

    public Calciatore(int altezza, String procuratore, Date inRosaDa, Date scadenza, int cartelliniGialliStagioneAttuale, int cartelliniRossiStagioneAttuale, int partiteGiocateStagioneAttuale, int assistStagioneAttuale, int minutiGiocatiStagioneAttuale, List<String> altriRuoli, ArrayList<Statistica> statistiche, ArrayList<Trasferimento> trasferimenti, ArrayList<Infortunio> infortuni, String idCalciatore, String nome, String ruoloPrincipale, String squadra, Date dataNascita, int valoreMercato, String nazionalita) {
        super(idCalciatore, nome, ruoloPrincipale, squadra, dataNascita, valoreMercato, nazionalita);
        this.altezza = altezza;
        this.procuratore = procuratore;
        this.inRosaDa = inRosaDa;
        this.scadenza = scadenza;
        this.cartelliniGialliStagioneAttuale = cartelliniGialliStagioneAttuale;
        this.cartelliniRossiStagioneAttuale = cartelliniRossiStagioneAttuale;
        this.partiteGiocateStagioneAttuale = partiteGiocateStagioneAttuale;
        this.assistStagioneAttuale = assistStagioneAttuale;
        this.minutiGiocatiStagioneAttuale = minutiGiocatiStagioneAttuale;
        this.altriRuoli = altriRuoli;
        this.statistiche = statistiche;
        this.trasferimenti = trasferimenti;
        this.infortuni = infortuni;
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

    public int getPartiteGiocateStagioneAttuale() {
        return partiteGiocateStagioneAttuale;
    }

    public void setPartiteGiocateStagioneAttuale(int partiteGiocateStagioneAttuale) {
        this.partiteGiocateStagioneAttuale = partiteGiocateStagioneAttuale;
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

    public List<String> getAltriRuoli() {
        return altriRuoli;
    }

    public void setAltriRuoli(List<String> altriRuoli) {
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

    
}
