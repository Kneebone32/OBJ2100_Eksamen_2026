import java.util.Scanner;

public class RegistratorConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final RegistratorController controller;

    public void displayMenu(){
        while (true){
        System.out.println("Velkommen");
        System.out.println();
        System.out.println("1.Opprett ny henvendelse");
        System.out.println("2.Kanseller henvendelse");
        System.out.println("3.sjekk status ");
        System.out.println("4.Avslutt");
        System.out.println();
        System.out.print("Velg: ");

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
        System.out.println("Velg type henvendelse");
        System.out.println("1. Nettverksfeil");
        System.out.println("2. Serverfeil");
        System.out.println("3. Klientfeil");

        System.out.print("Valg: ");
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

        System.out.print("Beskrivelse av henvendelse: ");
        String hendelseInput = scanner.nextLine();

        Melding melding = controller.opprettHenvendelse(type, hendelseInput);
        System.out.println(melding);
    }

    //Registrator kan kansellere en henvendelse
    private void kansellerHenvendelse(){
        System.out.print("Hendelse_id som skal kanselleres: ");
        int id = Integer.parseInt(scanner.nextLine());

        String respons = controller.cancel(id);
        System.out.println(respons);
    }

    //Registrator kan sjekke status for en henvendelse.
    private void sjekkHenvendelseStatus(){
        System.out.print("Henvendelse_id du vil sjekke: ");
        int id = Integer.parseInt(scanner.nextLine());

        String respons = controller.sjekkStatus(id);
        System.out.println(respons);
    }

}
