package Datenbank;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Abfragen {

    public static String abfrageHerstellerMeisteVerstoss(){
        String ergebnis = "";
        String query = "SELECT Hersteller, count(*) " +
                "FROM Fahrzeug FULL JOIN Bussgeld ON Fahrzeug.Kennzeichen = Bussgeld.Fahrzeug " +
                "GROUP BY Hersteller " +
                "ORDER BY count(*) DESC " +
                "LIMIT 5";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String Hersteller = rs.getString(1);
                String Anzahl = String.valueOf(rs.getInt(2));
                ergebnis = ergebnis + "Der Hersteller " + Hersteller + " hat " + Anzahl + " Verstöße.\n";
                //System.out.println("Hersteller: " + rs.getString(1));
                //System.out.println("Anzahl: " + rs.getInt(2));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return ergebnis;
    }

    public static String abfrageTagMeisteVerstoss(){
        String ergebnis = "";
        String query = "SELECT DATE(Tageszeit), count(*) " +
                "FROM Fahrzeug FULL JOIN Bussgeld ON Fahrzeug.Kennzeichen = Bussgeld.Fahrzeug " +
                "GROUP BY DATE(Tageszeit) " +
                "ORDER BY count(*) DESC " +
                "LIMIT 1";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                // yyyy-MM-dd 00:00:00.0 umwandeln in dd.MM.YYYY und in ergebnis als String speichern.
                DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                ergebnis = "Tag mit meisten Verstöße ist: " + String.valueOf(sdf.format(rs.getTimestamp(1)));
            }
            //System.out.println(ergebnis);

        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return ergebnis;
    }

    public static String abfrageFahrzeugMeisteVerstoss(){
        String ergebnis = "";
        String query = "SELECT Kennzeichen, count(*) " +
                "FROM Fahrzeug FULL JOIN Bussgeld ON Fahrzeug.Kennzeichen = Bussgeld.Fahrzeug " +
                "GROUP BY Kennzeichen " +
                "ORDER BY count(*) DESC " +
                "LIMIT 1";
        try(
                Connection conn = Datenbankverbindung.connect();
                Statement stmt = conn.createStatement();
        ){
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                ergebnis = "Fahrzeug mit meisten Verstöße ist: " + String.valueOf(rs.getString(1));
            }
            //System.out.println(ergebnis);

        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return ergebnis;
    }

    /* Nur zum Test vom Ergebnis.
    public static void main(String[] args) {
        String ergebnis = abfrageHerstellerMeisteVerstoss();
        System.out.println(ergebnis);
        String ergebnis1 = abfrageTagMeisteVerstoss();
        System.out.println(ergebnis1);
        String ergebnis2 = abfrageFahrzeugMeisteVerstoss();
        System.out.println(ergebnis2);
    }
     */
}
