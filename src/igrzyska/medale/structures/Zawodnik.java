/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale.structures;

/**
 *
 * @author Marcin
 */
public class Zawodnik extends Osoba{
    private float rating;
    
    public Zawodnik(int id, String imie, String nazwisko, String kraj, String dataUr, float rating) {
        super(id, imie, nazwisko, kraj, dataUr);
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    
}
