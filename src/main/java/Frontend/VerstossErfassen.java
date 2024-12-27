package Frontend;

import Datenbank.Datenbankverbindung;
import Datenbank.ElementVerstoss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Datenbank.BeispieldatenEinfuegen.insertTableVerstoss;

public class VerstossErfassen {
    private static final JFrame verstoss = new JFrame("Verstoss erfassen");
    // ID
    EingabePanel id = new EingabePanel("ID: ");
    // Beschreibung
    EingabePanel beschreibung = new EingabePanel("Beschreibung: ");
    // Strafe
    EingabePanel strafe = new EingabePanel("Strafe: ");
    // Punkte
    EingabePanel punkte = new EingabePanel("Punkte: ");
    // Fahrverbot
    EingabePanel fahrverbot = new EingabePanel("Fahrverbot: ");
    // Transaktion-Buttons
    JButton create_btn = new JButton("Erfassen");
    JButton ok_btn = new JButton("OK");
    JButton cancel_btn = new JButton("Abbrechen");
    ButtonGroup transaction_group = new ButtonGroup();
    // Zum Speichern der Ergebnisse
    ArrayList<ElementVerstoss> VerstossList = new ArrayList<>();
    // Zum Prüfen, ob Elemente hinzugefügt wurden
    Integer anzahlElemente = 0;

    private void verstoss() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Feld ID hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(id, gbc);

        // Feld Beschreibung hinzufügen
        gbc.gridy = 1; // Spalte
        panel.add(beschreibung, gbc);

        // Feld Strafe hinzufügen
        gbc.gridy = 2; // Spalte
        panel.add(strafe, gbc);

        // Feld Punkte hinzufügen
        gbc.gridy = 3; // Spalte
        panel.add(punkte, gbc);

        // Feld Fahrverbot hinzufügen
        gbc.gridy = 4; // Spalte
        panel.add(fahrverbot, gbc);

        // Transaction Buttons hinzufügen
        transaction_group.add(create_btn);
        transaction_group.add(ok_btn);
        transaction_group.add(cancel_btn);

        JPanel transaction_panel = new JPanel();
        transaction_panel.setLayout(new BoxLayout(transaction_panel, BoxLayout.X_AXIS));
        transaction_panel.add(create_btn);
        transaction_panel.add(ok_btn);
        transaction_panel.add(cancel_btn);

        gbc.gridy = 5; // Spalte
        panel.add(transaction_panel, gbc);

