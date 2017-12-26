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
public class Osoba {
    private int id;
    private String imie;
    private String nazwisko;
    private String kraj;
    private String dataUr;
    
    public Osoba(int id, String imie, String nazwisko, String kraj, String dataUr){
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.kraj = kraj;
        this.dataUr = dataUr;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getDataUr() {
        return dataUr;
    }

    public void setDataUr(String dataUr) {
        this.dataUr = dataUr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
