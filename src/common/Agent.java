package common;

import java.io.Serializable;

import common.enums.KlientRolle;

//Klasse for agenter hvor vi har oppgitt agentID og navn.-
//Denne klassen implementerer også Serializable slik at den kan bli sendt mellom klient og server.
public class Agent implements Serializable {
    private final int agentID;
    private final String navn;
    private final KlientRolle klientRolle;

    public Agent(int agentID, String navn, KlientRolle klientRolle) {
        this.agentID = agentID;
        this.navn = navn;
        this.klientRolle = klientRolle;
    }

    public int getAgentID() {
        return agentID;
    }

    public String getNavn() {
        return navn;
    }

    public KlientRolle getKlientRolle() {
        return klientRolle;
    }
}
