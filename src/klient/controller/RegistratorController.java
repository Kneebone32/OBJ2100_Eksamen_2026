package klient.controller;

import java.io.IOException;
import common.*;
import common.enums.*;
import klient.KlientMain;

// Metoder som registratoren bruker mot serveren
public class RegistratorController extends KlientMain {

    public Melding opprettHenvendelse(String innhold, HenvendelseType type) throws IOException, ClassNotFoundException {
        Melding forespørsel = Melding.opprettHenvendelse(innhold, type, KlientRolle.REGISTRATOR);
        return sendOgMotta(forespørsel);
    }

    public Melding kansellerHenvendelse(int henvendelseID) throws IOException, ClassNotFoundException {
        Melding forespørsel = Melding.forespørsel(Kommando.KANSELLER_HENVENDELSE, henvendelseID, KlientRolle.REGISTRATOR);
        return sendOgMotta(forespørsel);
    }

    public Melding hentStatus(int henvendelseID) throws IOException, ClassNotFoundException {
        Melding forespørsel = Melding.forespørsel(Kommando.HENT_HENVENDELSE_STATUS, henvendelseID, KlientRolle.REGISTRATOR);
        return sendOgMotta(forespørsel);
    }
}
