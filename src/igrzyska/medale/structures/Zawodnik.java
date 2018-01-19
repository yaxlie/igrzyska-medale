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
    private String dyscyplina;
    int idTrener;
    
    public Zawodnik(int id, String imie, String nazwisko, String kraj, String dataUr, float rating, String dyscyplina, 
            int idTrener) {
        super(id, imie, nazwisko, kraj, dataUr);
        this.rating = rating;
        this.dyscyplina = dyscyplina;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDyscyplina() {
        return dyscyplina;
    }

    public void setDyscyplina(String dyscyplina) {
        this.dyscyplina = dyscyplina;
    }

    public int getIdTrener() {
        return idTrener;
    }

    public void setIdTrener(int idTrener) {
        this.idTrener = idTrener;
    }
    
    
    
    
}
