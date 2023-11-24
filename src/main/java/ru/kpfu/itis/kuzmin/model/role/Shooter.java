package ru.kpfu.itis.kuzmin.model.role;

import ru.kpfu.itis.kuzmin.model.gun.Revolver;
import ru.kpfu.itis.kuzmin.model.gun.Uzi;
import ru.kpfu.itis.kuzmin.model.role.Role;

public class Shooter extends Role {
    private int role;
    public Shooter() {
        super(1, Role.SHOOTER);
        setDefaultWeapon(new Uzi());
    }


}
