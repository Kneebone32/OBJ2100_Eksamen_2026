package server.util;

import common.HendelseLogg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


//Klasse som håndterer lesing og lagring av logg til/fra fil på en trådsikker måte
public class LoggHandler {


    //Funksjon for å skrive en logglinje til fil. Bruker synchronized for trådsikker skriving
    public synchronized static String skrivLoggLinje(HendelseLogg hendelse) throws NullPointerException{
        File fil = new File("logg/henvendelseLogg.txt");
        File loggmappe = fil.getParentFile(); 

        if(loggmappe != null && !loggmappe.exists()){
            loggmappe.mkdirs();
        }


        int loggId = hendelse.getLoggID(); 
        LocalDateTime loggDatoTid = hendelse.getTidsstempel();
        String loggReferanse = hendelse.getReferanse();
        String loggInnhold = hendelse.getInnhold();

        if(loggId == 0 || loggDatoTid == null || loggReferanse == null || loggInnhold == null){
            throw new NullPointerException("Ett av feltene er null");
        }

        try (PrintWriter printer = new PrintWriter(new FileWriter(fil, true))) {
            if(!fil.exists() || !fil.isFile()){
                fil.createNewFile();
        }
            printer.println(loggId + ";" + loggDatoTid + ";" + loggReferanse + ";" + loggInnhold);
        } catch (IOException e) {
            System.out.println("Noe gikk feil ved skriving av logg til fil");
        }

        return "Logglinje skrevet til fil";
    }


    //Funksjon for å hente alle logglinjer fra fil. Bruker synchronized for trådsikker lesing og returerer alle linjene i en arraylist
    public synchronized static List<String> hentHeleLoggFraFil(){
        File fil = new File("logg/henvendelseLogg.txt");
        List<String> loggLinjer = new ArrayList<>();

        if(!fil.exists() || !fil.isFile()){
            System.out.println("Fil ikke funnet");
            return loggLinjer;
        }

        try (BufferedReader leser = new BufferedReader(new FileReader(fil))) {
            String linje;

            while((linje = leser.readLine()) != null){
                String[] linjeSplittet = linje.split(";");

                if(linjeSplittet.length < 4) continue;
                if(linjeSplittet[0].trim().length() < 1 ) continue;
                if(!erGyldigDato(linjeSplittet[1].trim())) continue;
                if(linjeSplittet[2].trim().length() < 1) continue;
                if(linjeSplittet[3].trim().length() < 1) continue;

                loggLinjer.add(linje);

            }
        } catch (IOException e) {
            System.out.println("Noe gikk galt ved lesing av loggfil");
        }

        return loggLinjer;
    }


    private static boolean erGyldigDato(String dato){
        try {
            LocalDateTime.parse(dato);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
