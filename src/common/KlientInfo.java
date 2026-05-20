package common;

import java.io.Serializable;
import common.enums.KlientRolle;

//Klasse for å kunne sende klientinfo (navn og rolle) som et Objekt fra Klient til Server via Meldingsklassen
public class KlientInfo implements Serializable{
    private final String klientNavn;
    private final KlientRolle klientRolle; 

    public KlientInfo(String klientNavn, KlientRolle klientRolle){
        this.klientNavn = klientNavn;
        this.klientRolle = klientRolle;
    }

    public String getKlientNavn() {
        return klientNavn;
    }

    public KlientRolle getKlientRolle() {
        return klientRolle;
    }
    
}
