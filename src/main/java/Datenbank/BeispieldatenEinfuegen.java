package Datenbank;

import java.sql.*;
import java.util.List;

public class BeispieldatenEinfuegen {

    public static void insertTableVerstoss(List<ElementVerstoss> verstoss){
        String sqlSelect = "SELECT count(*) FROM Verstoss WHERE" +
                " Beschreibung = ? AND Strafe = ? AND Punkte = ? AND Fahrverbot = ?";
        String sqlInsert = "INSERT INTO Verstoss VALUES(?,?,?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementVerstoss ElementVerstoss: verstoss){
                // SELECT
                pstmtSelect.setString(1, ElementVerstoss.getBeschreibung());
                pstmtSelect.setFloat(2, ElementVerstoss.getStrafe());
                pstmtSelect.setInt(3, ElementVerstoss.getPunkte());
                pstmtSelect.setInt(4, ElementVerstoss.getFahrverbot());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                   result = Integer.parseInt(resultSelect.getString(1));
                   //System.out.println(result);
                }
                if(result == 0) {
                    // INSERT
                    pstmtInsert.setInt(1, ElementVerstoss.getVerstossID());
                    pstmtInsert.setString(2, ElementVerstoss.getBeschreibung());
                    pstmtInsert.setFloat(3, ElementVerstoss.getStrafe());
                    pstmtInsert.setInt(4, ElementVerstoss.getPunkte());
                    pstmtInsert.setInt(5, ElementVerstoss.getFahrverbot());
                    pstmtInsert.addBatch();
                    if (++count % batchSize == 0) {
                        pstmtInsert.executeBatch();
                    }
                }
            }
            pstmtInsert.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean insertTableFahrzeug(List<ElementFahrzeug> fahrzeuge){
        Boolean insert = false;
        String sqlSelect = "SELECT count(*) FROM Fahrzeug WHERE Kennzeichen = ?";
        String sqlInsert = "INSERT INTO Fahrzeug VALUES(?,?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementFahrzeug ElementFahrzeug: fahrzeuge){
                // SELECT
                pstmtSelect.setString(1, ElementFahrzeug.getKennzeichen());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                    result = Integer.parseInt(resultSelect.getString(1));
                    //System.out.println(result);
                }
                if(result == 0) {
                    // INSERT
                    pstmtInsert.setString(1, ElementFahrzeug.getKennzeichen());
                    pstmtInsert.setString(2, ElementFahrzeug.getModell());
                    pstmtInsert.setString(3, ElementFahrzeug.getHersteller());
                    pstmtInsert.setInt(4, ElementFahrzeug.getMotorleistung());
                    pstmtInsert.addBatch();
                    if (++count % batchSize == 0) {
                        pstmtInsert.executeBatch();
                    }
                    insert = true;
                }
            }
            pstmtInsert.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return insert;
    }

    // Nicht verwendete Methode als Beispiel, wie Daten hardcoded importiert werden können ohne Select davor.
    public static void insertTableBussgeld2(){
        try{
            Connection conn = Datenbankverbindung.connect();
            String query = "DELETE FROM Bussgeld;" +
                    "INSERT INTO Bussgeld (Tageszeit, VerstossID, Fahrzeug)" +
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
        String sqlSelect = "SELECT count(*) FROM Bussgeld" +
                " WHERE Tageszeit = ? AND VerstossID = ? AND Fahrzeug = ?";
        String sqlInsert = "INSERT INTO Bussgeld VALUES(?,?,?,?)";
        final int batchSize = 5;
        int count = 0;
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
        ){
            for(ElementBussgeld ElementBussgeld: bussgeld){
                // SELECT
                pstmtSelect.setTimestamp(1, ElementBussgeld.getTageszeit());
                pstmtSelect.setInt(2, ElementBussgeld.getVerstossID());
                pstmtSelect.setString(3, ElementBussgeld.getFahrzeug());
                ResultSet resultSelect = pstmtSelect.executeQuery();
                int result = 1;
                while(resultSelect.next()){
                    result = Integer.parseInt(resultSelect.getString(1));
                    //System.out.println(result);
                }
                if(result == 0) {
                    pstmtInsert.setInt(1, ElementBussgeld.getID());
                    pstmtInsert.setTimestamp(2, ElementBussgeld.getTageszeit());
                    pstmtInsert.setInt(3, ElementBussgeld.getVerstossID());
                    pstmtInsert.setString(4, ElementBussgeld.getFahrzeug());

                    pstmtInsert.addBatch();
                    if (++count % batchSize == 0) {
                        pstmtInsert.executeBatch();
                    }
                }
            }
            pstmtInsert.executeBatch();
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
        List<ElementBussgeld> bussgeld = DatenDateiLesen.readBussgeld();
        insertTableBussgeld(bussgeld);
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
