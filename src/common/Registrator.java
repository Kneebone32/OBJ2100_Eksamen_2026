package common;

import java.io.Serializable;

//Klasse for registratorer hvor vi har oppgitt registratorID og navn.
//Klassen implementerer Serializable slik at den kan bli sendt mellom klient og server.
public class Registrator implements Serializable {
    private final int registratorID;
    private final String navn;

    public Registrator(int registratorID, String navn) {
        this.registratorID = registratorID;
        this.navn = navn;
    }

    public int getRegistratorID() {
        return registratorID;
    }

    public String getNavn() {
        return navn;
    }
}
