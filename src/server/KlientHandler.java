package server;
import common.*;
import common.enums.HenvendelseStatus;
import common.enums.HenvendelseType;
import common.enums.KlientRolle;
import common.enums.Kommando;
import common.enums.SvarKode;

import java.io.*;
import java.net.Socket;

//Klasse som styrer kommunikasjonen mellom Klient og ServerMain
public class KlientHandler implements Runnable {
    private final Socket socket;
    private final ServerMain server;

    public KlientHandler(Socket socket, ServerMain server) {
        this.socket = socket;
        this.server = server;
    }

    // Håndterer kommunikasjon med klienten
    @Override
    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                Melding forespørsel = (Melding) input.readObject();
                Melding svar = håndterForespørsel(forespørsel);
                output.writeObject(svar);
                output.flush();
            }
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            System.out.println("Feil ved deserialisering av melding: " + e.getMessage());
        }
    }

    //switch på forespørsler som kommer fra Klient
    private Melding håndterForespørsel(Melding forespørsel) throws InterruptedException {
        try {

        switch (forespørsel.getKommando()) {
            case OPPRETT_HENVENDELSE:
                return opprettHenvendelse(forespørsel);

            case HENT_HENVENDELSE_STATUS:
                return hentHenveldelseStatus(forespørsel);

            case KANSELLER_HENVENDELSE:
                return kansellerHenvendelse(forespørsel);

            case HENT_LEDIG_HENVENDELSE:
                return hentLedigHenvendelse(forespørsel);

            case SETT_HENVENDELSE_FULLFØRT:
                return settHenvendelseFullført(forespørsel);

            case REGISTRER_KLIENT:
                return registrerKlient(forespørsel);

            default:
                return Melding.svar("Ukjent forespørsel", SvarKode.ERROR);

        }
        } catch (ClassCastException | NullPointerException e) {
            return Melding.svar("Ugyldig melding", SvarKode.ERROR);
        }
        
    }

    //Oppretter en Henvendelse og returnerer id. Kun for Registrator
    private Melding opprettHenvendelse(Melding forespørsel){
        if(!validerRolle(forespørsel.getKommando(), forespørsel.getKlientRolle()))
            return Melding.svar("Kunne ikke opprette henvendelse", SvarKode.ERROR);

        String innhold = (String) forespørsel.getInnhold();
        HenvendelseType henvendelseType = forespørsel.getHenvendelseType();
        int id = server.opprettHenvendelse(innhold, henvendelseType);

        return Melding.svarMedID("Henvendelse opprettet", SvarKode.SUKSESS, id);
    }

    //Henter henvendelsesStatus på en henvendelse. Kun for Registrator
    private Melding hentHenveldelseStatus(Melding forespørsel){
        if(!validerRolle(forespørsel.getKommando(), forespørsel.getKlientRolle()) || forespørsel.getInnhold() == null){
            return Melding.svar("Kunne ikke hente status", SvarKode.ERROR);
        }

        int hentId = (int) forespørsel.getInnhold();
        HenvendelseStatus henvendelseStatus = server.hentHenvendelseStatus(hentId);

        if (henvendelseStatus == null){
            return Melding.svar("Kunne ikke hente status", SvarKode.ERROR);
        }

        return Melding.svar(henvendelseStatus, SvarKode.SUKSESS);
    }


    //Kansellerer en henvendelse. Kun for Registrator og hvis status == OPPRETTET
    private Melding kansellerHenvendelse(Melding forespørsel){
        if(!validerRolle(forespørsel.getKommando(), forespørsel.getKlientRolle()))
            return Melding.svar("Kunne ikke kansellere status", SvarKode.ERROR);
        int henvendelseID = (int) forespørsel.getInnhold();
        SvarKode svarKode = server.kansellerHenvendelse(henvendelseID);
        if(svarKode == SvarKode.ERROR){
            return Melding.svar("Kunne ikke kansellere henvendelse", svarKode);
        }
        return Melding.svar("Henvendelse ble kansellert", svarKode);
    }

    //Henter neste ledige Henvendelse fra arbeidskøen. Kun for Agent
    private Melding hentLedigHenvendelse(Melding forespørsel){
        Henvendelse henvendelse = null;
        try {
            if(!validerRolle(forespørsel.getKommando(), forespørsel.getKlientRolle()))
                return Melding.svar("Kunne ikke hente henvendelse", SvarKode.ERROR);
            Integer nesteHenvendelseID = server.hentNesteHenvendelseID();
            henvendelse = server.hentHenvendelse(nesteHenvendelseID);
                
        } catch (InterruptedException e) {
            return Melding.svar("Kunne ikke hente henvendelse", SvarKode.ERROR);
        }

        if(henvendelse == null){
            return Melding.svar("Kunne ikke hente henvendelse", SvarKode.ERROR);
        }
        return Melding.svar(henvendelse, SvarKode.SUKSESS);
    }

    //Setter en henvendelse til fullført. Kun for Agent og hvis status == BEHANDLER
    private Melding settHenvendelseFullført(Melding forespørsel){
        if(!validerRolle(forespørsel.getKommando(), forespørsel.getKlientRolle())){
            return Melding.svar("Kunne ikke endre status til fullført", SvarKode.ERROR);
        }
        int id = (int) forespørsel.getInnhold();
        SvarKode svarKode = server.settHenvendelseFullført(id);
        if(svarKode == SvarKode.ERROR){
            return Melding.svar("Kunne ikke endre status til fullført", SvarKode.ERROR);
        }

        return Melding.svar("Henvendelse ble satt til FULLFØRT", SvarKode.SUKSESS);
    }

    //Registrerer en ny tilkoblet Klient.
    private Melding registrerKlient(Melding forespørsel){
        KlientInfo klientInfo = (KlientInfo) forespørsel.getInnhold();
        server.registrerKlient(klientInfo);
            return Melding.svar("Klient ble opprettet", SvarKode.SUKSESS);
    }

    //Metode for å validere rollen til Klienten
    private boolean validerRolle(Kommando kommando, KlientRolle klientRolle){
        return switch (kommando) {
            case OPPRETT_HENVENDELSE, HENT_HENVENDELSE_STATUS, KANSELLER_HENVENDELSE, REGISTRER_KLIENT -> klientRolle == KlientRolle.REGISTRATOR;
            case HENT_LEDIG_HENVENDELSE, SETT_HENVENDELSE_FULLFØRT -> klientRolle == KlientRolle.AGENT;
            default -> false;
        };
    }

}

