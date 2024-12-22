package Datenbank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DatenDateiLesen {

    public static Object ermittelnFile(String file){
        File myFile;
        String os = System.getProperty("os.name");
        if (os.contains("Wind")) {
            myFile = new File("src\\main\\java\\Dateien\\" + file + ".csv");
        } else {
            myFile = new File("src/main/java/Dateien/" + file + ".csv");
        }
        return myFile;
    }

    public static ArrayList<ElementFahrzeug> readFahrezuge(){
        ArrayList<ElementFahrzeug> elementsList = new ArrayList<>();
        File myFile = new File(String.valueOf(ermittelnFile("Fahrzeuge")));
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

    public static ArrayList<ElementVerstoss> readVerstoss(){
        ArrayList<ElementVerstoss> elementsList = new ArrayList<>();
        File myFile = new File(String.valueOf(ermittelnFile("Verstoss")));
        try{
            Scanner myFileReader = new Scanner(myFile);
            String line;
            line = myFileReader.nextLine(); // erste Zeile nicht berücksichtigen

            while(myFileReader.hasNextLine()){
                line = myFileReader.nextLine();
                String[] splitted = line.split(";");
                Integer VerstossID = 0;
                Float Strafe = 0.0F;
                Integer Punkte = 0;
                Integer Fahrverbot = 0;

                try{
                    // funktioniert nicht mit dem letzten Eintrag (Fahrverbot)
                    if(splitted[3] == "") {
                        splitted[3] = "0";
                    }

                    VerstossID = Integer.parseInt(splitted[0]);
                    Strafe = Float.parseFloat(splitted[2]);
                    Punkte = Integer.parseInt(splitted[3]);
                    Fahrverbot = Integer.parseInt(splitted[4]);
                }
                catch(Exception e){
                    // TODO: Fehler in GUI hochbringen
                    e.printStackTrace();
                }
                // Objekte erzeugen
                ElementVerstoss element = new ElementVerstoss(VerstossID, splitted[1], Strafe, Punkte, Fahrverbot);
                elementsList.add(element);
            }
        } catch (FileNotFoundException e){
            System.out.println("Die Datei existiert nicht");
        }
        return elementsList;
    }

    public static ArrayList<ElementBussgeld> readBussgeld(){
        ArrayList<ElementBussgeld> elementsList = new ArrayList<>();
        File myFile = new File(String.valueOf(ermittelnFile("Bussgeld")));
        try{
            Scanner myFileReader = new Scanner(myFile);
            String line;
            line = myFileReader.nextLine(); // erste Zeile nicht berücksichtigen

            while(myFileReader.hasNextLine()){
                line = myFileReader.nextLine();
                String[] splitted = line.split(";");
                Integer ID = 0;
                //Date Tageszeit;
                Integer VerstossID = 0;

                try{
                    ID = Integer.parseInt(splitted[0]);
                    //Tageszeit = Float.parseFloat(splitted[1]);
                    VerstossID = Integer.parseInt(splitted[2]);
                }
                catch(Exception e){
                    // TODO: Fehler in GUI hochbringen
                    e.printStackTrace();
                }
                // Objekte erzeugen
                ElementBussgeld element = new ElementBussgeld(ID, splitted[1], VerstossID, splitted[3]);
                elementsList.add(element);
            }
        } catch (FileNotFoundException e){
            System.out.println("Die Datei existiert nicht");
        }
        return elementsList;
    }
}
