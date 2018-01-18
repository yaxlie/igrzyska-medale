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
import java.sql.Types;
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
   private static String adm = "admin.";
   
   private String username = "";
   private String password = "";
   
   private SelectedStuff selectedStuff;
   private ArrayList<String> krajList;
   
   private FXMLDocumentController mainWindow;
   
   protected IgrzyskaSingleton() {
        connection = null;
        selectedStuff = new SelectedStuff();
        krajList = new ArrayList<>();
   }
   
   public void connect(String username, String password) throws SQLException{
       
       this.username = username;
       this.password = password;
       
        connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
        connectionProps);
        System.out.println("Połączono z bazą danych");
   }
   
   public static IgrzyskaSingleton getInstance() {
      if(instance == null) {
         instance = new IgrzyskaSingleton();
      }
      return instance;
   }

    public void setSelectedStuff(SelectedStuff selectedStuff) {
        this.selectedStuff = selectedStuff;
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
    
        public boolean existDyscyplina(String name){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select nazwa " +
            "from " + adm + "dyscyplina WHERE UPPER(nazwa) like UPPER('" + name + "')");
            while (rs.next()) {
                return true;
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
        return false;
   }
        
    public void dodajDyscypline(String nazwa, String data_wp, String opis){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            int changes;
            String val = "(" + modTextNull(nazwa) + ", " + modTextNull(data_wp) + ", " + modTextNull(opis) + ",null, null)" ;
            changes = stmt.executeUpdate("INSERT INTO "+adm+"dyscyplina VALUES" + val);
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
   
    public TableArray getZespoly(String country, String dyscyplina){
        TableArray zespoly = new TableArray();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select numer, kraj_nazwa, dyscyplina_nazwa from " + adm + "zespół" + " where kraj_nazwa like " 
                +modText(country)+" and dyscyplina_nazwa like "+modText(dyscyplina));
            while (rs.next()) {
                String nazwa = rs.getString(2) + " : " + rs.getString(3);
                zespoly.getArray().add(nazwa);
                zespoly.getId().add(rs.getInt(1));
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
        return zespoly;
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
            String val = "(null, " + modTextNull(imie) + ", " + modTextNull(nazwisko) + ", " + modTextNull(zespol) + ", " 
                    + modText(ocena) + ", " + modText(idTrenera) + 
                    ", " + modTextNull(dyscyplina) + ", " + modTextNull(kraj) + ", " + modTextNull(data) + ")" ;
            changes = stmt.executeUpdate("INSERT INTO "+adm+"zawodnik VALUES" + val);
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
   
   public void usunKraj(String kraj){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM kraj WHERE nazwa like '" + kraj + "'");
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
   
   public void usunZawodnika(int id){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM zawodnik WHERE id_zaw = "+ Integer.toString(id));
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
   
      public void usunDyscypline(String nazwa){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM dyscyplina WHERE nazwa like '"+ nazwa + "'");
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
      
    public void usunMedal(int id){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM medal WHERE id_med =" + id);
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
        
    public int getKrajScore(String kraj){
        int score = 0;
         try {
           CallableStatement stmt = connection.prepareCall("{? = call getKrajScore(?)}");
           stmt.registerOutParameter(1, Types.INTEGER);
           stmt.setString(2,kraj);
           stmt.execute();
           score = stmt.getInt(1);
           stmt.close();
           
       } catch (SQLException ex) {
             System.out.println("Bład wykonania polecenia" + ex.toString());
       }
       return score;
    } 
    
       public TableArray getMedale(int id_zaw){
        TableArray medale = new TableArray();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select id_med, kolor, dyscyplina, data_wręczenia from " + adm + "medal where zawodnik_id_zaw"
                    + " = " + Integer.toString(id_zaw));
            while (rs.next()) {
                String s = "<<" + rs.getString(2)+ ">>" + " (" + rs.getString(3) + ")     " + rs.getString(4).split(" ")[0];
                medale.getArray().add(s);
                medale.getId().add(rs.getInt(1));
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
        return medale;
   }
   
}