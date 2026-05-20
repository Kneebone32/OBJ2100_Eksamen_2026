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
    public Melding(Kommando kommando, Object innhold, SvarKode responsStatus, int henvendelseID, HenvendelseType henvendelseType) {
        this.kommando = kommando;
        this.innhold = innhold;
        this.responsStatus = responsStatus;
        this.henvendelseID = henvendelseID;
        this.henvendelseType = henvendelseType;
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

    //metoder for å skille på opprettHenvendelse, forespørsel og svar.
    public static Melding opprettHenvendelse(String innhold, HenvendelseType henvendelseType){
        return new Melding(Kommando.OPPRETT_HENVENDELSE, innhold, null, 0, henvendelseType);
    }

    public static Melding forespørsel(Kommando kommando, Object innhold){
        return new Melding(kommando, innhold, null, 0, null);
    }

    public static Melding svar(Object innhold, SvarKode svarKode){
        return new Melding(null, innhold, svarKode, 0, null);
    }

    public static Melding svarMedID(Object innhold, SvarKode svarKode, int id){
        return new Melding(null, innhold, svarKode, id, null);
    }

    public static Melding registrerKlient(Kommando kommando, Object klientRolle){
        return new Melding(kommando, klientRolle, null, 0, null);
    }

}
