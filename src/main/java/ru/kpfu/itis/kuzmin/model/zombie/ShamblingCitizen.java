package ru.kpfu.itis.kuzmin.model.zombie;

public class ShamblingCitizen extends Zombie{
    public ShamblingCitizen(int id, double positionX, double positionY) {
        super(id, SHAMBLING_CITIZEN, 100, 20, (float) 0.5, positionX, positionY);
    }
}
