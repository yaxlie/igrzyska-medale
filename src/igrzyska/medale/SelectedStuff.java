/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

/**
 *
 * @author Marcin
 */
public class SelectedStuff {
    private String kraj = null;
    private String dyscyplina = null;
    private int zespol;
    private int zawodnik;
    private int trener;
    private int medal;

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getDyscyplina() {
        return dyscyplina;
    }

    public void setDyscyplina(String dyscyplina) {
        this.dyscyplina = dyscyplina;
    }

    public int getZawodnik() {
        return zawodnik;
    }

    public void setZawodnik(int zawodnik) {
        this.zawodnik = zawodnik;
    }

    public int getTrener() {
        return trener;
    }

    public void setTrener(int trener) {
        this.trener = trener;
    }

    public int getMedal() {
        return medal;
    }

    public void setMedal(int medal) {
        this.medal = medal;
    }

    public int getZespol() {
        return zespol;
    }

    public void setZespol(int zespol) {
        this.zespol = zespol;
    }
    
    
}