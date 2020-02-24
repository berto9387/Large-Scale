/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

import java.util.Date;



/**
 *
 * @author tony_
 */
public class InformazioniPrincipali {
    private String idCalciatore;
    private String nome;
    private String ruoloPrincipale;
    private String squadra;
    private Date dataNascita;
    private int valoreMercato;
    private String linkFoto;
    private String nazionalita;
    private int giudizioDirigenza;
    private int giudizioAllenatore;
    private String propostoDa;
    private Report report;

    public InformazioniPrincipali(String idCalciatore, String nome, String ruoloPrincipale, String squadra, Date dataNascita, int valoreMercato, String nazionalita, int giudizioDirigenza, int giudizioAllenatore, String propostoDa, Report report) {
        this.idCalciatore = idCalciatore;
        this.nome = nome;
        this.ruoloPrincipale = ruoloPrincipale;
        this.squadra = squadra;
        this.dataNascita = dataNascita;
        this.valoreMercato = valoreMercato;
        this.nazionalita = nazionalita;
        this.giudizioDirigenza = giudizioDirigenza;
        this.giudizioAllenatore = giudizioAllenatore;
        this.propostoDa = propostoDa;
        this.report = report;
    }

    public InformazioniPrincipali(String idCalciatore, String nome, String ruoloPrincipale, String squadra, Date dataNascita, String linkFoto, String nazionalita) {
        this.idCalciatore = idCalciatore;
        this.nome = nome;
        this.ruoloPrincipale = ruoloPrincipale;
        this.squadra = squadra;
        this.dataNascita = dataNascita;
        this.linkFoto = linkFoto;
        this.nazionalita = nazionalita;
    }

    public InformazioniPrincipali() {
    }

    
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRuoloPrincipale() {
        return ruoloPrincipale;
    }

    public void setRuoloPrincipale(String ruoloPrincipale) {
        this.ruoloPrincipale = ruoloPrincipale;
    }

    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public int getValoreMercato() {
        return valoreMercato;
    }

    public void setValoreMercato(int valoreMercato) {
        this.valoreMercato = valoreMercato;
    }

    

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public int getGiudizioDirigenza() {
        return giudizioDirigenza;
    }

    public void setGiudizioDirigenza(int giudizioDirigenza) {
        this.giudizioDirigenza = giudizioDirigenza;
    }

    public int getGiudizioAllenatore() {
        return giudizioAllenatore;
    }

    public void setGiudizioAllenatore(int giudizioAllenatore) {
        this.giudizioAllenatore = giudizioAllenatore;
    }

    public String getIdCalciatore() {
        return idCalciatore;
    }

    public void setIdCalciatore(String idCalciatore) {
        this.idCalciatore = idCalciatore;
    }

    public String getPropostoDa() {
        return propostoDa;
    }

    public void setPropostoDa(String propostoDa) {
        this.propostoDa = propostoDa;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getLinkFoto() {
        return linkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        this.linkFoto = linkFoto;
    }
    
    
}
