package server;
import common.*;
import common.enums.HenvendelseStatus;
import common.enums.Kommando;
import common.enums.SvarKode;

import java.io.*;
import java.net.Socket;

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
        //TODO: unntakshåntering på typetvang
    private Melding håndterForespørsel(Melding forespørsel) throws InterruptedException {
        switch (forespørsel.getKommando()) {
            case OPPRETT_HENVENDELSE:
                Henvendelse nyHenvendelse = (Henvendelse) forespørsel.getInnhold();
                int id = server.opprettHenvendelse(nyHenvendelse);
                return new Melding(Kommando.OPPRETT_HENVENDELSE, null, SvarKode.SUKSESS, id);

            case HENT_HENVENDELSE_STATUS:
                int hentId = (int) forespørsel.getInnhold();
                HenvendelseStatus henvendelseStatus = server.hentHenvendelseStatus(hentId);
                return new Melding(henvendelseStatus, SvarKode.SUKSESS);



            case KANSELLER_HENVENDELSE:
                int henvendelseID = (int) forespørsel.getInnhold();
                SvarKode svarKode = server.kansellerHenvendelse();
                if(svarKode == SvarKode.ERROR){
                    return new Melding("Kunne ikke kansellere henvendelse", svarKode);
                }
                return new Melding("Henvendelse ble kansellert", svarKode);

            case HENT_LEDIG_HENVENDELSE:
                int nesteHenvendelseID = server.hentNesteHenvendelseID();
                Henvendelse henvendelse = server.hentHenvendelse(nesteHenvendelseID);
                return new Melding(henvendelse, SvarKode.SUKSESS);

            case SETT_HENVENDELSE_FULLFØRT:
            
            default: 
                return new Melding("Ukjent forespørsel", SvarKode.ERROR);

        }
        
    }


    private boolean validerRolle(){

    }
}
