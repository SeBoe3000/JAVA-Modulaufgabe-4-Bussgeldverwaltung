package Frontend;

import javax.swing.*;
import java.awt.*;

public class VerstossErfassen {
    private static final JFrame verstoss = new JFrame("Verstoss erfassen");

    // ID
    EingabePanel id = new EingabePanel("ID: ");
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

    private void verstoss() {
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
        panel.add(id, gbc);

        // Feld Modell hinzufügen
        gbc.gridy = 1; // Spalte
        panel.add(tageszeit, gbc);

        // Feld Hersteller hinzufügen
        gbc.gridy = 2; // Spalte
        panel.add(verstossID, gbc);

        // Feld Motorleistung hinzufügen
        gbc.gridy = 3; // Spalte
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

        gbc.gridy = 4; // Spalte
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


    private void buttonListenerfahrzeuge() {
        // TODO: ButtonListener implementieren
    }

    public void main(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                verstoss();
                buttonListenerfahrzeuge();
            }
        });
    }
}
