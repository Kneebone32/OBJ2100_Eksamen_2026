package server;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.net.ServerSocket;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import common.*;
import common.enums.*;
import server.util.LoggHandler;

// Hovedklassen for serveren som håndterer innkommende klienttilkoblinger
public class ServerMain {
    public static final int PORT = 11111;

    private final Map<Integer, Henvendelse> henvendelseRegistrer = new LinkedHashMap<>();
    private final LinkedBlockingQueue<Integer> henvendelseVenteListe = new LinkedBlockingQueue<>();

    private final AtomicInteger nesteHenvendelseID = new AtomicInteger(1);
    private final AtomicInteger nesteLoggID = new AtomicInteger(1);
    private final AtomicInteger nesteKlientID = new AtomicInteger(1);

    public static void main(String[] args) {
        new ServerMain().start();
    }
        
    // Starter serveren og håndterer innkommende klienttilkoblinger
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Ny klient koblet til: ");
            new Thread(new KlientHandler(socket, this)).start();
            }
            } catch (Exception e) {
                System.out.println("Server feil: " + e.getMessage());
            }
        }
        
        // Oppretter en ny henvendelse og legger den til i registret og vente listen
        public synchronized int opprettHenvendelse(String innhold, HenvendelseType type) {
            int id = nesteHenvendelseID.getAndIncrement();
            Henvendelse nyHenvendelse = new Henvendelse(
                id, 
                type,
                innhold
                );
            henvendelseRegistrer.put(id, nyHenvendelse);
            henvendelseVenteListe.add(id);
            loggHendelse(String.valueOf(id), "Henvendelse opprettet");
            return id;
        }

        // Oppdaterer statusen til en henvendelse og fjerner den fra vente listen hvis den blir kansellert
        public synchronized void oppdaterHenvendelseStatus(int id, HenvendelseStatus nyStatus) {
            Henvendelse henvendelse = henvendelseRegistrer.get(id);
            if (henvendelse != null) {
                henvendelse.setStatus(nyStatus);
                loggHendelse(String.valueOf(id), "Henvendelse oppdatert til status: " + nyStatus);

                if (nyStatus == HenvendelseStatus.KANSELLERT) {
                    henvendelseVenteListe.remove(id);
                }
            }
        }

        // Kansellerer en henvendelse hvis den er i OPPRETTET status og fjerner den fra vente listen
        public synchronized SvarKode kansellerHenvendelse(int id) {
            Henvendelse henvendelse = henvendelseRegistrer.get(id);
            if (henvendelse != null && henvendelse.getStatus() == HenvendelseStatus.OPPRETTET) {
                henvendelse.setStatus(HenvendelseStatus.KANSELLERT);
                henvendelseVenteListe.remove(id);
                loggHendelse(String.valueOf(id), "Henvendelse kansellert");
                return SvarKode.SUKSESS;
            }
            return SvarKode.ERROR;
        }

        // Logger en hendelse ved å opprette en HendelseLoggLinje og skrive den til fil ved hjelp av LoggHandler
        public synchronized void loggHendelse(String referanse, String innhold) {
            int id = nesteLoggID.getAndIncrement();
            HendelseLoggLinje hendelseLogg = new HendelseLoggLinje(id, referanse, innhold);
            LoggHandler.skrivLoggLinje(hendelseLogg);
        }

        // Registrerer en klient ved å tildele den en unik ID og opprette en Agent eller Registrator basert på klient rolle 
        public synchronized int registrerKlient(KlientInfo klientInfo) {
            int id = nesteKlientID.getAndIncrement();
            switch (klientInfo.getKlientRolle()) {
                case AGENT:
                    Agent agent = new Agent(id, klientInfo.getKlientNavn(), klientInfo.getKlientRolle());
                    break;
                case REGISTRATOR:
                    Registrator registrator = new Registrator(id, klientInfo.getKlientNavn(), klientInfo.getKlientRolle());
                    break;
            }
            return id;
        }

        // Henter en henvendelse basert på ID fra registret
        public synchronized Henvendelse hentHenvendelse(int id) {
            return henvendelseRegistrer.get(id);
        }

        // Henter neste ledige henvendelse ID fra vente listen, blokkerer hvis ingen er tilgjengelig
        public Integer hentNesteHenvendelseID() throws InterruptedException {
            return henvendelseVenteListe.take();
        }

        // Henter statusen til en henvendelse basert på ID
        public synchronized HenvendelseStatus hentHenvendelseStatus(int id) {
            Henvendelse henvendelse = henvendelseRegistrer.get(id);
            return henvendelse != null ? henvendelse.getStatus() : null;
        }
}
