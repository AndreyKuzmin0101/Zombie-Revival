package ru.kpfu.itis.kuzmin.util;

import ru.kpfu.itis.kuzmin.model.zombie.ShamblingCitizen;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

public class ZombieFactory {
    public Zombie createZombie(int id, byte type, double positionX, double positionY) {
        switch (type) {
            case 1:
                return new ShamblingCitizen(id, positionX, positionY);
        }
        return null;
    }
}
