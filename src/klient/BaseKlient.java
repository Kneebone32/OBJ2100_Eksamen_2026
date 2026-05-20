package klient;

import java.net.Socket;
import java.io.*;
import common.Melding;

public abstract class BaseKlient {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void kobleTil(String host, int port) throws IOException {
        socket = new Socket(host, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public synchronized Melding sendOgMotta(Melding mld) throws IOException, ClassNotFoundException {
        output.writeObject(mld);
        return (Melding) input.readObject();
    }
}

