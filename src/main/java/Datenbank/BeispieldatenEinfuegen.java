package Datenbank;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BeispieldatenEinfuegen {

    public static void insertTableVerstoss(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "INSERT INTO Verstoss (VerstossID, Beschreibung, Strafe, Punkte, Fahrverbot)" +
                    "VALUES" +
                    "(1, 'Verstoß gegen die 0,5 Promillegrenze beim 1. Mal', 528.50, 2, 1)," +
                    "(2, 'Verstoß gegen die 0,5 Promillegrenze beim 2. Mal', 1053.50, 2, 3)," +
                    "(3, 'Verstoß gegen die 0,5 Promillegrenze beim 3. Mal', 1578.50, 2, 3)," +
                    "(4, 'Während der Fahrt nicht angeschnallt gewesen', 58.50, NULL, NULL)," +
                    "(5, 'Als Kraftfahrer das Handy am Steuer genutzt', 128.50, 1, NULL)," +
                    "(6, 'Als Kraftfahrer das Handy am Steuer genutzt mit Gefährdung', 178.50, 2, NULL)," +
                    "(7, 'Als Kraftfahrer das Handy am Steuer genutzt mit Sachbeschädigung', 228.50, 2, 1)," +
                    "(8, '50er-Zone: Überschreitung der Höchstgeschwindigkeit außerorts bis 10 km/h', 48.50, NULL, NULL)," +
                    "(9, '50er-Zone: Überschreitung der Höchstgeschwindigkeit außerorts 21 - 25 km/h', 128.50, 1, NULL)," +
                    "(10, '50er-Zone: Überschreitung der Höchstgeschwindigkeit außerorts 41 - 50 km/h', 348.50, 2, 1)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertTableFahrzeug(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "INSERT INTO Fahrzeug (Kennzeichen, Modell, Hersteller, Motorleistung)" +
                    "VALUES" +
                    "('KA-DL-5874', 'Twingo', 'Renault', 75)," +
                    "('KA-FG-1531', 'Tiguan', 'VW', 100)," +
                    "('RA-LM-4563', 'Polo', 'VW', 80)," +
                    "('RA-FD-4213', 'UP', 'VW', 60)," +
                    "('A-GF-2131', 'Corsa', 'Opel', 65)," +
                    "('A-AL-4531', 'Twingo', 'Renault', 75)," +
                    "('GAP-X-435', 'Tiguan', 'VW', 110)," +
                    "('GAP-U-124', 'Tiguan', 'VW', 105)," +
                    "('GAP-F-5727', 'Astra', 'Opel', 90)," +
                    "('GAP-FF-435', 'Twingo', 'Renault', 80)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertTableBussgeld(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "INSERT INTO Bussgeld (Tageszeit, VerstossID, Fahrzeug)" +
                    "VALUES" +
                    "('2024-01-20 11:05:15', 1, 'KA-DL-5874')," +
                    "('2023-12-04 16:54:45', 2, 'RA-FD-4213')," +
                    "('2024-12-20 05:23:27', 5, 'A-GF-2131')," +
                    "('2024-07-09 08:34:48', 3, 'KA-FG-1531')," +
                    "('2022-11-13 23:23:06', 2, 'A-AL-4531')," +
                    "('2023-05-16 13:50:13', 5, 'RA-FD-4213')," +
                    "('2021-09-01 06:47:40', 10, 'GAP-X-435')," +
                    "('2020-07-03 09:59:59', 9, 'RA-FD-4213')," +
                    "('2022-11-06 05:54:44', 2, 'GAP-F-5727')," +
                    "('2024-02-29 04:12:57', 7, 'A-GF-2131')," +
                    "('2019-02-04 10:56:34', 4, 'GAP-U-124')," +
                    "('2018-02-08 21:32:28', 5, 'KA-FG-1531')," +
                    "('2023-12-03 01:50:17', 10, 'A-AL-4531')," +
                    "('2020-03-15 06:51:39', 3, 'GAP-U-124')," +
                    "('2024-07-19 07:21:41', 8, 'GAP-FF-435')," +
                    "('2021-09-26 13:21:03', 6, 'RA-LM-4563')," +
                    "('2023-04-28 14:21:16', 1, 'GAP-U-124')," +
                    "('2024-08-21 22:21:04', 8, 'RA-LM-4563')," +
                    "('2022-03-09 15:54:27', 9, 'GAP-U-124')," +
                    "('2023-01-01 08:51:33', 7, 'RA-LM-4563')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void fillTableall(){
        insertTableVerstoss();
        insertTableFahrzeug();
        insertTableBussgeld();
    }

    public static void deleteTableall(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "DELETE FROM Bussgeld;" +
                    "DELETE FROM Fahrzeug;" +
                    "DELETE FROM Verstoss";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void makeAllNew(){
        Tabellenerstellen.dropTableall();
        Tabellenerstellen.createTableall();
        fillTableall();
    }


    public static void main(String[] args) {
        // makeAllNew();
        // deleteTableall();
        fillTableall();
    }
}
