package klient.view;

import java.io.IOException;
import java.util.Scanner;
import klient.controller.SupportAgentController;
import common.Melding;

public class SupportAgentConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final SupportAgentController kontroller;

    // Konstruktør
    public SupportAgentConsoleView(SupportAgentController kontroller) {
        this.kontroller = kontroller;
    }

    public void displayMenu(){
        while(true){
        System.out.println("Velkommen");
        System.out.println();
        System.out.println("1.Sett en henvendelse til fullført");
        System.out.println("2.Hent en ny henvendelse");
        System.out.println("3.Avslutt");
        System.out.println();
        System.out.print("Velg: ");

        String valg = scanner.nextLine();

        switch(valg){
            case "1":
                fullforHenvendelse();
                break;

            case "2":
                hentNyHenvendelse();
                break;

            case "3":
                System.out.println("Velkommen igjen");
                return;

            default:
                System.out.println("Ugyldig valg");
        }
    }
    }

    //Agent setter en henvendelse til fullført
    private void fullforHenvendelse(){
        System.out.print("Hendelse_id som skal fullføres: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Kommentar: ");
        String kommentar = scanner.nextLine();

        try {
            Melding respons = kontroller.settFullført(id);
            System.out.println(respons);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Agent henter neste ledige henvendelse
    private void hentNyHenvendelse() {
        try {
            Melding respons = kontroller.hentHenvendelse();
            System.out.println(respons);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}

