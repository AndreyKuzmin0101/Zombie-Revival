package ru.kpfu.itis.kuzmin.model;

import javafx.scene.control.ProgressBar;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.role.Role;

public interface ITeammate {
    void move(double positionX, double positionY);

    void shoot(World world, double vectorX, double vectorY);

    double getPositionX();

    void setPositionX(double positionX);

    double getPositionY();

    void setPositionY(double positionY);

    double getCenterX();

    double getCenterY();

    double getHp();

    void setHp(double hp);

    void setHpProgressBar(ProgressBar progressBar);

    Weapon getWeapon();

    void setWeapon(Weapon weapon);

    Role getRole();

    void setRole(Role role);
}