        // Panel dem Frame hinzufügen
        verstoss.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        verstoss.setSize(dim.width/2, dim.height/2);
        verstoss.setLocation(dim.width/4, dim.height/4);
        // Fenster Schließen, wenn geschlossen
        verstoss.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        verstoss.setVisible(true);
    }

    private void buttonListenerverstoss() {
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
        int eingabeID = 0;
        String eingabeBeschreibung = "";
        float eingabeStrafe = 0F;
        int eingabePunkte = 0;
        int eingabeFahrverbot = 0;
        boolean inWork = true;
        int anzahlFelderKorrekt = 0;

        // Eingaben prüfen und Felder befüllen
        if(checkValues(id, "Integer", "Eine ungültige ID wurde angegeben.")){
            eingabeID = Integer.parseInt(id.getTextfield());
            anzahlFelderKorrekt ++;
        }
        if(checkValues(beschreibung, "isValidString", "Bitte eine Beschreibung angeben.")){
            eingabeBeschreibung = beschreibung.getTextfield();
            anzahlFelderKorrekt ++;
        }
        if(checkValues(strafe, "Float", "Eine ungültige Strafe wurde angegeben.")){
            eingabeStrafe = Float.parseFloat(strafe.getTextfield());
            anzahlFelderKorrekt ++;
        }
        if(checkValues(punkte, "Integer", "Eine ungültige Anzahl an Punkten wurde angegeben.")){
            eingabePunkte = Integer.parseInt(punkte.getTextfield());
            anzahlFelderKorrekt ++;
        }
        if(checkValues(fahrverbot, "Integer", "Eine ungültiges Fahrverbot wurde angegeben.")){
            eingabeFahrverbot = Integer.parseInt(fahrverbot.getTextfield());
            anzahlFelderKorrekt ++;
        }

        // System.out.println("Anzahlkorrekter Felder " + anzahlFelderKorrekt);

        if(anzahlFelderKorrekt == 5){
            boolean insertPossible = true;
            // Hier könnte auf einen doppelten Datensatz geprüft werden.
            // Beim Insert erfolgt eine Meldung, ob doppelte Datensätze dabei waren, oder nicht. Daher bleibt hier die Überprüfung aus.
            // Bei einem vorhandenen Datensatz könnte dann insertPossible auf false geändert werden und ggf. das nächste If nach außerhalb angebracht werden und true und false hier getauscht werden.

            // Prüfung, ob Element bereits in der Liste vorhanden ist, falls ja nicht hinzufügen.
            boolean inList = checkElementAlreadyInList(eingabeID);
            if(inList == true){
                JOptionPane.showMessageDialog(null, "Der angegebene Verstoss befindet sich bereits in der ElementListe. Geben Sie einen anderen Verstoss an.", "Datensatz bereits in ElementListe vorhanden", JOptionPane.ERROR_MESSAGE);
                insertPossible = false;
            }

            // Prüfung, ob Element bereits in Datenbank vorhanden ist, falls ja nicht hinzufügen.
            boolean inDatenbank = checkElementAlreadyInDatenbank(eingabeID);
            if(inDatenbank == true){
                JOptionPane.showMessageDialog(null, "Der angegebene Verstoss befindet sich bereits in der Datenbank. Geben Sie einen anderen Verstoss an.", "Datensatz bereits in Datenbank vorhanden", JOptionPane.ERROR_MESSAGE);
                insertPossible = false;
            }

            if(insertPossible) {
                // Element der Liste hinzufügen
                ElementVerstoss verstoss = new ElementVerstoss(eingabeID, eingabeBeschreibung, eingabeStrafe, eingabePunkte, eingabeFahrverbot);
                VerstossList.add(verstoss);
                anzahlElemente++;
                // System.out.println("Element Liste hinzufügen" + anzahlElemente);
                // Nach Hinzufügen die Felder leeren
                felderLeeren();
                // Finaler Check verstoss
                inWork = false;
            }
        }
        return inWork;
    }

    private void elementInsert(){
        // Liste der Elemente abarbeiten und in Datenbank erfassen. Meldung über durchgeführten Insert wird ausgegeben.
        // Wurden in der Zwischenzeit Daten bereits erfasst (mehrbenutzerbetrieb) ist es hier nicht mehr möglich einzugreifen.
        // Dafür müssten die bereits erfassten Daten als Liste angezeigt werden und eine nachträgliche Bearbeitung der Daten möglich sein.
        if(insertTableVerstoss(VerstossList) == true) {
            JOptionPane.showMessageDialog(null, "Die Datensätze wurden alle erfolgreich erfasst.");
        } else {
            JOptionPane.showMessageDialog(null, "Es waren doppelte Datensätze vorhanden. Es wurde nichts verarbeitet. Bitte nochmal von vorne erfassen.");
        }
    }

    // Prüfung auf gültige Eingaben
    public boolean checkValues(EingabePanel input, String checkArt, String fehlernachricht) {
        String check = input.getTextfield();
        boolean check1 = false;

        if (checkArt == "isValidString") {
            check1 = EingabenCheck.isValidString(check);
        }  else if (checkArt == "Integer") {
            check1 = EingabenCheck.isValidInteger(check);
        } else if (checkArt == "Float") {
            check1 = EingabenCheck.isValidFloat(check);
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
        if (!(id.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - ID");
        }
        if (!(beschreibung.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - Beschreibung");
        }
        if (!(strafe.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - Strafe");
        }
        if (!(punkte.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - Punkte");
        }
        if (!(fahrverbot.getTextfield().isEmpty())) {
            notInWork = false;
            // System.out.println("Noch nicht fertig - Fahrverbot");
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
    public boolean checkElementAlreadyInList(Integer id){
        boolean inList = false;
        for(ElementVerstoss ElementVerstoss: VerstossList){
            if (id.equals(ElementVerstoss.getVerstossID())){
                // System.out.println("Bereits vorhanden");
                inList = true;
            }
        }
        return inList;
    }

    // Prüfung, ob der Wert bereits in der Datenbank vorhanden ist
    public boolean checkElementAlreadyInDatenbank(Integer id){
        boolean inDatenbank = false;

        String sqlSelect = "SELECT count(*) FROM Verstoss WHERE VerstossID = ?";
        try(
                Connection conn = Datenbankverbindung.connect();
                PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
        ){
            pstmtSelect.setInt(1, id);
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
        id.setTextField("");
        beschreibung.setTextField("");
        strafe.setTextField("");
        punkte.setTextField("");
        fahrverbot.setTextField("");
        // Auch einen etwaigen Fehler aus den Feldern entfernen
        id.removeError();
        beschreibung.removeError();
        strafe.removeError();
        punkte.removeError();
        fahrverbot.removeError();
    }

    private void backToStart(){
        // Arrayliste leeren
        VerstossList.clear();
        // Bei Beendung Programm anzahlElemente auf 0 zurücksetzen. Ansonsten kommt nach Erfassung bei Abbrechen der Dialog.
        anzahlElemente = 0;
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Frame start wieder anzeigen
        BussgelderGUI calc = new BussgelderGUI();
        calc.main();
        verstoss.setVisible(false);
    }

    public void main(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                verstoss();
                buttonListenerverstoss();
            }
        });
    }
}