package Frontend;

import Datenbank.Datenbankverbindung;
import Datenbank.ElementBussgeld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import static Datenbank.BeispieldatenEinfuegen.insertTableBussgeld;

public class BussgeldErfassen {
    private static final JFrame bussgeld = new JFrame("Bussgeld erfassen");
    // Tageszeit
    EingabePanel tageszeit = new EingabePanel("Tageszeit: ");
    // VerstossID
    EingabePanel verstossID = new EingabePanel("VerstossID: ");
    // Fahrzeug
    EingabePanel fahrzeug = new EingabePanel("Fahrzeug: ");
    // Transaktion-Buttons
    JButton create_btn = new JButton("Erfassen");
    JButton ok_btn = new JButton("OK");
    JButton cancel_btn = new JButton("Abbrechen");
    ButtonGroup transaction_group = new ButtonGroup();
    // Zum Speichern der Ergebnisse
    ArrayList<ElementBussgeld> BussgeldList = new ArrayList<>();
    // Zum Prüfen, ob Elemente hinzugefügt wurden
    Integer anzahlElemente = 0;

    private void bussgeld() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Feld Tageszeit hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(tageszeit,gbc);

        // Feld VerstossID hinzufügen
        gbc.gridy = 1; // Spalte
        panel.add(verstossID, gbc);

        // Feld Fahrzeug hinzufügen
        gbc.gridy = 2; // Spalte
        panel.add(fahrzeug, gbc);

        // Transaction Buttons hinzufügen
        transaction_group.add(create_btn);
        transaction_group.add(ok_btn);
        transaction_group.add(cancel_btn);

        JPanel transaction_panel = new JPanel();
        transaction_panel.setLayout(new BoxLayout(transaction_panel, BoxLayout.X_AXIS));
        transaction_panel.add(create_btn);
        transaction_panel.add(ok_btn);
        transaction_panel.add(cancel_btn);

        gbc.gridy = 3; // Spalte
        panel.add(transaction_panel, gbc);

