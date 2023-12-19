package ru.kpfu.itis.kuzmin.protocol;

import java.nio.ByteBuffer;

public class ZombieModel{
    public static final int ZOMBIE_DATA_LENGTH = 13;
    private int id;
    private byte type;
    private float startPositionX;
    private float startPositionY;

    public ZombieModel(int id, byte type, float startPositionX, float startPositionY) {
        this.id = id;
        this.type = type;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
    }

    public byte[] getData() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(ZOMBIE_DATA_LENGTH);
        byteBuffer.putInt(id);
        byteBuffer.put(type);
        byteBuffer.putFloat(startPositionX);
        byteBuffer.putFloat(startPositionY);

        return byteBuffer.array();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
