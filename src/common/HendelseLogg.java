package common;

import java.time.LocalDateTime;

public class HendelseLogg {
    private final int loggID;
    private LocalDateTime tidsstempel;
    private String referanse;
    private String innhold;

    public HendelseLogg(int loggID, LocalDateTime tidsstempel, String referanse, String innhold) {
        this.loggID = loggID;
        this.tidsstempel = tidsstempel;
        this.referanse = referanse;
        this.innhold = innhold;
    }

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
