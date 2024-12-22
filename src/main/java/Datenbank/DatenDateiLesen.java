package Datenbank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatenDateiLesen {

    public static ArrayList<ElementFahrzeug> readFahrezuge(){
        ArrayList<ElementFahrzeug> elementsList = new ArrayList<>();
        File myFile;
        String os = System.getProperty("os.name");
        if (os.contains("Wind")) {
            myFile = new File("src\\main\\java\\Dateien\\Fahrzeuge.csv");
        } else {
            myFile = new File("src/main/java/Dateien/Fahrzeuge.csv");
        }
        try{
            Scanner myFileReader = new Scanner(myFile);
            String line;
            line = myFileReader.nextLine(); // erste Zeile nicht berücksichtigen

            while(myFileReader.hasNextLine()){
                line = myFileReader.nextLine();
                String[] splitted = line.split(";");
                Integer Motorleistung = 0;
                try{
                    Motorleistung = Integer.parseInt(splitted[3]);
                    }
                catch(Exception e){
                    // TODO: Fehler in GUI hochbringen
                    e.printStackTrace();
                }
                // Objekte erzeugen
                ElementFahrzeug element = new ElementFahrzeug(splitted[0], splitted[1], splitted[2], Motorleistung);
                elementsList.add(element);
            }
        } catch (FileNotFoundException e){
            System.out.println("Die Datei existiert nicht");
        }
        return elementsList;
    }
}
