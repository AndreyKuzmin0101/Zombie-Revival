package ru.kpfu.itis.kuzmin.model.role;

import ru.kpfu.itis.kuzmin.model.gun.Revolver;

public class Engineer extends Role{
    public Engineer() {
        super(2, Role.ENGINEER);
        setDefaultWeapon(new Revolver());
    }
}
