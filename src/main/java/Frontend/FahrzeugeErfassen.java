package Frontend;

import javax.swing.*;
import java.awt.*;

public class FahrzeugeErfassen {
    private static final JFrame fahrzeuge = new JFrame("Fahrzeuge erfassen");


    private void fahrzeuge() {
        JPanel panel = new JPanel();


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
