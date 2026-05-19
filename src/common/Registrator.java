package common;

import java.io.Serializable;

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
