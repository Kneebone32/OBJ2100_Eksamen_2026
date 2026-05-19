package common;

import java.io.Serializable;

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
