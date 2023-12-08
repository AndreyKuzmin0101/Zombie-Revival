package ru.kpfu.itis.kuzmin.protocol;

import java.io.BufferedInputStream;
import java.io.IOException;


// Message contents: [TYPE (1), DATA LENGTH(1), DATA(?)]
public class Message {
    public static final byte LOSE = -3;
    public static final byte VICTORY = -2;
    public static final byte SPAWN_ZOMBIE = -1;
    public static final byte START_GAME = 1;
    public static final byte MOVE = 2;
    public static final byte SHOT = 3;
    public static final byte ZOMBIE_DIE = 4;
    public static final byte PLAYER_DIE = 5;
    public static final byte CREATE_WALL = 6;
    public static final byte CREATE_TURRET = 7;

    public static Message createMessage(byte messageType, byte[] data) {
        return new Message(messageType, data);
    }

    public static Message readMessage(BufferedInputStream input) throws IOException {
        byte type = (byte) input.read();
        byte dataLength = (byte) input.read();
        byte[] data = input.readNBytes(dataLength);
        return new Message(type, data);
    }

    public static byte[] getBytes(Message message) {
        int rawMessageLength = 1 + 1 + message.getData().length;
        byte[] bytes = new byte[rawMessageLength];
        int j = 0;
        bytes[j++] = message.getType();
        bytes[j++] = (byte) message.getData().length;
        int dataLength = message.getData().length;
        for (int i = 0; i < dataLength; i++) {
            bytes[j++] = message.getData()[i];
        }
        return bytes;
    }

    protected final byte[] data;
    protected final byte type;
    protected Message(byte type, byte[] data){
        this.data = data;
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public byte getType() {
        return type;
    }

}
