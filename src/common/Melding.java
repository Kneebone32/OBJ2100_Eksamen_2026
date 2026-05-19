package common;

import java.io.Serializable;

public class Melding implements Serializable {
    private final Kommando kommando;
    private final Object innhold;
    private final String responsStatus;

    public Melding(Kommando kommando, Object innhold, String responsStatus) {
        this.kommando = kommando;
        this.innhold = innhold;
        this.responsStatus = responsStatus;
    }

    public Melding(Kommando kommando, Object innhold) {
        this(kommando, innhold, "OK");
    }

    public Kommando getCommand() {
        return kommando;
    }

    public Object getPayload() {
        return innhold;
    }

    public String getResponseStatus() {
        return responsStatus;
    }
}
