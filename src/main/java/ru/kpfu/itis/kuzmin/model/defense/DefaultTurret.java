package ru.kpfu.itis.kuzmin.model.defense;

import ru.kpfu.itis.kuzmin.model.gun.turret.DefaultGun;

public class DefaultTurret extends Turret{


    public DefaultTurret(double positionX, double positionY) {
        super(100, new DefaultGun(), positionX, positionY);
    }
}

