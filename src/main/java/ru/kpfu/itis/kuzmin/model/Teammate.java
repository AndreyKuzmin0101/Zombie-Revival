package ru.kpfu.itis.kuzmin.model;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.role.Role;

public class Teammate implements ITeammate{
    private ProgressBar hpBar;
    private double hp;
    private Weapon weapon;
    private Role role;

    public Teammate(Role role, Weapon weapon) {
        this.role = role;
        this.weapon = weapon;
        this.hp = 100;
    }

    @Override
    public void move(double positionX, double positionY) {
        setPositionX(positionX);
        setPositionY(positionY);
    }

    @Override
    public void shoot(World world, double vectorX, double vectorY) {
        ImageView bulletView = new ImageView("/images/bullet.png");
        bulletView.setFitHeight(11);
        bulletView.setFitWidth(11);
        bulletView.setLayoutX(getPositionX() + 28);
        bulletView.setLayoutY(getPositionY() + 54);

        Bullet bullet = new Bullet(weapon.getDamage(), weapon.getBulletVelocity(), vectorX, vectorY, bulletView);

        LevelController.addBullet(bulletView);
        world.addBullet(bullet);
    }

    @Override
    public double getPositionX() {
        return role.getImage().getLayoutX();
    }

    @Override
    public void setPositionX(double positionX) {
        Platform.runLater(() -> {
            role.getImage().setLayoutX(positionX);
            hpBar.setLayoutX(positionX);
        });

    }

    @Override
    public double getPositionY() {
        return role.getImage().getLayoutY();
    }

    @Override
    public void setPositionY(double positionY) {
        Platform.runLater(() -> {
            role.getImage().setLayoutY(positionY);
            hpBar.setLayoutY(positionY - 20);
        });

    }

    @Override
    public double getCenterX() {
        return getPositionX() + 26.5;
    }

    @Override
    public double getCenterY() {
        return getPositionY() + 35;
    }

    @Override
    public double getHp() {
        return hp;
    }

    @Override
    public void setHp(double hp) {
        if (hp <= 0) {
            this.hp = 0;
            this.hpBar.setProgress(0);
        } else {
            this.hp = hp;
            this.hpBar.setProgress(hp / 100);
        }

    }

    @Override
    public void setHpProgressBar(ProgressBar progressBar) {
        this.hpBar = progressBar;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }
}
