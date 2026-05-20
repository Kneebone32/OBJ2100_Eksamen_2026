package server;
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
    public static final int PORT = Nettverk.PORT;

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
            System.out.println("Server kjører");
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
            loggHendelse("Henvendelse id:" + String.valueOf(id), "Henvendelse opprettet");
            return id;
        }

        // Setter en henvendelse fra OPPRETTET til BEHANDLER
        public synchronized Henvendelse hentHenvendelse(int id) {
            Henvendelse henvendelse = henvendelseRegistrer.get(id);
            if (henvendelse != null && henvendelse.getStatus() == HenvendelseStatus.OPPRETTET) {
                henvendelse.setStatus(HenvendelseStatus.BEHANDLER);
                loggHendelse("Henvendelse id:" + String.valueOf(id), "Henvendelse hentet for behandling");
                return henvendelse;
            }
            return null;
        }

        // Kansellerer en henvendelse hvis den er i OPPRETTET status og fjerner den fra vente listen
        public synchronized SvarKode kansellerHenvendelse(int id) {
            Henvendelse henvendelse = henvendelseRegistrer.get(id);
            if (henvendelse != null && henvendelse.getStatus() == HenvendelseStatus.OPPRETTET) {
                henvendelse.setStatus(HenvendelseStatus.KANSELLERT);
                henvendelseVenteListe.remove(id);
                loggHendelse("Henvendelse id:" + String.valueOf(id), "Henvendelse kansellert");
                return SvarKode.SUKSESS;
            }
            return SvarKode.ERROR;
        }

        // Setter en henvendelse fra BEHANDLER til FULLFØRT
        public synchronized SvarKode settHenvendelseFullført(int id) {
            Henvendelse henvendelse = henvendelseRegistrer.get(id);
            if (henvendelse != null && henvendelse.getStatus() == HenvendelseStatus.BEHANDLER) {
                henvendelse.setStatus(HenvendelseStatus.FULLFØRT);
                loggHendelse("Henvendelse id:" + String.valueOf(id), "Henvendelse fullført");
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
