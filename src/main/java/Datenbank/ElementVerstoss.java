package Datenbank;

public class ElementVerstoss {
    private Integer VerstossID;
    private String Beschreibung;
    private Float Strafe;
    private Integer Punkte;
    private Integer Fahrverbot;

    public ElementVerstoss() {
    }

    public ElementVerstoss(Integer verstossID, String beschreibung, Float strafe, Integer punkte, Integer fahrverbot) {
        VerstossID = verstossID;
        Beschreibung = beschreibung;
        Strafe = strafe;
        Punkte = punkte;
        Fahrverbot = fahrverbot;
    }

    public Integer getVerstossID() {
        return VerstossID;
    }

    public void setVerstossID(Integer verstossID) {
        VerstossID = verstossID;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public Float getStrafe() {
        return Strafe;
    }

    public void setStrafe(Float strafe) {
        Strafe = strafe;
    }

    public Integer getPunkte() {
        return Punkte;
    }

    public void setPunkte(Integer punkte) {
        Punkte = punkte;
    }

    public Integer getFahrverbot() {
        return Fahrverbot;
    }

    public void setFahrverbot(Integer fahrverbot) {
        Fahrverbot = fahrverbot;
    }
}
