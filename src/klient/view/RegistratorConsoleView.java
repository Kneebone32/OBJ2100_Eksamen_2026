package klient.view;

import java.io.IOException;
import java.util.Scanner;
import klient.controller.RegistratorController;
import common.enums.HenvendelseType;
import common.enums.SvarKode;
import common.Melding;

public class RegistratorConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final RegistratorController kontroller;

    // Konstruktør
    public RegistratorConsoleView(RegistratorController kontroller) {
        this.kontroller = kontroller;
    }

    public void displayMenu(){

        // Registrerer navn på registrator
        String navn;
        while (true) {
            System.out.print("Vennligst skriv inn navnet ditt: ");
            navn = scanner.nextLine();
            if (!navn.isBlank()) {
                break;
            }
            System.out.println("Navnet kan ikke være tomt");
        }

        try {
            Melding respons = kontroller.registrerRegistrator(navn);
            System.out.println(respons.getInnhold());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Kunne ikke registrere registrator: " + e.getMessage());
            return;
        }
        System.out.println("Velkommen, " + navn + "!");
        
        // Meny
        while (true){
            System.out.println("\n");
            System.out.println("Hva ønsker du å gjøre?");
            System.out.println();
            System.out.println("1. Opprett ny henvendelse");
            System.out.println("2. Kanseller henvendelse");
            System.out.println("3. Sjekk status");
            System.out.println("4. Avslutt");
            System.out.println();
            System.out.print("Skriv et tall for å velge: ");

            String valg = scanner.nextLine();

            switch(valg){
                case "1":
                opprettHenvendelse();
                break;

                case "2":
                    kansellerHenvendelse();
                    break;

                case "3":
                    sjekkHenvendelseStatus();
                    break;

                case "4":
                    System.out.println("Velkommen igjen");
                    return;

                default:
                    System.out.println("Ugyldig valg");
            }
        }
    }


    /*Registrator oppretter en ny henvendelse. Her velger registrator
      en kategori og skriver en beskrivelse om henvendelsen.
    */
    private void opprettHenvendelse() {
        System.out.println();
        System.out.println("Velg type henvendelse");
        System.out.println();
        System.out.println("1. Nettverksfeil");
        System.out.println("2. Serverfeil");
        System.out.println("3. Klientfeil");
        System.out.println();
        System.out.print("Skriv et tall for å velge: ");
        String valg = scanner.nextLine();


        HenvendelseType type;
        switch (valg){
            case "1":
                type = HenvendelseType.Nettverksfeil;
                break;

            case "2":
                type = HenvendelseType.Serverfeil;
                break;

            case "3":
                type = HenvendelseType.Klientfeil;
                break;

            default:
                System.out.println("Ugyldig valg");
                return;
        }

        System.out.println();
        System.out.print("Beskrivelse av henvendelse: ");
        String hendelseInput = scanner.nextLine();
        System.out.println();

        try {
            Melding respons = kontroller.opprettHenvendelse(hendelseInput, type);
            System.out.println(respons.getInnhold());

            if (respons.getResponseStatus() == SvarKode.SUKSESS) {
                System.out.println("Henvendelse ID: " + respons.getHenvendelseID());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Kunne ikke opprette henvendelse: " + e.getMessage());
        }
    }

    //Registrator kan kansellere en henvendelse
    private void kansellerHenvendelse() {
        System.out.print("Oppgi ID til henvendelsen som skal kanselleres: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            Melding respons = kontroller.kansellerHenvendelse(id);
            System.out.println(respons.getInnhold());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Kunne ikke kansellere henvendelse: " + e.getMessage());
        }
    }

    //Registrator kan sjekke status for en henvendelse.
    private void sjekkHenvendelseStatus() {
        System.out.print("Oppgi ID til henvendelsen du vil sjekke: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            Melding respons = kontroller.hentStatus(id);
            System.out.println(respons.getInnhold());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Kunne ikke sjekke status til henvendelse: " + e.getMessage());
        }
    }

}
