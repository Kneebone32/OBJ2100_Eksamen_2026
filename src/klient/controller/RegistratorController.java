package klient.controller;

import java.io.IOException;
import common.*;
import common.enums.*;
import klient.BaseKlient;

// Metoder som registratoren bruker mot serveren
public class RegistratorController extends BaseKlient {

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

    public Melding registrerRegistrator(String navn) throws IOException, ClassNotFoundException {
        KlientInfo klientInfo = new KlientInfo(navn, KlientRolle.REGISTRATOR);
        Melding foresprørsel = Melding.registrerKlient(Kommando.REGISTRER_KLIENT, klientInfo);
        return sendOgMotta(foresprørsel);
    }
}
