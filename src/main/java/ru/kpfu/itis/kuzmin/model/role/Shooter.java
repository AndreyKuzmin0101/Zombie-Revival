package ru.kpfu.itis.kuzmin.model.role;

import ru.kpfu.itis.kuzmin.model.gun.Uzi;

public class Shooter extends Role {
    private int role;
    public Shooter() {
        super(1.5, Role.SHOOTER);
        setDefaultWeapon(new Uzi());
    }


}
