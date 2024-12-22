package Datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BeispieldatenEinfuegen {

    public static void insertTableVerstoss(List<ElementVerstoss> verstoss){
        // TODO: umstellen auf Einlesen aus Datei mit SELECT-Statement davor.
        String sql = "INSERT INTO Verstoss VALUES(?,?,?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            for(ElementVerstoss ElementVerstoss: verstoss){
                pstmt.setInt(1, ElementVerstoss.getVerstossID());
                pstmt.setString(2, ElementVerstoss.getBeschreibung());
                pstmt.setFloat(3, ElementVerstoss.getStrafe());
                pstmt.setInt(4, ElementVerstoss.getPunkte());
                pstmt.setInt(5, ElementVerstoss.getFahrverbot());

                pstmt.addBatch();
                if(++count % batchSize == 0){
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertTableFahrzeug(List<ElementFahrzeug> fahrzeuge){
        // TODO: umstellen auf Einlesen aus Datei mit SELECT-Statement davor.
        String sql = "INSERT INTO Fahrzeug VALUES(?,?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            for(ElementFahrzeug ElementFahrzeug: fahrzeuge){
                pstmt.setString(1, ElementFahrzeug.getKennzeichen());
                pstmt.setString(2, ElementFahrzeug.getModell());
                pstmt.setString(3, ElementFahrzeug.getHersteller());
                pstmt.setInt(4, ElementFahrzeug.getMotorleistung());

                pstmt.addBatch();
                if(++count % batchSize == 0){
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertTableBussgeld2(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "INSERT INTO Bussgeld (Tageszeit, VerstossID, Fahrzeug)" +
                    "VALUES" +
                    "('2024-12-20 11:05:15', 1, 'KA-DL-5874')," +
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

    public static void insertTableBussgeld(List<ElementBussgeld> bussgeld){
        // TODO: umstellen auf Einlesen aus Datei mit SELECT-Statement davor.
        String sql = "INSERT INTO Verstoss VALUES(?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            for(ElementBussgeld ElementBussgeld: bussgeld){
                pstmt.setString(1, ElementBussgeld.getTageszeit());
                pstmt.setInt(2, ElementBussgeld.getVerstossID());
                pstmt.setString(3, ElementBussgeld.getFahrzeug());

                pstmt.addBatch();
                if(++count % batchSize == 0){
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void fillTableall(){
        List<ElementVerstoss> verstoss = DatenDateiLesen.readVerstoss();
        insertTableVerstoss(verstoss);
        List<ElementFahrzeug> fahrzeuge = DatenDateiLesen.readFahrezuge();
        insertTableFahrzeug(fahrzeuge);
        // TODO: String in Timestamp umwandeln
        //List<ElementBussgeld> bussgeld = DatenDateiLesen.readBussgeld();
        //insertTableBussgeld(bussgeld);
        insertTableBussgeld2();
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
        makeAllNew();
        // deleteTableall();
        //fillTableall();
    }
}
