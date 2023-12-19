package ru.kpfu.itis.kuzmin.model;

import ru.kpfu.itis.kuzmin.model.defense.Turret;
import ru.kpfu.itis.kuzmin.model.defense.Wall;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

import java.util.ArrayList;

public interface IWorld {
    void moveBullets(double elapsedTime);

    void deleteOldBullets();

    void checkIntersectBulletsAndZombies();

    void checkIntersectPlayersAndZombies(Player player, Teammate teammate, double elapsedTime);

    void addBullet(Bullet bullet);

    void addZombie(Zombie zombie);

    void moveZombies(Player player, Teammate teammate, double elapsedTime);

    void addDeadZombieId(int id);

    void addWall(double positionX, double positionY);

    void addTurret(double positionX, double positionY);

    void shootTurrets(double elapsedTime);

    ArrayList<Wall> getWalls();

    ArrayList<Turret> getTurrets();

    void deleteWall(Wall wall);

    void deleteTurret(Turret turret);

    ArrayList<Zombie> getZombies();
}
