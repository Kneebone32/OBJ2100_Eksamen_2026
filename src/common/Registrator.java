package common;

import java.io.Serializable;

import common.enums.KlientRolle;

//Klasse for registratorer hvor vi har oppgitt registratorID og navn.
//Klassen implementerer Serializable slik at den kan bli sendt mellom klient og server.
public class Registrator implements Serializable {
    private final int registratorID;
    private final String navn;
    private final KlientRolle klientRolle;

    public Registrator(int registratorID, String navn, KlientRolle klientRolle) {
        this.registratorID = registratorID;
        this.navn = navn;
        this.klientRolle = klientRolle;
    }

    public int getRegistratorID() {
        return registratorID;
    }

    public String getNavn() {
        return navn;
    }

    public KlientRolle getKlientRolle() {
        return klientRolle;
    }

    
}
