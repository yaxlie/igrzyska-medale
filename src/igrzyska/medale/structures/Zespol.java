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
public class Zespol {
    private int numer;
    private String kraj;
    private String dyscyplina;
    private int kapitan;
    private int trener;
    
    public Zespol(){}
    
    public Zespol(int numer, String kraj, String dyscyplina, int kapitan, int trener){
        this.numer = numer; 
        this.kraj = kraj;
        this.dyscyplina = dyscyplina;
        this.kapitan = kapitan;
        this.trener = trener;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

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

    public int getKapitan() {
        return kapitan;
    }

    public void setKapitan(int kapitan) {
        this.kapitan = kapitan;
    }

    public int getTrener() {
        return trener;
    }

    public void setTrener(int trener) {
        this.trener = trener;
    }
    
    
}
