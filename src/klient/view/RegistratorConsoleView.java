import java.util.Scanner;

public class RegistratorConsoleView {

    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu(){
        System.out.println("Velkommen");
        System.out.println();
        System.out.println("1.Opprett ny henvendelse");
        System.out.println("2.Kanseller henvendelse");
        System.out.println("3.Avslutt");
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
                System.out.println("Velkommen igjen");
                return;

            default:
                System.out.println("Ugyldig valg");
        }
    }


    //Registrator oppretter en ny henvendelse.
    private void opprettHenvendelse() {
        System.out.print("Beskrivelse av henvendelse: ");
        String hendelseInput = scanner.nextLine();
    }

    //Registrator kan kansellere en henvendelse
    private void kansellerHenvendelse(){
        System.out.print("Hendelse_id som skal kanselleres: ");
        int id = Integer.parseInt(scanner.nextLine());
    }
}
