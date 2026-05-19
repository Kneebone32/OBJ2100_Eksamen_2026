package common;

import java.time.LocalDateTime;

//Denne klassen representerer en hendelse i loggen.
//Hver hendelse skal ha en unik loggID, et tidsstempel, en referanse og innholdet i hendelsen.
public class HendelseLoggLinje {
    private final int loggID;
    private LocalDateTime tidsstempel;
    private String referanse;
    private String innhold;

    //Konstruktøren for hendelselogg
    //hvor vi har lagt inn alle de nødvendige feltene for å kunne opprette en hendeelse i loggen.
    public HendelseLoggLinje(int loggID, String referanse, String innhold) {
        this.loggID = loggID;
        this.tidsstempel = LocalDateTime.now();
        this.referanse = referanse;
        this.innhold = innhold;
    }

    //Her er gettere for å hente ut informasjonen i hendelseslogggen
    //slik at den kan bli brukt i LoggHandler.
    public int getLoggID() {
        return loggID;
    }

    public LocalDateTime getTidsstempel() {
        return tidsstempel;
    }

    public String getReferanse() {
        return referanse;
    }

    public String getInnhold() {
        return innhold;
    }
}
