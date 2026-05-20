package klient.view;

import java.io.IOException;
import java.util.Scanner;

import klient.controller.BehandleHenvendelse;
import klient.controller.SupportAgentController;
import common.Melding;
import common.Henvendelse;

public class SupportAgentConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final SupportAgentController kontroller;

    // Konstruktør
    public SupportAgentConsoleView(SupportAgentController kontroller) {
        this.kontroller = kontroller;
    }

    public void displayMenu(){

        // Registrerer navn på agent
        String navn;
        while (true) {
            System.out.println("Vennligst skriv inn navnet ditt:");
            navn = scanner.nextLine();
            if (!navn.isBlank()) {
                break;
            }
            System.out.println("Navnet kan ikke være tomt");
        }

        try {
            Melding respons = kontroller.registrerSupportAgent(navn);
            System.out.println(respons.getInnhold());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Kunne ikke registrere agent: " + e.getMessage());
            return;
        }
        System.out.println("Velkommen, " + navn + "!");

        // Meny
        while (true) {
            System.out.println("\n");
            System.out.println("Hva ønsker du å gjøre?");
            System.out.println();
            System.out.println("1. Hent en ny henvendelse");
            System.out.println("2. Avslutt");
            System.out.println();
            System.out.println("Skriv et tall for å velge: ");

            String valg = scanner.nextLine();

            switch(valg){
                case "1":
                    hentNyHenvendelse();
                    break;

                case "2":
                    System.out.println("Velkommen igjen");
                    return;

                default:
                    System.out.println("Ugyldig input");
            }
        }
    }

    //Agent henter neste ledige henvendelse og velger fullført eller ikke
    private void hentNyHenvendelse() {
        try {
            Melding respons = kontroller.hentHenvendelse();
            Henvendelse innhold = (Henvendelse) respons.getInnhold();
            BehandleHenvendelse behandleHenvendelse = new BehandleHenvendelse(innhold);
            behandleHenvendelse.behandleHenvendelse();

            System.out.println("Henvendelse ID: " + innhold.getHenvendelseID() + "\n"
                               + innhold.getHenvendelseInnhold() + "\n");
            
            System.out.println("Fullfør?" + "\n"
                               + "1. Ja" + "\n"
                               + "2. Nei" + "\n"
                               + "Skriv 1 eller 2: ");
            String valg = scanner.nextLine();
            if (valg.equals("1")) {
                fullførHenvendelse(innhold.getHenvendelseID());
                System.out.println("Henvendelsen er fullført");
            } else if (valg.equals("2")) {
                fullførHenvendelse(innhold.getHenvendelseID());
                System.out.println("Dette er ikke implementert enda. Henvendelsen settes som fullført");
            } else {
                System.out.println("Ugyldig input");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Kunne ikke hente henvendelse: " + e.getMessage());
        }
    }

    //Agent setter en henvendelse til fullført
    private void fullførHenvendelse(int id){
        try {
            Melding respons = kontroller.settFullført(id);
            System.out.println(respons.getInnhold());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Kunne ikke fullføre henvendelse: " + e.getMessage());
        }
    }

    


}

