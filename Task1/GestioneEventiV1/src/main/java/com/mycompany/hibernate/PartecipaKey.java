/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author berto
 */
@Embeddable
public class PartecipaKey implements Serializable{
    @Column(name="id_Partecipante")
    Long idPartecipante;

    @Column(name="id_Evento");
    Long idEvento;
    
}
