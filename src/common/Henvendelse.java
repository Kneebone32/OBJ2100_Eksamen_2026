package common;

import java.io.Serializable;
import java.time.LocalDateTime;

import common.enums.HenvendelseStatus;
import common.enums.HenvendelseType;

//Denne klassen representerer en henvendelse som kan bli
//opprettet av en klient og sendt til serveren.
//Henvendelsen skal ha en unik ID, en status, en type og innholdet i henvendelse.
public class Henvendelse implements Serializable {
    private final int henvendelseID;
    private final LocalDateTime tidsstempel;
    private HenvendelseStatus status;
    private HenvendelseType type;
    private String henvendelseInnhold;

    //Konstruktøren med de nødvendige parameterne for å opprette en henvendelse.
    public Henvendelse(int henvendelseID, HenvendelseType type, String henvendelseInnhold) {
        this.henvendelseID = henvendelseID;
        this.status = HenvendelseStatus.OPPRETTET;
        this.type = type;
        this.henvendelseInnhold = henvendelseInnhold;
        this.tidsstempel = LocalDateTime.now();
    }

    //Metode for å oppdatere innholdet som er i henvendelsen.
    static void henvendelseInnhold(Henvendelse henvendelse, String innhold) {
        henvendelse.henvendelseInnhold = innhold;
    }

    //Gettere og settere for å hente ut og oppdatere informasjonen i henvendelsen.
    public int getHenvendelseID() {
        return henvendelseID;
    }

    public HenvendelseStatus getStatus() {
        return status;
    }

    public HenvendelseType getType() {
        return type;
    }

    public String getHenvendelseInnhold() {
        return henvendelseInnhold;
    }

    public void setStatus(HenvendelseStatus status) {
        this.status = status;
    }

    public void setType(HenvendelseType type) {
        this.type = type;
    }

    public LocalDateTime getTidsstempel() {
        return tidsstempel;
    }
}
