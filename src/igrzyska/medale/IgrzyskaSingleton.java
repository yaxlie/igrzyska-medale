/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import igrzyska.medale.structures.Zawodnik;
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
   
   protected IgrzyskaSingleton() {
        connection = null;
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
            "from kraj");
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
        return countries;
   }
   
   private TableArray getOsoby(String type, String country, String dyscyplina){
        TableArray osoby = new TableArray();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select id_zaw, imię, nazwisko from " + type + " where kraj like '" 
                +country+"' and dyscyplina like '"+dyscyplina+"'");
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
   
   public Zawodnik getZawodnik(int id){
        Zawodnik zawodnik = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select id_zaw, imię, nazwisko, ocena, kraj, data_ur from zawodnik where id_zaw = " + id);
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
}