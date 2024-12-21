package Datenbank;

import java.util.Date;

public class ElementBussgeld {
    private Integer ID;
    private Date Tageszeit;
    private Integer VerstossID;
    private String Fahrzeug;

    public ElementBussgeld() {
    }

    public ElementBussgeld(Integer ID, Date tageszeit, Integer verstossID, String fahrzeug) {
        this.ID = ID;
        Tageszeit = tageszeit;
        VerstossID = verstossID;
        Fahrzeug = fahrzeug;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Date getTageszeit() {
        return Tageszeit;
    }

    public void setTageszeit(Date tageszeit) {
        Tageszeit = tageszeit;
    }

    public Integer getVerstossID() {
        return VerstossID;
    }

    public void setVerstossID(Integer verstossID) {
        VerstossID = verstossID;
    }

    public String getFahrzeug() {
        return Fahrzeug;
    }

    public void setFahrzeug(String fahrzeug) {
        Fahrzeug = fahrzeug;
    }
}
