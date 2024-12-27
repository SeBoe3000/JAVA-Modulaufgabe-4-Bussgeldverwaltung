package Frontend;

import Datenbank.Abfragen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BussgelderGUI implements KeyListener {
    private static final JFrame start = new JFrame("Bussgelder");

    // Transaktion-Buttons
    JButton create_fahrzeug = new JButton("Fahrzeug erfassen");
    JButton create_verstoss = new JButton("Verstoss erfassen");
    ButtonGroup create_group = new ButtonGroup();

    // Ergebnisse von Abfragen
    // TODO: Abfragen nach Eingabe neuer Daten aktualisieren
    JLabel abfrage1 = new JLabel();
    JLabel abfrage2 = new JLabel();
    JLabel abfrage3 = new JLabel();


    private void start() {
        JPanel panel = new JPanel();

        // GridBagLayout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(gridbag);

        // Erfassen Buttons hinzufügen
        gbc.gridx = 0; // Spalte
        gbc.gridy = 0; // Zeile
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.CENTER;

        create_group.add(create_fahrzeug);
        create_group.add(create_verstoss);

        JPanel create_group = new JPanel();
        create_group.setLayout(new BoxLayout(create_group, BoxLayout.X_AXIS));
        create_group.add(create_fahrzeug);
        create_group.add(create_verstoss);
        panel.add(create_group, gbc);

        // Abfragen hinzufügen
        gbc.gridy = 1; // Zeile
        panel.add(abfrage1, gbc);
        gbc.gridy = 2; // Zeile
        panel.add(abfrage2, gbc);
        gbc.gridy = 3; // Zeile
        panel.add(abfrage3, gbc);

        // Panel dem Frame hinzufügen
        start.add(panel);

        // Größe vom Fenster auf Hälte der Bildschirmgröße in die Mitte setzen
        Dimension dim = new Dimension(1920, 1080);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        start.setSize(dim.width / 2, dim.height / 2);
        start.setLocation(dim.width / 4, dim.height / 4);
        // Fenster Schließen, wenn geschlossen
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Fenster anzeigen
        start.setVisible(true);
        // KeyListener hinzufügen
        start.addKeyListener(this);
        start.setFocusable(true);
    }

    private void buttonListenerstart() {
        // TODO: ButtonListener implementieren
        ActionListener fahrzeug_erfassen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fahrzeugErfassen();
            }
        };
        create_fahrzeug.addActionListener(fahrzeug_erfassen);

        ActionListener verstoss_erfassen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verstossErfassen();
            }
        };
        create_verstoss.addActionListener(verstoss_erfassen);
    }

    // Fahrzeug erfassen
    public void fahrzeugErfassen() {
        FahrzeugeErfassen fahrzeug = new FahrzeugeErfassen();
        fahrzeug.main();
        start.setVisible(false);
    }

    // Verstoss erfassen
    public void verstossErfassen() {
        VerstossErfassen verstoss = new VerstossErfassen();
        verstoss.main();
        start.setVisible(false);
    }

    public void abfragenUpdaten(){
        abfrage1.setText(Abfragen.abfrageHerstellerMeisteVerstoss());
        abfrage2.setText(Abfragen.abfrageTagMeisteVerstoss());
        abfrage3.setText(Abfragen.abfrageFahrzeugMeisteVerstoss());
        // System.out.println("Ergebnis abfragenUpdate: " + Abfragen.abfrageTagMeisteVerstoss());
        // System.out.println(abfrage2.getText());
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                start();
                buttonListenerstart();
                // Abfragen setzen
                abfragenUpdaten();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F: {
                fahrzeugErfassen();
                break;
            }
            case KeyEvent.VK_V: {
                verstossErfassen();
                break;
            }
        }
    }
}