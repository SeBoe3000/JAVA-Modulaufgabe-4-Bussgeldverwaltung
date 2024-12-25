package Frontend;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EingabenCheck {

    /* Überprüfung String auf gültige Fließkommazahl, d.h.:
    - Nur Zahlen und Punkte sind zulässige Zeichen
    - Nur ein Punkt darf vorhanden sein.
    - Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein. (Dadurch Überprüfung auf nicht leerer Wert)
     */
    public static boolean isValidFloat(String eingabe){
        boolean isValid = true;
        Integer anzahlPunkte = 0;
        Integer anzahlZahl = 0;

        for(int i = 0; i < eingabe.length(); i++){
            if(!((eingabe.charAt(i) >= '0' && eingabe.charAt(i) <= '9')||eingabe.charAt(i) == '.')){
                isValid = false;
                break;
            }
            if((eingabe.charAt(i) >= '1' && eingabe.charAt(i) <= '9')){
                anzahlZahl ++;
            }
            if(eingabe.charAt(i) == '.'){
                anzahlPunkte ++;
            }
        }
        //System.out.println("anzahlPunkte " + anzahlPunkte + " anzahlZahl " + anzahlZahl);
        if (anzahlPunkte > 1 || anzahlZahl == 0){
            isValid = false;
        }
        return isValid;
    }

    /* Überprüfung String auf gültige Integer, d.h:
    - Nur Zahlen zulässige Zeichen
    - Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein. (Dadurch Überprüfung auf nicht leerer Wert)
    */
    public static boolean isValidInteger(String eingabe){
        boolean isValid = true;
        Integer anzahlZahl = 0;

        for(int i = 0; i < eingabe.length(); i++){
            if(!(eingabe.charAt(i) >= '0' && eingabe.charAt(i) <= '9')){
                isValid = false;
                break;
            }
            if((eingabe.charAt(i) >= '1' && eingabe.charAt(i) <= '9')){
                anzahlZahl ++;
            }
        }
        if (anzahlZahl == 0){
            isValid = false;
        }
        return isValid;
    }

    /* Überprüfung Integer (nach Umwandlung) auf
    - kleiner gleich 3
    */
    public static boolean isValidVergehen(Integer eingabe){
        boolean isValid = true;

        if(eingabe >= 3){
            isValid = false;
        }
        return isValid;
    }

    /* Überprüfung String auf
    - mind. ein Zeichen.
    - kein DROP TABLE, DELETE, GRANT, REVOKE vorhanden
    - kein OR mit Leerzeichen davor und danach vorhanden */
    public static boolean isValidString(String eingabe){
        boolean isValid = true;
        // Mind. ein Zeichen muss vorhanden sein
        if(eingabe.equals("")){
            isValid = false;
        } else {
            isValid = true;
        }
        // Kein Drop, Delete, Grant, Revoke oder or mit Leerzeichen davor und danach vorhanden
        boolean injection = false;
        Pattern pattern = Pattern.compile("([dD][rR][oO][pP]|[dD][eE][lL][eE][tT][eE]|[gG][rR][aA][nN][tT]|[rR][eE][vV][oO][kK][eE]|\\s[oO][rR]\\s)");
        Matcher matcher = pattern.matcher(eingabe);
        injection = matcher.find();
        // System.out.println("injection: " + injection);
        if(injection == true){
            isValid = false;
        }

        return isValid;
    }

    /* Überprüfung String auf Datum
    - TODO: Prüfung auf richtiges Datums- / Uhrzeitformat und in Bestandteilt für Prüfung zerlegen
    - Prüfung auf Tage nicht größer 31, bei Monat
    - Prüfung auf Monate nicht größer 12
    - Prüfung auf Tage nicht größer 30 bei Monat 04, 06, 09, 11
    - Prüfung auf Tage nicht größer 29 bei Monat 02
    - Prüfung auf Tage nicht größer 28 bei Monat 02 und keinem Schaltjahr
    - Prüfung auf Stunden nicht größer 23
    - Prüfung auf Minuten nicht größer 59
    - Prüfung auf Sekunden nicht größer 59
    - TODO: Prüfung auf Datum (+ Uhrzeit) nicht in der Zukunft
    */
    public static boolean isValidDatum(String eingabe){
        boolean isValid = true;
        Integer Tag = 0;
        Integer Monat = 0;
        Integer Jahr = 0;
        Integer Stunden = 0;
        Integer Minuten = 0;
        Integer Sekunden = 0;

        // in die einzelnen Bestandteile zerlegen

        if (Tag > 31 ||
                Monat > 12 ||
                ((Monat == 4 || Monat == 6 || Monat == 9 || Monat == 11) && Tag > 30 ) ||
                (Monat == 2 && Tag > 29) ||
                (Monat == 2 && Tag > 28 && !((Jahr % 4 == 0 && Jahr % 100 != 0) || Jahr % 400 == 0)) ||
                (Stunden > 23) ||
                (Minuten > 59) ||
                (Sekunden > 59)
        ) {
            isValid = false;
        }

        return isValid;
    }

    /* Überprüfung String auf Kennzeichen
    - 1-3 Buchstaben + "-" + 1-2 Buchstaben + "-" + 1-4 Zahlen
    - nur Großbuchstaben ohne Umlaute.
    - Zahlenfolge beginnt nicht mit 0
    - nur gültige Unterscheidungskennzeichen, z.B. Liste hier: https://autokennzeichen.de/a-z/
    - Verstößt nicht gegen die guten Sitten, d.h. z.B. kein SS.
    Hinweis: etwaige weitere Regeln wie z.B. historische oder elektronische Fahrzeuge werden hier nicht beachtet. */
    public static boolean isValidKennzeichen(String eingabe){
        boolean isValid = true;
        Pattern pattern = Pattern.compile("^[A-Z]{1,3}-[A-Z]{1,2}-[1-9][0-9]{0,3}$");
        Matcher matcher = pattern.matcher(eingabe);
        isValid = matcher.find();

        // Zerlegen nur, wenn richtiger Aufbau, ansonsten kommt es zu einem Fehler.
        if(isValid) {

            String[] parts = eingabe.split("-");
            String part1 = parts[0];
            // System.out.println(part1);

            //TODO: gültige Unterscheidungskennzeichen aus dem Internet laden und in Liste / Datenbank speichern
            ArrayList<String> Kennzeichen = new ArrayList<>();
            Kennzeichen.add("A");
            Kennzeichen.add("KA");
            Kennzeichen.add("RA");
            Kennzeichen.add("GAP");

            boolean startFound = false;
            for (int i = 0; i < Kennzeichen.size(); i++) {
                if (Kennzeichen.get(i).equals(part1)) {
                    startFound = true;
                    // System.out.println("startFound ist: " + startFound);
                    break;
                }
            }
            if (startFound == false) {
                isValid = false;
            }
            // Verstoß gegen die guten Sitten
            if (parts[1].equals("SS")) {
                isValid = false;
                // System.out.println("Verstoß gegen die guten Sitten");
            }
        }
        return isValid;
    }

    // Überprüfung auf maximale Länge
    public static boolean isValidStringLaenge(String eingabe, Integer laenge){
        boolean isValid = true;
        Integer anzahlZahl = 0;

        for(int i = 0; i < eingabe.length(); i++){
            anzahlZahl ++;
        }
        if (anzahlZahl > laenge){
            isValid = false;
        }
        return isValid;
    }

}
