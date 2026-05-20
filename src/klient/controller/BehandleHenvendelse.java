package klient.controller;

import common.Henvendelse;

public class BehandleHenvendelse {
    private final Henvendelse henvendelse;

    public BehandleHenvendelse(Henvendelse henvendelse){
        this.henvendelse = henvendelse;
    }

    public void behandleHenvendelse(){
        try {
            System.out.println("Starter behandlingen");
            Thread.sleep(3000);
            System.out.println("Henvendelse: " + henvendelse.getHenvendelseID() + " er ferdig behandlet");

        } catch (InterruptedException e) {
            System.out.println("Behandlingen av henvendelsen ble avbrutt");
        }
    }
}
