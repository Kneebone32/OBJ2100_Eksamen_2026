package common;

import java.io.Serializable;

import common.enums.HenvendelseType;
import common.enums.Kommando;
import common.enums.SvarKode;

//Klasse for meldinger som skal bli sendt mellom klient og server. 
public class Melding implements Serializable {
    private final Kommando kommando;
    private final int henvendelseID;
    private final Object innhold;
    private final SvarKode responsStatus;
    private final HenvendelseType henvendelseType;

    //Konstruktøren for meldingene hvor vi har oppgitt kommando, innhold, responsstatus
    //og en henvendelseID som skal være unik for hver melding.
    public Melding(Kommando kommando, Object innhold, SvarKode responsStatus, int henvendelseID) {
        this.kommando = kommando;
        this.innhold = innhold;
        this.responsStatus = responsStatus;
        this.henvendelseID = henvendelseID;
        this.henvendelseType = null;
    }

    //brukes som svarmelding fra server
    public Melding(Object innhold, SvarKode responsStatus){
        this.innhold = innhold;
        this.responsStatus = responsStatus;
        henvendelseID = 0;
        kommando = null;
        this.henvendelseType = null;
    }

    //kan brukes til opprettelse av henvendelse
    public Melding(Kommando kommando, Object innhold, HenvendelseType henvendelseType){
        this.kommando = kommando;
        this.henvendelseType = henvendelseType;
        this.innhold = innhold;
        this.responsStatus = null;
        this.henvendelseID = 0;
    }


    //De forskjellige getters for å hente ut informasjonen i meldingene.
    public Kommando getKommando() {
        return kommando;
    }

    public int getHenvendelseID(){
        return henvendelseID;
    }

    public Object getInnhold() {
        return innhold;
    }

    public SvarKode getResponseStatus() {
        return responsStatus;
    }

    public HenvendelseType getHenvendelseType(){
        return henvendelseType;
    }

}
