package klient.controller;

import java.io.IOException;
import common.*;
import common.enums.*;
import klient.KlientMain;

// Metoder som agenter bruker mot serveren
public class SupportAgentController extends KlientMain {

    public Melding hentHenvendelse() throws IOException, ClassNotFoundException {
        Melding forespørsel = Melding.forespørsel(Kommando.HENT_LEDIG_HENVENDELSE, null, KlientRolle.AGENT);
        return sendOgMotta(forespørsel);
    }

    public Melding settFullført(int id) throws IOException, ClassNotFoundException {
        Melding forespørsel = Melding.forespørsel(Kommando.SETT_HENVENDELSE_FULLFØRT, id, KlientRolle.AGENT);
        return sendOgMotta(forespørsel);
    }
    
}
