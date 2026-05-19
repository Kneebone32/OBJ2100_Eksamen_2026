package common;

import java.io.Serializable;

public class Melding implements Serializable {
    private final Kommando kommando;
    private final int henvendelseID;
    private final Object innhold;
    private final String responsStatus;

    public Melding(Kommando kommando, Object innhold, String responsStatus, int henvendelseID) {
        this.kommando = kommando;
        this.innhold = innhold;
        this.responsStatus = responsStatus;
        this.henvendelseID = henvendelseID;
    }

    public Kommando getCommand() {
        return kommando;
    }

    public int getHenvendelseInnhold(){
        return henvendelseID;
    }

    public Object getInnhold() {
        return innhold;
    }

    public String getResponseStatus() {
        return responsStatus;
    }

}
