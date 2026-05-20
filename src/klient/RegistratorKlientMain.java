package klient;

import klient.controller.RegistratorController;
import common.Nettverk;
import klient.view.RegistratorConsoleView;

// Inngangspunktet for registrator klient
public class RegistratorKlientMain {
    public static void main(String[] args) {
        try {
            RegistratorController kontroller = new RegistratorController();

            kontroller.kobleTil(Nettverk.HOSTNAME, Nettverk.PORT);

            RegistratorConsoleView view = new RegistratorConsoleView(kontroller);
            view.displayMenu();
        } catch (Exception e) {
            System.out.println("Kunne ikke koble til server.");
        }
    }
}
