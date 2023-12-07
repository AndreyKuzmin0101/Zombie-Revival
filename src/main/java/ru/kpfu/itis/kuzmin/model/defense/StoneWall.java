package ru.kpfu.itis.kuzmin.model.defense;

public class StoneWall extends Wall{

    public StoneWall(double positionX, double positionY) {
        super((byte) 1, 100, positionX, positionY);
    }
}
