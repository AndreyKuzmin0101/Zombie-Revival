package ru.kpfu.itis.kuzmin.model;

import javafx.scene.control.ProgressBar;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.role.Role;

public interface IPlayer {
    void shoot(World world, double elapsedTime);

    void move(World world, double elapsedTime);

    void setWall(World world);

    void setTurret(World world);

    double getPositionX();

    void setPositionX(double positionX);

    double getPositionY();

    void setPositionY(double positionY);

    double getCenterX();

    double getCenterY();

    void setHp(double hp);

    void changeMode(int newMode);

    int getMode();

    double getHp();

    void setHpProgressBar(ProgressBar progressBar);

    void setRight(boolean right);

    void setLeft(boolean left);

    void setUp(boolean up);

    void setDown(boolean down);

    Role getRole();

    void setRole(Role role);

    void setShoot(boolean shoot);

    void setMouseX(double mouseX);

    void setMouseY(double mouseY);

    void setWeapon(Weapon weapon);

    double getMouseX();

    double getMouseY();
}
