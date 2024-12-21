package Datenbank;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Tabellenerstellen {

    public static void createTableVerstoss(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "CREATE TABLE IF NOT EXISTS Verstoss" +
                    "(VerstossID integer NOT NULL," +
                    "Beschreibung text NOT NULL," +
                    "Strafe real NOT NULL," +
                    "Punkte integer NULL," +
                    "Fahrverbot integer NULL," +
                    "PRIMARY KEY(VerstossID))";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createTableFahrzeug(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "CREATE TABLE IF NOT EXISTS Fahrzeug" +
                    "(Kennzeichen varchar(8) NOT NULL," +
                    "Modell varchar(30) NOT NULL," +
                    "Hersteller varchar(20) NOT NULL," +
                    "Motorleistung integer NOT NULL," +
                    "PRIMARY KEY(Kennzeichen))";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createTableBussgeld(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "CREATE TABLE IF NOT EXISTS Bussgeld" +
                    "(ID serial NOT NULL," +
                    "Tageszeit date NOT NULL," +
                    "VerstossID integer NOT NULL," +
                    "Fahrzeug varchar(8) NOT NULL," +
                    "PRIMARY KEY(ID)," +
                    "UNIQUE(Tageszeit, VerstossID, Fahrzeug)," +
                    "CONSTRAINT fk_Verstoss FOREIGN KEY (VerstossID) REFERENCES Verstoss (VerstossID)," +
                    "CONSTRAINT fk_Fahrzeug FOREIGN KEY (Fahrzeug) REFERENCES Fahrzeug (Kennzeichen))";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void createTableall(){
        createTableVerstoss();
        createTableFahrzeug();
        createTableBussgeld();
    }

    public static void main(String[] args) {
        createTableall();
    }
}
