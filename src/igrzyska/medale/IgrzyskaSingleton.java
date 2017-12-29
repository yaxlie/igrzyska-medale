/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import igrzyska.medale.structures.Zawodnik;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcin
 */
public class IgrzyskaSingleton {
   private static IgrzyskaSingleton instance = null;
   private Connection connection;
   private Properties connectionProps;
   private static String adm = "inf127301.";
   
   private SelectedStuff selectedStuff;
   private ArrayList<String> krajList;
   
   private FXMLDocumentController mainWindow;
   
   protected IgrzyskaSingleton() {
        connection = null;
        selectedStuff = new SelectedStuff();
        krajList = new ArrayList<>();
        connectionProps = new Properties();
        connectionProps.put("user", "inf127301");
        connectionProps.put("password", "inf127301");
        
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
            connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(IgrzyskaMedale.class.getName()).log(Level.SEVERE,
            "nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        } 
   }
   public static IgrzyskaSingleton getInstance() {
      if(instance == null) {
         instance = new IgrzyskaSingleton();
      }
      return instance;
   }
   
   public ArrayList<String> getCountries(){
        ArrayList<String> countries = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select nazwa " +
            "from " + adm + "kraj");
            while (rs.next()) {
                String nazwa = rs.getString(1);
                countries.add(nazwa);
            }
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        krajList = countries;
        return countries;
   }
   
      public ArrayList<String> getDyscypliny(){
        ArrayList<String> dyscypliny = new ArrayList<>();
        dyscypliny.add("");
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select nazwa " +
            "from " + adm + "dyscyplina");
            while (rs.next()) {
                String nazwa = rs.getString(1);
                dyscypliny.add(nazwa);
            }
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return dyscypliny;
   }
      
    public ArrayList<String> getDyscypliny(String name){
        ArrayList<String> dysc = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select nazwa " +
            "from " + adm + "dyscyplina WHERE ROWNUM <= 3 and UPPER(nazwa) like UPPER('%" + name + "%')");
            while (rs.next()) {
                String nazwa = rs.getString(1);
                dysc.add(nazwa);
            }
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return dysc;
   }
    
    public ArrayList<String> getOsoby(String table){
        ArrayList<String> dysc = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * " +
            "from " + adm + table);
            while (rs.next()) {
                String nazwa = rs.getString(1);
                nazwa += ", " + rs.getString(2);
                nazwa += " " + rs.getString(3);
                nazwa += ", " + rs.getString(8);
                dysc.add(nazwa);
            }
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return dysc;
   }
   
   private TableArray getOsoby(String type, String country, String dyscyplina){
        TableArray osoby = new TableArray();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select id_zaw, imię, nazwisko from " + adm + type + " where kraj like " 
                +modText(country)+" and dyscyplina like "+modText(dyscyplina));
            while (rs.next()) {
                String nazwa = rs.getString(2) + " " + rs.getString(3);
                osoby.getArray().add(nazwa);
                osoby.getId().add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return osoby;
   }
   
   public TableArray getZawodnicy(String country, String dyscyplina){
       return getOsoby("zawodnik", country, dyscyplina);
   }
   public TableArray getZawodnicy(String country){
       return getOsoby("zawodnik", country, "%");
   }
   public TableArray getTrenerzy(String country, String dyscyplina){
       return getOsoby("trener", country, dyscyplina);
   }
   public TableArray getTrenerzy(String country){
       return getOsoby("trener", country, "%");
   }

    public SelectedStuff getSelectedStuff() {
        return selectedStuff;
    }
   
   public Zawodnik getZawodnik(int id){
        Zawodnik zawodnik = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select id_zaw, imię, nazwisko, ocena, kraj, data_ur from " + adm + "zawodnik where id_zaw = " + id);
            while (rs.next()) {
                zawodnik = new Zawodnik(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getFloat(4));
            }
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return zawodnik;
   }
   
   
   public void dodajZawodnika(String imie, String nazwisko, String zespol, String ocena, String idTrenera, 
           String dyscyplina, String kraj, String data){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            int changes;
            String val = "(null, " + modText(imie) + ", " + modText(nazwisko) + ", " + modText(zespol) + ", " 
                    + modText(ocena) + ", " + modText(idTrenera) + 
                    ", " + modText(dyscyplina) + ", " + modText(kraj) + ", " + modText(data) + ")" ;
            changes = stmt.executeUpdate("INSERT INTO zawodnik VALUES" + val);
            System.out.println("Wstawiono " + changes + " krotek."); 
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
   }
   
   private String modText(String s){
       return (s == null || "".equals(s))? "'%'" : "'"+s+"'";
   }
   private String modTextNull(String s){
       return (s == null || "".equals(s))? null : "'"+s+"'";
   }

    public FXMLDocumentController getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(FXMLDocumentController mainWindow) {
        this.mainWindow = mainWindow;
    }

    public ArrayList<String> getKrajList() {
        return krajList;
    }

    public int countMedale(String kraj, String dyscyplina, String kolor) {
        int c = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select count (*) from " + adm + "medal where"
                    + "(select kraj from " + adm + "zawodnik where id_zaw = ZAWODNIK_ID_ZAW) like " + modText(kraj) +
                    " and DYSCYPLINA like " + modText(dyscyplina) +
                    " and KOLOR like " + modText(kolor));
            while (rs.next()) {
                c = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
        return c;
   }
   
    public void dodajMedal(String kolor, int id_zesp, int id_zaw, String dysc, String data){
        try {
            CallableStatement stmt = connection.prepareCall("{call dodaj_medal(?,?,?,?,?)}");
            stmt.setString(1, kolor);
            if(id_zesp>0)
                stmt.setInt(2, id_zesp);
            else
                stmt.setNull(2, id_zesp);
            if(id_zaw>0)
                stmt.setInt(3, id_zaw);
            else
                stmt.setNull(3, id_zaw);
            stmt.setString(4, dysc);
            stmt.setString(5, data);
            stmt.execute();
            stmt.close(); 

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        }   
    }
    
        public void dodajZawodnikaProcedure(String imie, String nazwisko, String data_ur, int trener, String kraj,
                String dyscyplina, String zespol, float ocena){
        try {
            CallableStatement stmt = connection.prepareCall("{call dodaj_zawodnik(?,?,?,?,?,?,?,?)}");
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, data_ur);
            if(trener>0)
                stmt.setInt(4, trener);
            else 
                stmt.setNull(4, trener);
            stmt.setString(5, kraj);
            stmt.setString(6, dyscyplina);
            stmt.setString(7, zespol);
            stmt.setFloat(8, ocena);
            stmt.execute();
            stmt.close(); 

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        }   
    }
   
}