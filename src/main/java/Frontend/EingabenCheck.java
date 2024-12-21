package Frontend;

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
    - TODO: kein DROP TABLE, DELETE, GRANT, REVOKE vorhanden
    - TODO: kein OR mit Leerzeichen davor und danach vorhanden */
    public static boolean isValidString(String eingabe){
        boolean isValid = true;
        // Mind. ein Zeichen muss vorhanden sein
        if(eingabe.equals("")){
            isValid = false;
        } else {
            isValid = true;
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
    - TODO: 1-3 Buchstaben + "-" + 1-2 Buchstaben + "-" + 1-4 Zahlen
    - TODO: nur Großbuchstaben ohne Umlaute.
    - TODO: Zahlenfolge beginnt nicht mit 0
    - TODO: nur gültige Unterscheidungskennzeichen, z.B. Liste hier: https://autokennzeichen.de/a-z/
    - TODO: Verstößt nicht gegen die guten Sitten, d.h. z.B. kein SS.
    - TODO: kein DROP TABLE, DELETE, GRANT, REVOKE vorhanden
    - TODO: kein OR mit Leerzeichen davor und danach vorhanden
    Hinweis: etwaige weitere Regeln wie z.B. historische oder elektronische Fahrzeuge werden hier nicht beachtet. */
    public static boolean isValidKennzeichen(String eingabe){
        boolean isValid = true;

        return isValid;
    }

}
