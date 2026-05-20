package klient.controller;

import java.io.IOException;

import common.*;
import common.enums.HenvendelseType;
import klient.KlientMain;

public class RegistratorController extends KlientMain {
    public Melding opprettHenvendelse(String innhold, HenvendelseType type) throws IOException, ClassNotFoundException {
        Melding foresporsel = Melding.opprettHenvendelse(innhold, type);
        return sendOgMotta(foresporsel);
    }
}
