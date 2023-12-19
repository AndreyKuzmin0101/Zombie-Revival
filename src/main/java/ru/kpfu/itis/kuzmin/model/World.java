package ru.kpfu.itis.kuzmin.model;

import ru.kpfu.itis.kuzmin.client.Client;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.defense.DefaultTurret;
import ru.kpfu.itis.kuzmin.model.defense.StoneWall;
import ru.kpfu.itis.kuzmin.model.defense.Turret;
import ru.kpfu.itis.kuzmin.model.defense.Wall;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class World implements IWorld {
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Integer> deadZombieIds = new ArrayList<>();

    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Turret> turrets = new ArrayList<>();
    private Client client;
    public World(Client client) {
        this.client = client;
    }

    @Override
    public void moveBullets(double elapsedTime) {

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.reduceLifeTime();

            bullet.getImageView().setX(bullet.getImageView().getX() + bullet.getVelocity() * elapsedTime * bullet.getVectorX());
            bullet.getImageView().setY(bullet.getImageView().getY() + bullet.getVelocity() * elapsedTime * bullet.getVectorY());
        }
    }

    @Override
    public void deleteOldBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.getLifetime() <= 0) {
                iterator.remove();
                LevelController.removeBullet(bullet.getImageView());
            }
        }
    }

    @Override
    public void checkIntersectBulletsAndZombies() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();

            Iterator<Zombie> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
                Zombie zombie = zombieIterator.next();
                if (bullet.getImageView().getBoundsInParent().intersects(zombie.getImage().getBoundsInParent())) {
                    zombie.setHp(zombie.getHp() - bullet.getDamage());
                    if (zombie.getHp() <= 0) {
                        zombieIterator.remove();
                        LevelController.removeZombie(zombie);
                        client.sendZombieDie(zombie.getId());
                    }

                    bulletIterator.remove();
                    LevelController.removeBullet(bullet.getImageView());

                    break;
                }
            }
        }

        Iterator<Integer> idsIterator = deadZombieIds.iterator();
        while (idsIterator.hasNext()) {
            removeZombie(idsIterator.next());
            idsIterator.remove();
        }

    }

    @Override
    public void checkIntersectPlayersAndZombies(Player player, Teammate teammate, double elapsedTime) {
        for(Zombie zombie: zombies) {
            zombie.reduceIntervalDamage(elapsedTime);
            if (zombie.getIntervalDamage() <= 0){
                if (zombie.damage(player, teammate) <= 0) {
                    client.sendPlayerDie();
                }

            }

        }
    }

    private void removeZombie(int id) {
        for (Zombie zombie : zombies) {
            if (zombie.getId() == id) {
                zombies.remove(zombie);
                LevelController.removeZombie(zombie);
                break;
            }
        }
    }

    @Override
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    @Override
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    @Override
    public void moveZombies(Player player, Teammate teammate, double elapsedTime) {
        for (Zombie zombie : zombies) {
            zombie.move(player, teammate, elapsedTime, this);
        }
    }

    @Override
    public void addDeadZombieId(int id) {
        deadZombieIds.add(id);
    }

    @Override
    public void addWall(double positionX, double positionY) {
        Wall wall = new StoneWall(positionX, positionY);
        walls.add(wall);
    }

    @Override
    public void addTurret(double positionX, double positionY) {
        Turret turret = new DefaultTurret(positionX, positionY);
        turrets.add(turret);
    }

    @Override
    public void shootTurrets(double elapsedTime) {
        for (Turret turret : turrets) {
            turret.shoot(this, elapsedTime);
        }
    }

    public static double getZombieStartPositionX() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            return random.nextDouble(-260, -60);
        } else {
            return random.nextDouble(1925, 2125);
        }

    }
    public static double getZombieStartPositionY() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            return random.nextDouble(-280, -80);
        } else {
            return random.nextDouble(1030, 1230);
        }
    }

    @Override
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    @Override
    public ArrayList<Turret> getTurrets() {
        return turrets;
    }

    @Override
    public void deleteWall(Wall wall) {
        walls.remove(wall);
        LevelController.removeWall(wall);
    }

    @Override
    public void deleteTurret(Turret turret) {
        turrets.remove(turret);
        LevelController.removeTurret(turret);
    }

    @Override
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }



}
