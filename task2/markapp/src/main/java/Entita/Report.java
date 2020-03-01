/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entita;

/**
 *
 * @author tony_
 */
public class Report {
    private String _id;
    private String commento;
    private int rating;//da 0 a 5

    public Report() {
    }

    
    public Report(String _id, String commento, int rating) {
        this._id = _id;
        this.commento = commento;
        this.rating = rating;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
}
