package ru.kpfu.itis.kuzmin.model;

import ru.kpfu.itis.kuzmin.client.Client;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

import java.util.*;

public class World {
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Integer> deadZombieIds = new ArrayList<>();
    private Client client;

    public World(Client client) {
        this.client = client;
    }

    public void moveBullets() {

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.reduceLifeTime();

            bullet.getImageView().setX(bullet.getImageView().getX() + bullet.getVelocity() * bullet.getVectorX());
            bullet.getImageView().setY(bullet.getImageView().getY() + bullet.getVelocity() * bullet.getVectorY());
        }
    }

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

    //TODO: оптимизировать
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

    public void checkIntersectPlayersAndZombies(Player player, Teammate teammate) {
        for(Zombie zombie: zombies) {
            zombie.reduceIntervalDamage();
            if (zombie.getIntervalDamage() <= 0){
                if (zombie.damage(player, teammate) <= 0) {
                    client.sendPlayerDie();
                }
                zombie.resetIntervalDamage();
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

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public void moveZombies(Player player, Teammate teammate) {
        for (Zombie zombie : zombies) {
            zombie.move(player, teammate);
        }
    }

    public void addDeadZombieId(int id) {
        deadZombieIds.add(id);
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

}
