package common;

import java.io.Serializable;

//Klasse for agenter hvor vi har oppgitt agentID og navn.-
//Denne klassen implementerer også Serializable slik at den kan bli sendt mellom klient og server.
public class Agent implements Serializable {
    private final int agentID;
    private final String navn;

    public Agent(int agentID, String navn) {
        this.agentID = agentID;
        this.navn = navn;
    }

    public int getAgentID() {
        return agentID;
    }

    public String getNavn() {
        return navn;
    }
}
