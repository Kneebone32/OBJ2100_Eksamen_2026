package common;

import java.io.Serializable;

import common.enums.HenvendelseType;
import common.enums.KlientRolle;
import common.enums.Kommando;
import common.enums.SvarKode;

//Klasse for meldinger som skal bli sendt mellom klient og server. 
public class Melding implements Serializable {
    private final Kommando kommando;
    private final int henvendelseID;
    private final Object innhold;
    private final SvarKode responsStatus;
    private final HenvendelseType henvendelseType;
    private final KlientRolle klientRolle;

    //Konstruktøren for meldingene hvor vi har oppgitt kommando, innhold, responsstatus
    //og en henvendelseID som skal være unik for hver melding.
    public Melding(Kommando kommando, Object innhold, SvarKode responsStatus, int henvendelseID, HenvendelseType henvendelseType, KlientRolle klientRolle) {
        this.kommando = kommando;
        this.innhold = innhold;
        this.responsStatus = responsStatus;
        this.henvendelseID = henvendelseID;
        this.henvendelseType = henvendelseType;
        this.klientRolle = klientRolle;
        
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

    public KlientRolle getKlientRolle() {
        return klientRolle;
    }

    //metoder for å skille på opprettHenvendelse, forespørsel og svar.
    public static Melding opprettHenvendelse(String innhold, HenvendelseType henvendelseType, KlientRolle klientRolle){
        return new Melding(Kommando.OPPRETT_HENVENDELSE, innhold, null, 0, henvendelseType, klientRolle);
    }

    public static Melding forespørsel(Kommando kommando, Object innhold, KlientRolle klientRolle){
        return new Melding(kommando, innhold, null, 0, null, klientRolle);
    }

    public static Melding svar(Object innhold, SvarKode svarKode){
        return new Melding(null, innhold, svarKode, 0, null, null);
    }

    public static Melding svarMedID(Object innhold, SvarKode svarKode, int id){
        return new Melding(null, innhold, svarKode, id, null, null);
    }

    public static Melding registrerKlient(Kommando kommando, Object klientRolle){
        return new Melding(kommando, klientRolle, null, 0, null, null);
    }

}
