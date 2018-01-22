/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import igrzyska.medale.structures.Zawodnik;
import igrzyska.medale.structures.Zespol;
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
   
   private String infoText = "";
   private String errorText = "";
   
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
        connection.setAutoCommit(false);
        System.out.println("Połączono z bazą danych");
        setInfoText("Zalogowano pomyślnie.");
   }
   
   public void save(){
        try {
               connection.commit();
               setInfoText("Zapisano bazę."); 
               getMainWindow().refreshView();
       } catch (SQLException ex) {
           setErrorText("Bład: " + ex.getLocalizedMessage()); 
           getMainWindow().refreshView();
           Logger.getLogger(IgrzyskaSingleton.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public void rollback(){
       try {
           connection.rollback();
           setErrorText("Cofnięto wprowadzone zmiany."); 
           getMainWindow().refreshView();
       } catch (SQLException ex) {
           setErrorText("Bład: " + ex.getLocalizedMessage()); 
           getMainWindow().refreshView();
           Logger.getLogger(IgrzyskaSingleton.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.errorText="";
        this.infoText = infoText;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.infoText="";
        this.errorText = errorText;
    }
   
   public void disconnect(){
       try {
           connection.close();
       } catch (SQLException ex) {
           Logger.getLogger(IgrzyskaSingleton.class.getName()).log(Level.SEVERE, null, ex);
       }
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
            rs = stmt.executeQuery("select id_zaw, imię, nazwisko, count(NVL(id_med, null)) as medale from " + adm + type +
                    " z left join " + adm + "medal med on (z.ID_ZAW = med.ZAWODNIK_ID_ZAW) where kraj like " 
                    +modText(country)+" and z.dyscyplina like "+modText(dyscyplina) + 
                    " group by z.id_zaw, imię, nazwisko order by medale desc");
            while (rs.next()) {
                String nazwa = rs.getString(2) + " " + rs.getString(3) + " <" + Integer.toString(rs.getInt(4)) + ">";
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
    public TableArray getZespoly(){
        return getZespoly("", "", true);
    }
    public TableArray getZespoly(String country, String dyscyplina){
        return getZespoly(country, dyscyplina, false);
    }
    
    public TableArray getZespoly(String country, String dyscyplina, boolean writeId){
        TableArray zespoly = new TableArray();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select numer, kraj_nazwa, dyscyplina_nazwa, count(NVL(id_med, null)) as medale from " + adm + "zespół" +
                    " z left join " + adm + "medal med on (z.numer = med.zespół_numer) where kraj_nazwa like " 
                    +modText(country)+" and z.dyscyplina_nazwa like "+modText(dyscyplina) + 
                    " group by z.numer, kraj_nazwa, dyscyplina_nazwa order by medale desc");
            
            while (rs.next()) {
                String nazwa = writeId?
                        Integer.toString(rs.getInt(1)) + ", " +
                        rs.getString(2) + " : " + rs.getString(3) + " <" + Integer.toString(rs.getInt(4)) + ">":
                        rs.getString(2) + " : " + rs.getString(3) + " <" + Integer.toString(rs.getInt(4)) + ">";
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
            rs = stmt.executeQuery("select id_zaw, imię, nazwisko, ocena, kraj, data_ur, dyscyplina, Trener_id_tren from " + adm + "zawodnik where id_zaw = " + id);
            while (rs.next()) {
                String data = rs.getString(6);
                if(data != null){
                    data = data.split(" ")[0];
                    String[] d = data.split("-");
                    data = d[2] + "-" + d[1] + "-" + d[0];
                }
                else
                    data = "";
                zawodnik = new Zawodnik(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(5),
                        data,
                        rs.getFloat(4),
                        rs.getString(7),
                        rs.getInt(8));
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
   
      public Zespol getZespol(int id){
        Zespol zespol = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select numer, kraj_nazwa, Dyscyplina_nazwa, kapitan_id, trener_id_tren from " 
                    + adm + "zespół where numer = " + id);
            while (rs.next()) {
                zespol = new Zespol(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5));
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
        return zespol;
   }
   
   
   public void dodajZawodnika(String imie, String nazwisko, String zespol, String ocena, String idTrenera, 
           String dyscyplina, String kraj, String data){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            int changes;
            String val = "(null, " + modTextNull(imie) + ", " + modTextNull(nazwisko) + ", " + modTextNull(zespol) + ", " 
                    + modText(ocena) + ", " + modText(idTrenera) + 
                    ", " + modTextNull(dyscyplina) + ", " + modTextNull(kraj) + ", " 
                    + data==""? null: "TO_DATE('" + data + "', 'dd-mm-yyyy')";
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
   
   public boolean updateZawodnik(int id, String imie, String nazwisko, String zespol, int idTrenera, 
           String dyscyplina, String kraj, String data){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String val = "imię = " + modTextNull(imie) + ", nazwisko = " + modTextNull(nazwisko) + 
                    ", Zespół_numer = " + modTextNull(zespol) +  ", Trener_id_tren = null" //+ idTrenera  
                    + ", dyscyplina = " + modTextNull(dyscyplina) + ", kraj = " + modTextNull(kraj) 
                    + ", data_ur = " + "to_date('" + data + "', 'dd-mm-yyyy')" ;
            stmt.executeUpdate("Update "+adm+"zawodnik set " + val + " where id_zaw = " + id);
            setInfoText("Zaktualizowano zawodnika: " + imie + " " + nazwisko); 
            getMainWindow().refreshView();
        } catch (SQLException ex) {
            setErrorText("Bład: " + ex.getLocalizedMessage()); 
            getMainWindow().refreshView();
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
            return true;
        }
   }
   
   public void usunKraj(String kraj){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM kraj WHERE nazwa like '" + kraj + "'");
            setInfoText("Usunięto kraj: " + kraj); 
            getMainWindow().refreshView();
        } catch (SQLException ex) {
            setErrorText("Bład: " + ex.getLocalizedMessage()); 
            getMainWindow().refreshView();
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
            setInfoText("Usunięto zawodnika: " + id); 
            getMainWindow().refreshView();
        } catch (SQLException ex) {
            setErrorText("Bład: " + ex.getLocalizedMessage()); 
            getMainWindow().refreshView();
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* kod obsługi */ }
            }
        }
   }
   
    public void usunZespol(int id){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM zespół WHERE numer = "+ Integer.toString(id));
            setInfoText("Usunięto zespół: " + id); 
            getMainWindow().refreshView();
        } catch (SQLException ex) {
            setErrorText("Bład: " + ex.getLocalizedMessage()); 
            getMainWindow().refreshView();
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
            setInfoText("Usunięto dyscyplinę: " + nazwa); 
            getMainWindow().refreshView();
        } catch (SQLException ex) {
            setErrorText("Bład: " + ex.getLocalizedMessage()); 
            getMainWindow().refreshView();
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { 
                    
                }
            }
        }
   }
      
    public void usunMedal(int id) throws SQLException{
        Statement stmt = null;
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM medal WHERE id_med =" + id);
            stmt.close();
            setInfoText("Usunięto medal: " + id); 
            getMainWindow().refreshView();
            
   }
   
   private String modText(String s){
       return (s == null || "".equals(s))? "'%'" : "'"+s+"'";
   }
   private String modTextNull(String s){
       noSpecChars(s);
       return (s == null || "".equals(s))? null : "'"+s;
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
                    + "((select kraj from " + adm + "zawodnik where id_zaw = ZAWODNIK_ID_ZAW) like " + modText(kraj)
                    + "or" + "(select kraj_nazwa from " + adm + "zespół where numer = zespół_numer) like " + modText(kraj)+")"
                    + " and DYSCYPLINA like " + modText(dyscyplina) +
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
   
    public boolean dodajMedal(String kolor, int id_zesp, int id_zaw, String dysc, String data){ 
        System.out.println("Procedura dodawania medali zesp:" + id_zesp +" zaw:" +  id_zaw);
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
            setInfoText("Dodano medal(e).");
            getMainWindow().refreshView();
           
        } catch (SQLException ex) {
            setErrorText("Bład: " + ex.getLocalizedMessage()); 
            getMainWindow().refreshView();
            System.out.println("Bład wykonania polecenia" + ex.toString());
        }finally{
            return true;
        }   
    }
    
    public String noSpecChars(String s){
        if(s!=null){
            if(s.length()>20)
                s = s.substring(0,20);
            return s.replaceAll("[-+.^:,'\"*&^%$]","");
        }
        else return null;
    }
        public boolean dodajZawodnikaProcedure(String imie, String nazwisko, String data_ur, int trener, String kraj,
                String dyscyplina, String zespol, float ocena){
        try {
            CallableStatement stmt = connection.prepareCall("{call dodaj_zawodnik(?,?,?,?,?,?,?,?)}");
            stmt.setString(1, noSpecChars(imie));
            stmt.setString(2, noSpecChars(nazwisko));
            stmt.setString(3, data_ur);
            if(trener>0)
                stmt.setInt(4, trener);
            else 
                stmt.setNull(4, trener);
            stmt.setString(5, noSpecChars(kraj));
            stmt.setString(6, noSpecChars(dyscyplina));
            stmt.setString(7, zespol);
            stmt.setFloat(8, ocena);
            stmt.execute();
            stmt.close(); 
            setInfoText("Dodano nowego zawodnika: " + imie + " " + nazwisko);

        } catch (SQLException ex) {
            setErrorText("Bład: " + ex.getLocalizedMessage()); 
            getMainWindow().refreshView();
            System.out.println("Bład wykonania polecenia" + ex.toString());
        }finally{
            return true;
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
        return getMedale(id_zaw, false);
    }
    public TableArray getMedale(int id_zaw, boolean zespoly){
        TableArray medale = new TableArray();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = zespoly?
                    stmt.executeQuery("select id_med, kolor, dyscyplina, data_wręczenia from " + adm + "medal where zespół_numer"
                    + " = " + Integer.toString(id_zaw)):
                    stmt.executeQuery("select id_med, kolor, dyscyplina, data_wręczenia from " + adm + "medal where zawodnik_id_zaw"
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