package common;

import java.io.Serializable;

public class Henvendelse implements Serializable {
    private final int henvendelseID;
    private HenvendelseStatus status;
    private HenvendelseType type;
    private String henvendelseInnhold;

    public Henvendelse(int henvendelseID, HenvendelseStatus status, HenvendelseType type, String henvendelseInnhold) {
        this.henvendelseID = henvendelseID;
        this.status = HenvendelseStatus.OPPRETTET;
        this.type = type;
        this.henvendelseInnhold = henvendelseInnhold;
    }

    static void henvendelseInnhold(Henvendelse henvendelse, String innhold) {
        henvendelse.henvendelseInnhold = innhold;
    }

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
}
