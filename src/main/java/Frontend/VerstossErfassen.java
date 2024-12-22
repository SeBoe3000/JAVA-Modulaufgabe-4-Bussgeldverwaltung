package Frontend;

import javax.swing.*;
import java.awt.*;

public class VerstossErfassen {
    private static final JFrame verstoss = new JFrame("Verstoss erfassen");


    private void verstoss() {
        JPanel panel = new JPanel();


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
