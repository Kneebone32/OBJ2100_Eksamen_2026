package klient;

import java.net.Socket;
import java.io.*;
import common.Melding;

// Felles base for klientene (registrator og support agent)
public abstract class BaseKlient implements AutoCloseable {
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

    @Override
    public void close() throws IOException {
        if (input != null) input.close();
        if (output != null) output.close();
        if (socket != null) socket.close();
    }
}

