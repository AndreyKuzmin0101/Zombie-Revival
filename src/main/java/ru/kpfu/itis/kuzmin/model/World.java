package ru.kpfu.itis.kuzmin.model;

import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class World {
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();

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
                if (bullet.getImageView().getLayoutBounds().intersects(zombie.getImage().getLayoutBounds())) {
                    zombieIterator.remove();
                    LevelController.removeZombie(zombie.getImage());

                    bulletIterator.remove();
                    LevelController.removeBullet(bullet.getImageView());

                    break;
                }
            }
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public void moveZombies() {
        for (Zombie zombie : zombies) {
            zombie.move(this);
        }
    }

    public static double getZombieStartPositionX() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            return random.nextDouble(-260, -60);
        } else {
            return random.nextDouble(1545, 1745);
        }

    }
    public static double getZombieStartPositionY() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            return random.nextDouble(-280, -80);
        } else {
            return random.nextDouble(830, 1030);
        }
    }

}
