package ru.kpfu.itis.kuzmin.level;

import ru.kpfu.itis.kuzmin.protocol.ZombieModel;

public interface ISpawner {
    ZombieModel getZombie();

    boolean isActive();

    void setInActive();
}
