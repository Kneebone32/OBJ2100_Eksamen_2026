package server;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.net.ServerSocket;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import common.*;

import common.Henvendelse;

// Hovedklassen for serveren som håndterer innkommende klienttilkoblinger
public class ServerMain {
    public static final int PORT = 11111;

    private final Map<Integer, Henvendelse> henvendelseRegistrer = new LinkedHashMap<>();
    private final LinkedBlockingQueue<Integer> henvendelseVenteListe = new LinkedBlockingQueue<>();

    private final AtomicInteger nesteHenvendelseID = new AtomicInteger(1);

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
        

        public synchronized int opprettHenvendelse(Henvendelse henvendelse) {
            int id = nesteHenvendelseID.getAndIncrement();
            Henvendelse nyHenvendelse = new Henvendelse(
                id, 
                HenvendelseStatus.OPPRETTET,
                henvendelse.getType(),
                henvendelse.getHenvendelseInnhold() 
                );
            henvendelseRegistrer.put(id, nyHenvendelse);
            henvendelseVenteListe.add(id);
            return id;
        }

        public synchronized Henvendelse hentHenvendelse(int id) {
            return henvendelseRegistrer.get(id);
        }

        public synchronized Integer hentNesteHenvendelseID() throws InterruptedException {
            return henvendelseVenteListe.take();
        }


}
