package ru.kpfu.itis.kuzmin.client;

import ru.kpfu.itis.kuzmin.protocol.Message;

public interface IClient {
    void connect();
    void sendMessage(Message message);
}