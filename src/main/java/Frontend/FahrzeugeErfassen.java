package Frontend;

import Datenbank.ElementFahrzeug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Datenbank.BeispieldatenEinfuegen.insertTableFahrzeug;

public class FahrzeugeErfassen {
    private static final JFrame fahrzeuge = new JFrame("Fahrzeuge erfassen");
    // Kennzeichen
    EingabePanel kennzeichen = new EingabePanel("Kennzeichen: ");
    // Modell
    EingabePanel modell = new EingabePanel("Modell: ");
    // Hersteller
    EingabePanel hersteller = new EingabePanel("Hersteller: ");
    // Motorleistung
    EingabePanel motorleistung = new EingabePanel("Motorleistung: ");
    // Transaktion-Buttons
    JButton create_btn = new JButton("Erfassen");
    JButton ok_btn = new JButton("OK");
    JButton cancel_btn = new JButton("Abbrechen");
    ButtonGroup transaction_group = new ButtonGroup();
    // Zum Speichern der Ergebnisse
    ArrayList<ElementFahrzeug> FahrzeugeList = new ArrayList<>();
    // Zum Prüfen, ob Elemente hinzugefügt wurden
    Integer anzahlElemente = 0;

    private void fahrzeuge() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Feld Kennzeichen hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(kennzeichen, gbc);

        // Feld Modell hinzufügen
        gbc.gridy = 1; // Spalte
        panel.add(modell, gbc);

        // Feld Hersteller hinzufügen
        gbc.gridy = 2; // Spalte
        panel.add(hersteller, gbc);

        // Feld Motorleistung hinzufügen
        gbc.gridy = 3; // Spalte
        panel.add(motorleistung, gbc);

        // Transaction Buttons hinzufügen
        transaction_group.add(create_btn);
        transaction_group.add(ok_btn);
        transaction_group.add(cancel_btn);

        JPanel transaction_panel = new JPanel();
        transaction_panel.setLayout(new BoxLayout(transaction_panel, BoxLayout.X_AXIS));
        transaction_panel.add(create_btn);
        transaction_panel.add(ok_btn);
        transaction_panel.add(cancel_btn);

        gbc.gridy = 4; // Spalte
        panel.add(transaction_panel, gbc);

        // Panel dem Frame hinzufügen
        fahrzeuge.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        fahrzeuge.setSize(dim.width/2, dim.height/2);
        fahrzeuge.setLocation(dim.width/4, dim.height/4);
        // Fenster Schließen, wenn geschlossen
        fahrzeuge.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        fahrzeuge.setVisible(true);
    }

    private void buttonListenerfahrzeuge() {
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
                        if(noElement == false) {
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
        String eingabeKennzeichen = "";
        String eingabeModell = "";
        String eingabeHersteller = "";
        int eingabeMotorleistung = 0;
        boolean inWork = true;
        int anzahlFelderKorrekt = 0;

        // Eingaben prüfen und Felder befüllen
        if(checkValues(kennzeichen, "isValidString", "Bitte ein Kennzeichen angeben.")
                && checkValues(kennzeichen, "laenge10", "Die Eingabe vom Kennzeichen ist zu lang.")
                && checkValues(kennzeichen, "kennzeichen", "Das Kennzeichen entspricht nicht dem richtigen Aufbau.")){
            eingabeKennzeichen = kennzeichen.getTextfield();
            anzahlFelderKorrekt ++;
        }
        if(checkValues(modell, "isValidString", "Bitte ein Modell angegeben.")
                && checkValues(modell, "laenge30", "Die Eingabe vom Modell ist zu lang.")){
            eingabeModell = modell.getTextfield();
            anzahlFelderKorrekt ++;
        }
        if(checkValues(hersteller, "isValidString", "Bitte ein Hersteller angegeben.")
                && checkValues(hersteller, "laenge20", "Die Eingabe vom Hersteller ist zu lang.")){
            eingabeHersteller = hersteller.getTextfield();
            anzahlFelderKorrekt ++;
        }
        if(checkValues(motorleistung, "Integer", "Eine ungültige Motorleistung wurde angegeben.")){
            eingabeMotorleistung = Integer.parseInt(motorleistung.getTextfield());
            anzahlFelderKorrekt ++;
        }

        //System.out.println("Anzahlkorrekter Felder " + anzahlFelderKorrekt);

        if(anzahlFelderKorrekt == 4){
            boolean insertPossible = true;
            // Hier könnte auf einen doppelten Datensatz geprüft werden.
            // Beim Insert erfolgt eine Meldung, ob doppelte Datensätze dabei waren, oder nicht. Daher bleibt hier die Überprüfung aus.
            // Bei einem vorhandenen Datensatz könnte dann insertPossible auf false geändert werden und ggf. das nächste If nach außerhalb angebracht werden und true und false hier getauscht werden.

            if(insertPossible) {
                // Element der Liste hinzufügen
                ElementFahrzeug fahrzeug = new ElementFahrzeug(eingabeKennzeichen, eingabeModell, eingabeHersteller, eingabeMotorleistung);
                FahrzeugeList.add(fahrzeug);
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
        if(insertTableFahrzeug(FahrzeugeList) == true) {
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
        } else if (checkArt == "Integer") {
            check1 = EingabenCheck.isValidInteger(check);
        } else if (checkArt == "laenge10") {
            check1 = EingabenCheck.isValidStringLaenge(check, 10);
        } else if (checkArt == "laenge20") {
            check1 = EingabenCheck.isValidStringLaenge(check, 20);
        } else if (checkArt == "laenge30") {
            check1 = EingabenCheck.isValidStringLaenge(check, 30);
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
        if (!(kennzeichen.getTextfield().isEmpty())) {
            notInWork = false;
            //System.out.println("Noch nicht fertig - Kennzeichen");
        }
        if (!(modell.getTextfield().isEmpty())) {
            notInWork = false;
            //System.out.println("Noch nicht fertig - Modell");
        }
        if (!(hersteller.getTextfield().isEmpty())) {
            notInWork = false;
            //System.out.println("Noch nicht fertig - Hersteller");
        }
        if (!(motorleistung.getTextfield().isEmpty())) {
            notInWork = false;
            //System.out.println("Noch nicht fertig - Motorleistung");
        }
        return notInWork;
    }

    // Prüfung, ob ein Wert in der Liste vorhanden ist.
    public boolean checkElemetInList() {
        boolean noElement = true;
        if (anzahlElemente > 0) {
            noElement = false;
            System.out.println("Wert in Liste vorhanden");
        }
        return noElement;
    }

    // Felder nach erfolgreicher Verarbeitung oder Abbrechen leeren und Fehler entfernen
    private void felderLeeren(){
        // Felder leeren
        kennzeichen.setTextField("");
        modell.setTextField("");
        hersteller.setTextField("");
        motorleistung.setTextField("");
        // Auch einen etwaigen Fehler aus den Feldern entfernen
        kennzeichen.removeError();
        modell.removeError();
        hersteller.removeError();
        motorleistung.removeError();
    }

    private void backToStart(){
        // Werte und Fehler in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
        felderLeeren();
        // Frame start wieder anzeigen
        BussgelderGUI calc = new BussgelderGUI();
        calc.main();
        fahrzeuge.setVisible(false);
    }

    public void main(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fahrzeuge();
                buttonListenerfahrzeuge();
            }
        });
    }
}