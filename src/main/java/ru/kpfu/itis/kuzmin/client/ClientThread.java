package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class ClientThread implements Runnable{

    private BufferedInputStream input;
    private BufferedOutputStream output;
    private Client client;

    public ClientThread(BufferedInputStream input, BufferedOutputStream output, Client client) {
        this.input = input;
        this.output = output;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = Message.readMessage(input);

                client.handleMessage(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedOutputStream getOutput() {
        return output;
    }
}