import java.util.Scanner;

public class AgentConsoleView {

    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu(){
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


    private void fullforHenvendelse(){
        System.out.print("Hendelse_id som skal fullføres: ");
        int id = Integer.parseInt(scanner.nextLine());
    }

    private void hentNyHenvendelse(){
        System.out.print("Hent ny hendelse: ");
        int id = Integer.parseInt(scanner.nextLine());
    }
}
