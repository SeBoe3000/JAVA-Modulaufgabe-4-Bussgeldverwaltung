package Frontend;

import Datenbank.ElementFahrzeug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        // TODO: ButtonListener implementieren
        ActionListener erfassen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elementHinzu();
            }
        };create_btn.addActionListener(erfassen);

        ActionListener ok = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // nur wenn inWork false ist, die Datensätze in die Datenbank schreiben.
                if(elementHinzu() == false){
                    elementInsert();
                    // TODO: nur wenn Insert erfolgreich war, zum Start zurückkehren
                    backToStart();
                }
            }
        };ok_btn.addActionListener(ok);

        ActionListener abbrechen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean notInWork = true;
                boolean noElement = true;
                
                // Prüfung ob 1 Feld gefüllt ist
                if(!(kennzeichen.getTextfield().isEmpty())){
                    notInWork = false;
                    //System.out.println("Noch nicht fertig - Kennzeichen");
                }
                if(!(modell.getTextfield().isEmpty())){
                    notInWork = false;
                    //System.out.println("Noch nicht fertig - Modell");
                }
                if(!(hersteller.getTextfield().isEmpty())){
                    notInWork = false;
                    //System.out.println("Noch nicht fertig - Hersteller");
                }
                if(!(motorleistung.getTextfield().isEmpty())){
                    notInWork = false;
                    //System.out.println("Noch nicht fertig - Motorleistung");
                }
                
                // Check ob Element in der Liste
                if(anzahlElemente > 0){
                    noElement = false;
                    System.out.println("Wert in Liste vorhanden");
                }

                // Verarbeitung wenn mind. 1 Feld gefüllt ist
                if(notInWork == false) {
                    // TODO: Dialog hochbringen, ob Eingabe verarbeitet werden soll.
                    // Bei Ja, Eingabencheck, Insert aufrufen und Dialog schließen
                    // Bei Nein, Fenster schließen
                }

                // Verarbeitung, wenn noch ein Element in der Liste drin ist
                if(noElement == false) {
                    // TODO: Dialog hochbringen, ob Elemente hinzugefügt werden sollen
                    // Bei Ja, Insert aufrufen und Dialog schließen
                    // Bei Nein, Fenster schließen
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

        System.out.println("Anzahlkorrekter Felder " + anzahlFelderKorrekt);

        if(anzahlFelderKorrekt == 4){
            boolean insertPossible = true;
            // TODO: Prüfung auf vorhandenen Datensatz falls ja, insertPossible auf false ändern.
            // TODO: nächstes If nach außerhalb anbringen und true und false hier tauschen.

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
        // TODO: nochmal prüfen, ob alle Daten erfasst werden können (mehrbenutzerbetrieb)
        // TODO: Liste der Elemente abarbeiten und in Datenbank erfassen
        System.out.println("Liste Elemente in Datenbank erfassen");
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

    // Felder nach erfolgreicher Verarbeitung oder Abbrechen leeren
    private void felderLeeren(){
        kennzeichen.setTextField("");
        modell.setTextField("");
        hersteller.setTextField("");
        motorleistung.setTextField("");
    }

    private void backToStart(){
        // Werte in Feldern leeren, sonst sind diese beim nächsten Mal gefüllt
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
