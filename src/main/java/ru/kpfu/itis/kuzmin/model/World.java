package ru.kpfu.itis.kuzmin.model;

import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;

import java.util.ArrayList;
import java.util.Iterator;

public class World {
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public void moveBullets() {

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.reduceLifeTime();

            if (bullet.getLifetime() <= 0) {
                iterator.remove();
                LevelController.removeBullet(bullet.getImageView());
            }
            bullet.getImageView().setX(bullet.getImageView().getX() + bullet.getVelocity() * bullet.getVectorX());
            bullet.getImageView().setY(bullet.getImageView().getY() + bullet.getVelocity() * bullet.getVectorY());
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

}
