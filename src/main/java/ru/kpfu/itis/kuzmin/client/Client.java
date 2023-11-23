package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

public class Client implements IClient{

    private Socket socket;
    private final InetAddress host;
    private final int port;
    private ClientThread thread;

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        connect();
        BufferedReader input;
        BufferedWriter output;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        thread = new ClientThread(input, output);
        new Thread(thread).start();
    }

    @Override
    public void connect() {
        try{
            socket = new Socket(host, port);
        }
        catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void sendMessage(Message message) {
        // Отправка данных
    }

    private static class ClientThread implements Runnable{

        private BufferedReader input;
        private BufferedWriter output;

        public ClientThread(BufferedReader input, BufferedWriter output) {
            this.input = input;
            this.output = output;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String message = input.readLine();

                    // Обработка принятых данных
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public BufferedWriter getOutput() {
            return output;
        }
    }
}
