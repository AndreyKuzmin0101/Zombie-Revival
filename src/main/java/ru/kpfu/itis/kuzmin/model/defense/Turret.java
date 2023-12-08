package ru.kpfu.itis.kuzmin.model.defense;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

public abstract class Turret {
    private double hp;
    private double startHp;
    private ImageView image;
    private ProgressBar progressBar;
    private Weapon weapon;

    public Turret(double hp, Weapon weapon, double positionX, double positionY) {
        this.hp = hp;
        this.startHp = hp;
        this.weapon = weapon;
        ImageView imageView = new ImageView("/images/turret.png");
        imageView.setFitWidth(90);
        imageView.setFitHeight(80);
        imageView.setLayoutX(positionX - 45);
        imageView.setLayoutY(positionY - 40);
        this.image = imageView;

        progressBar = new ProgressBar(1);
        progressBar.setPrefWidth(50);
        progressBar.setPrefHeight(15);
        progressBar.setLayoutX(positionX - 20);
        progressBar.setLayoutY(positionY - 60);

        LevelController.addTurret(this);
    }

    public void shoot(World world, double elapsedTime) {
        weapon.reduceInterval(elapsedTime);
        if (weapon.getInterval() <= 0) {

            double distance = Double.MAX_VALUE;

            ImageView bulletView = new ImageView("/images/bullet.png");
            bulletView.setFitHeight(11);
            bulletView.setFitWidth(11);
            bulletView.setLayoutX(getPositionX() + 45);
            bulletView.setLayoutY(getPositionY() + 40);

            Bullet bullet = new Bullet(weapon.getDamage(), weapon.getBulletVelocity(), 0, 0, bulletView);

            for (Zombie zombie : world.getZombies()) {
                double dx = zombie.getCenterX() - bullet.getCenterX();
                double dy = zombie.getCenterY() - bullet.getCenterY();

                double sqrt = Math.sqrt(dx * dx + dy * dy);
                if (sqrt < distance) {
                    distance = sqrt;
                    bullet.setVectorX(dx/sqrt);
                    bullet.setVectorY(dy/sqrt);
                }
            }

            if (Double.compare(distance, Double.MAX_VALUE) != 0) {
                world.addBullet(bullet);
                LevelController.addBullet(bulletView);
                weapon.resumeInterval();
            }
        }
    }

    public double getPositionX() {
        return image.getLayoutX();
    }

    public double getPositionY() {
        return image.getLayoutY();
    }

    public double getCenterX() {
        return getPositionX() + 45;
    }

    public double getCenterY() {
        return getPositionY() + 40;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
        this.progressBar.setProgress(hp / this.startHp);
    }

    public ImageView getImage() {
        return image;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }


}
