package ru.kpfu.itis.kuzmin.model;

import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.role.Role;

public class Teammate {
    private Weapon weapon;
    private Role role;

    public Teammate(Role role, Weapon weapon) {
        this.role = role;
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
