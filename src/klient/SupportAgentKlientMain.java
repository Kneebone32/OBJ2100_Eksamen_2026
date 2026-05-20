package klient;

import klient.controller.SupportAgentController;
import klient.view.SupportAgentConsoleView;
import common.Nettverk;

// Inngangspunktet for support agent klient
public class SupportAgentKlientMain {
    public static void main(String[] args) {
        try {
            SupportAgentController kontroller = new SupportAgentController();

            kontroller.kobleTil(Nettverk.HOSTNAME, Nettverk.PORT);

            SupportAgentConsoleView view = new SupportAgentConsoleView(kontroller);
            view.displayMenu();
        } catch (Exception e) {
            System.out.println("Kunne ikke koble til server.");
        }
    }
    
}
