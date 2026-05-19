package common;

import java.io.Serializable;

//Klasse for meldinger som skal bli sendt mellom klient og server. 
public class Melding implements Serializable {
    private final Kommando kommando;
    private final int henvendelseID;
    private final Object innhold;
    private final String responsStatus;

    //Konstruktøren for meldingene hvor vi har oppgitt kommando, innhold, responsstatus
    //og en henvendelseID som skal være unik for hver melding.
    public Melding(Kommando kommando, Object innhold, String responsStatus, int henvendelseID) {
        this.kommando = kommando;
        this.innhold = innhold;
        this.responsStatus = responsStatus;
        this.henvendelseID = henvendelseID;
    }

    //De forskjellige getters for å hente ut informasjonen i meldingene.
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
