package klient.controller;

import java.io.IOException;

import common.*;
import klient.KlientMain;

public class RegistratorController extends KlientMain {
    public Melding opprettHenvendelse(HenvendelseType type, Object innhold) throws IOException, ClassNotFoundException {
        Melding foresporsel = new Melding(Kommando.OPPRETT_HENVENDELSE, innhold, type);
        return sendOgMotta(foresporsel);
    }
}