        // Panel dem Frame hinzufügen
        bussgeld.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        bussgeld.setSize(dim.width/2, dim.height/2);
        bussgeld.setLocation(dim.width/4, dim.height/4);
        // Fenster Schließen, wenn geschlossen
        bussgeld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        bussgeld.setVisible(true);
    }

    private void buttonListenerbussgeld() {
        ActionListener erfassen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elementHinzu();
            }
        };create_btn.addActionListener(erfassen);

        ActionListener ok = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prüfung ob 1 Feld gefüllt ist
                boolean notInWork = checkFieldsfilled();
                // Check ob Element in der Liste
                boolean noElement = checkElemetInList();

                // bei leeren Feldern und vorhandenem Element in der Liste den Insert durchführen und den Dialog schließen
                if(notInWork == true && noElement == false){
                    elementInsert();
                    // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
                    backToStart();
                }
                // ist mind. 1 Feld gefüllt, dann nur wenn inWork false ist, die Datensätze in die Datenbank schreiben.
                else if(elementHinzu() == false){
                    elementInsert();
                    // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
                    backToStart();
                }
            }
        };ok_btn.addActionListener(ok);

        ActionListener abbrechen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prüfung ob 1 Feld gefüllt ist
                boolean notInWork = checkFieldsfilled();
                // Check ob Element in der Liste
                boolean noElement = checkElemetInList();

                // System.out.println("Ergebnis Check - notInWork: " + notInWork + " und noElement: " + noElement);
                // Verarbeitung wenn mind. 1 Feld gefüllt oder noch ein Element in der Liste drin ist
                if(notInWork == false || noElement == false) {
                    String[] options = {"Ja", "Nein"};
                    int jaNein = JOptionPane.showOptionDialog(null, "Sollen die Änderungen gespeichert werden?",
                            "Speichern?", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if (jaNein == 1){
                        // Bei Nein, Fenster schließen
                        backToStart();
                    } else {
                        // Bei Ja Eingabencheck ausführen (nur wenn Felder gefüllt sind)
                        if(notInWork == false) {
                            // Nur einfügen, wenn keine Fehlermeldung vorhanden
                            if(elementHinzu() == false){
                                elementInsert();
                                // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
                                backToStart();
                            }
                        }
                        if(noElement == false && notInWork == true) {
                            // Bei Ja Insert aufrufen und Dialog schließen
                            elementInsert();
                            // Dialog nur bei keinen doppelten Datensätzen schließen wäre nur möglich, wenn Daten in einer Liste angezeigt werden.
                            backToStart();
                        }
                    }
                }

                // Verarbeitung, wenn alle Felder leer sind und kein Element in der Liste ist
                if(notInWork && noElement){
                    backToStart();
                }
            }
        };cancel_btn.addActionListener(abbrechen);
    }

    private boolean elementHinzu(){
        Timestamp eingabeTageszeit = Timestamp.valueOf("1990-11-11 11:11:11");
        int eingabeVerstossID = 0;
        String eingabeFahrzeug = "";
        boolean inWork = true;
        int anzahlFelderKorrekt = 0;

        // Eingaben prüfen und Felder befüllen
        if(checkValues(tageszeit, "isValidDatum", "Bitte gültiges Datum mit Uhrzeit im Format yyyy-MM-dd HH:mm:ss (yyyy = Jahr, MM = Monat, dd = Tag, HH = Stunde, mm = Minute und ss = Sekunde) angegeben.")
            && checkValues(tageszeit, "isValidDatumCorrect", "Das eingegebene Datum oder die eingegebene Uhrzeit ist ungültig, hat aber das richtige Format.")
            && checkValues(tageszeit, "isValidDatumFuture", "Das eingegebene Datum mit Uhrzeit darf nicht in der Zukunft liegen.")){
            eingabeTageszeit = Timestamp.valueOf(tageszeit.getTextfield());
            anzahlFelderKorrekt ++;
        }
        if(checkValues(verstossID, "Integer", "Eine ungültige Verstoss-ID wurde angegeben.")){
            eingabeVerstossID = Integer.parseInt(verstossID.getTextfield());
            anzahlFelderKorrekt ++;
        }
        if(checkValues(fahrzeug, "isValidString", "Bitte ein Fahrzeug angeben.")
                && checkValues(fahrzeug, "laenge10", "Die Eingabe vom Fahrzeug ist zu lang.")
                && checkValues(fahrzeug, "kennzeichen", "Das Fahrzeug entspricht nicht dem richtigen Aufbau.")){
            eingabeFahrzeug = fahrzeug.getTextfield();
            anzahlFelderKorrekt ++;
        }

        // Prüfung, ob VerstossID vorhanden ist
        boolean inVerstoss = VerstossErfassen.checkElementAlreadyInDatenbank(eingabeVerstossID);
        if(inVerstoss == false){
            verstossID.setError();
            JOptionPane.showMessageDialog(null, "Der angegebene Verstoss ist nicht in der Tabelle Verstoss vorhanden. Bitte geben Sie einen vorhandenen Verstoss an.", "Verstoss nicht vorhanden", JOptionPane.ERROR_MESSAGE);
            anzahlFelderKorrekt --;
        } else {
            verstossID.removeError();
        }

        // Prüfung, ob Kennzeichen vorhanden ist.
        boolean inFahrzeug = FahrzeugeErfassen.checkElementAlreadyInDatenbank(eingabeFahrzeug);
        if(inFahrzeug == false){
            fahrzeug.setError();
            JOptionPane.showMessageDialog(null, "Das angegebene Fahrzeug ist nicht in der Tabelle Fahrzeug vorhanden. Bitte geben Sie ein vorhandenes Fahrzeug an.", "Fahrzeug nicht vorhanden", JOptionPane.ERROR_MESSAGE);
            anzahlFelderKorrekt --;
        } else {
            fahrzeug.removeError();
        }

        // System.out.println("Anzahlkorrekter Felder " + anzahlFelderKorrekt);

        if(anzahlFelderKorrekt == 3){
            boolean insertPossible = true;
            // Hier könnte auf einen doppelten Datensatz geprüft werden.
            // Beim Insert erfolgt eine Meldung, ob doppelte Datensätze dabei waren, oder nicht. Daher bleibt hier die Überprüfung aus.
            // Bei einem vorhandenen Datensatz könnte dann insertPossible auf false geändert werden und ggf. das nächste If nach außerhalb angebracht werden und true und false hier getauscht werden.

            // Prüfung, ob Element bereits in der Liste vorhanden ist, falls ja nicht hinzufügen.
            boolean inList = checkElementAlreadyInList(eingabeTageszeit, eingabeVerstossID, eingabeFahrzeug);
            if(inList == true){
                JOptionPane.showMessageDialog(null, "Das angegebene Bussgeld befindet sich bereits in der ElementListe. Geben Sie ein anderes Bussgeld an.", "Datensatz bereits in ElementListe vorhanden", JOptionPane.ERROR_MESSAGE);
                insertPossible = false;
            }

            // Prüfung, ob Element bereits in Datenbank vorhanden ist, falls ja nicht hinzufügen.
            boolean inDatenbank = checkElementAlreadyInDatenbank(eingabeTageszeit, eingabeVerstossID, eingabeFahrzeug);
            if(inDatenbank == true){
                JOptionPane.showMessageDialog(null, "Das angegebene Bussgeld befindet sich bereits in der Datenbank. Geben Sie einen anderes Bussgeld an.", "Datensatz bereits in Datenbank vorhanden", JOptionPane.ERROR_MESSAGE);
                insertPossible = false;
            }

            if(insertPossible) {
                // Element der Liste hinzufügen
                ElementBussgeld bussgeld = new ElementBussgeld(eingabeTageszeit, eingabeVerstossID, eingabeFahrzeug);
                BussgeldList.add(bussgeld);
                anzahlElemente++;
                // System.out.println("Element Liste hinzufügen" + anzahlElemente);
                // Nach Hinzufügen die Felder leeren
                felderLeeren();
                // Finaler Check kennzeichnen
                inWork = false;
            }
        }
        return inWork;
    }

    private void elementInsert(){
        // Liste der Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert wird ausgegeben.
        // Wurden in der Zwischenzeit Daten bereits erfasst (mehrbenutzerbetrieb) ist es hier nicht mehr möglich einzugreifen.
        // Dafür müssten die bereits erfassten Daten als Liste angezeigt werden und eine nachträgliche Bearbeitung der Daten möglich sein.
        if(insertTableBussgeld(BussgeldList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Diese wurden nicht erfasst. Der Rest wurde verarbeitet.");
        }
    }

    // Prüfung auf gültige Eingaben
    public boolean checkValues(EingabePanel input, String checkArt, String fehlernachricht) {
        String check = input.getTextfield();
        boolean check1 = false;

        if (checkArt == "isValidString") {
            check1 = EingabenCheck.isValidString(check);
        }  else if (checkArt == "kennzeichen") {
            check1 = EingabenCheck.isValidKennzeichen(check);
        } else if (checkArt == "isValidDatum") {
            check1 = EingabenCheck.isValidDatum(check);
        } else if (checkArt == "isValidDatumCorrect") {
            check1 = EingabenCheck.isValidDatumCorrect(check);
        } else if (checkArt == "isValidDatumFuture") {
            check1 = EingabenCheck.isValidDatumNotInFuture(check);
        } else if (checkArt == "Integer") {
            check1 = EingabenCheck.isValidInteger(check);
        } else if (checkArt == "laenge10") {
            check1 = EingabenCheck.isValidStringLaenge(check, 10);
        }

        // Text für Fehlermeldung
        String text = fehlernachricht;
        String title = "Fehler";

        // Bei korrekter Eingabe (z.B. nach Fehler) Schriftfarbe zurückändern.
        input.removeError();

        if (!(check1)) {
            // Beim Fehler die Schriftfarbe auf rotändern.
            input.setError();
            JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    // Prüfung ob 1 Feld gefüllt ist
    public boolean checkFieldsfilled() {
        Boolean notInWork = true;
        if (!(tageszeit.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - Tageszeit");
        }
        if (!(verstossID.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - VerstossID");
        }
        if (!(fahrzeug.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - Fahrzeug");
        }
        return notInWork;
    }

    // Prüfung, ob ein Wert in der Liste vorhanden ist.
    public boolean checkElemetInList() {
        boolean noElement = true;
        if (anzahlElemente > 0) {
            noElement = false;
            // System.out.println("Wert in Liste vorhanden");
        }
        return noElement;
    }

    // Prüfung, ob der Wert bereits in der Liste vorhanden ist
    public boolean checkElementAlreadyInList(Timestamp tageszeit, Integer verstossID, String fahrzeug){
        boolean inList = false;
        for(ElementBussgeld ElementBussgeld : BussgeldList){
            if (tageszeit.equals(ElementBussgeld.getTageszeit())
                    && verstossID.equals(ElementBussgeld.getVerstossID())
                    && fahrzeug.equals(ElementBussgeld.getFahrzeug())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    // Prüfung, ob der Wert bereits in der Datenbank vorhanden ist
    public boolean checkElementAlreadyInDatenbank(Timestamp tageszeit, Integer verstossID, String fahrzeug){
        boolean inDatenbank = false;

        String sqlSelect = "SELECT count(*) FROM Bussgeld WHERE Tageszeit = ? AND VerstossID = ? AND Fahrzeug = ?";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
        ){
            pstmtSelect.setTimestamp(1, tageszeit);
            pstmtSelect.setInt(2, verstossID);
            pstmtSelect.setString(3, fahrzeug);
            ResultSet resultSelect = pstmtSelect.executeQuery();
            int result = 1;
            while (resultSelect.next()) {
                result = Integer.parseInt(resultSelect.getString(1));
                // System.out.println(result);
                if (result == 1) {
                    inDatenbank = true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return inDatenbank;
    }

    // Felder nach erfolgreicher Verarbeitung oder Abbrechen leeren und Fehler entfernen
    private void felderLeeren(){
        // Felder leeren
        tageszeit.setTextField("");
        verstossID.setTextField("");
        fahrzeug.setTextField("");
        // Auch einen etwaigen Fehler aus den Feldern entfernen
        tageszeit.removeError();
        verstossID.removeError();
        fahrzeug.removeError();
    }

    private void backToStart(){
        // Arrayliste leeren
        BussgeldList.clear();
        // Bei Beendung Programm anzahlElemente auf 0 zurücksetzen. Ansonsten kommt nach Erfassung bei Abbrechen der Dialog.
        anzahlElemente = 0;
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Frame start wieder anzeigen
        //BussgelderGUI calc = new BussgelderGUI();
        //calc.main();
        BussgelderGUI.start.setVisible(true);
        // Abfragen neu ausführen
        BussgelderGUI.abfragenUpdaten();
        bussgeld.setVisible(false);
    }

    public void main(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                bussgeld();
                buttonListenerbussgeld();
            }
        });
    }
}