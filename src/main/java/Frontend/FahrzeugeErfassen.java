package Frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        ActionListener abbrechen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToStart();
            }
        };cancel_btn.addActionListener(abbrechen);
    }

    private void backToStart(){
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
