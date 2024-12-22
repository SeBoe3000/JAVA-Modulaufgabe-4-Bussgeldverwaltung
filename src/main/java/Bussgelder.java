import Datenbank.BeispieldatenEinfuegen;
import Datenbank.Tabellenerstellen;
import Frontend.BussgelderGUI;
import Frontend.FahrzeugeErfassen;
import Frontend.VerstossErfassen;

public class Bussgelder {
    public static void main(String[] args) {
        // Tabellen erstellen, falls noch nicht vorhanden
        Tabellenerstellen.createTableall();
        // Daten erstellen, falls noch nicht vorhanden
        BeispieldatenEinfuegen.fillTableall();
        // GUI aufrufen
        BussgelderGUI calc = new BussgelderGUI();
        calc.main();
    }
}
