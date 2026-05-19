package server;
import common.*;
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
                Melding request = (Melding) input.readObject();
                Melding response = håndterForespørsel(request);
                output.writeObject(response);
                output.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Feil ved deserialisering av melding: " + e.getMessage());
        }
    }

    private Melding håndterForespørsel(Melding request) throws InterruptedException {
        switch (request.getCommand()) {
            case OPPRETT_HENVENDELSE:
                Henvendelse nyHenvendelse = (Henvendelse) request.getInnhold();
                int id = server.opprettHenvendelse(nyHenvendelse);
                return new Melding(Kommando.OPPRETT_HENVENDELSE, null, "OK", id);

            case HENT_HENVENDELSE_STATUS:
                int hentId = (int) request.getInnhold();
                Henvendelse henvendelse = server.hentHenvendelse(hentId);
        }
    }
}
