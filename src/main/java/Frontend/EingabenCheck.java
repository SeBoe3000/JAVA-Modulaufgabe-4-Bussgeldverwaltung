package Frontend;

public class EingabenCheck {
    // Verwendet für das Feld Strafe
    public static boolean isValidFloat(String eingabe){
        boolean isValid = true;
        Integer anzahlPunkte = 0;
        Integer anzahlZahl = 0;

        // Nur Zahlen und Punkte sind zulässige Zeichen
        // Nur ein Punkt darf vorhanden sein.
        // Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein. (Dadurch Überprüfung auf nicht leerer Wert)
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


    public static boolean isValidInteger(String eingabe){
        boolean isValid = true;
        Integer anzahlZahl = 0;

        // Nur Zahlen zulässige Zeichen
        // Mindestens eine Zahl zwischen 1 und 9 muss angegeben sein. (Dadurch Überprüfung auf nicht leerer Wert)
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
}
