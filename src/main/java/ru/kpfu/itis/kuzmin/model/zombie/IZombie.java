package ru.kpfu.itis.kuzmin.model.zombie;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.defense.Turret;
import ru.kpfu.itis.kuzmin.model.defense.Wall;

public interface IZombie {

    double getIntervalDamage();

    void resetIntervalDamage();

    void reduceIntervalDamage(double elapsedTime);

    double getPositionX();

    void setPositionX(double positionX);

    double getPositionY();

    void setPositionY(double positionY);

    double getCenterX();

    double getCenterY();

    double damage(Player player, Teammate teammate);

    void move(Player player, Teammate teammate, double elapsedTime, World world);

    void damageWall(Wall wall, World world);

    void damageTurret(Turret turret, World world);

    ImageView getImage();

    void setImage(ImageView image);

    int getId();

    void setId(int id);

    int getType();

    void setType(int type);

    double getHp();

    void setHp(double hp);

    int getDamage();

    void setDamage(int damage);

    float getSpeed();

    void setSpeed(float speed);

    ProgressBar getHpProgressBar();
}
