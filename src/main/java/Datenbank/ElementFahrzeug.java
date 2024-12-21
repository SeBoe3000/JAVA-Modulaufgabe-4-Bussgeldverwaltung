package Datenbank;

public class ElementFahrzeug {
    private String Kennzeichen;
    private String Modell;
    private String Hersteller;
    private Integer Motorleistung;

    public ElementFahrzeug() {
    }

    public ElementFahrzeug(String kennzeichen, String modell, String hersteller, Integer motorleistung) {
        Kennzeichen = kennzeichen;
        Modell = modell;
        Hersteller = hersteller;
        Motorleistung = motorleistung;
    }

    public String getKennzeichen() {
        return Kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        Kennzeichen = kennzeichen;
    }

    public String getModell() {
        return Modell;
    }

    public void setModell(String modell) {
        Modell = modell;
    }

    public String getHersteller() {
        return Hersteller;
    }

    public void setHersteller(String hersteller) {
        Hersteller = hersteller;
    }

    public Integer getMotorleistung() {
        return Motorleistung;
    }

    public void setMotorleistung(Integer motorleistung) {
        Motorleistung = motorleistung;
    }
}
