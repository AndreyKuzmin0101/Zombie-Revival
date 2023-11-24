package ru.kpfu.itis.kuzmin.model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.role.Role;

public class Player {
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;
    private boolean shoot;


    private double mouseX;
    private double mouseY;


    private Weapon weapon;
    private Role role;

    public Player(Role role, Weapon weapon) {
        this.role = role;
        this.weapon = weapon;
    }

    public void shoot(World world) {
        weapon.reduceInterval();

        if (shoot && weapon.getInterval() <= 0) {
            ImageView bulletView = new ImageView("/images/bullet.png");
            bulletView.setFitHeight(11);
            bulletView.setFitWidth(11);
            bulletView.setLayoutX(getPositionX() + 28);
            bulletView.setLayoutY(getPositionY() + 54);


            double dx = mouseX - bulletView.getLayoutX();
            double dy = mouseY - bulletView.getLayoutY();

            double sqrt = Math.sqrt(dx*dx + dy*dy);
            double vectorX = dx/sqrt;
            double vectorY = dy/sqrt;


            Bullet bullet = new Bullet(weapon.getDamage(),5, vectorX, vectorY, bulletView);

            world.addBullet(bullet);

            LevelController.addBullet(bulletView);

            weapon.resumeInterval();
        }
    }
    public void move() {
        if (left && getPositionX() > 0) {
            setPositionX(getPositionX() - role.getSpeed());
        }
        if (right && getPositionX() < 1480) {
            setPositionX(getPositionX() + role.getSpeed());
        }
        if (up && getPositionY() > 0) {
            setPositionY(getPositionY() - role.getSpeed());
        }
        if (down && getPositionY() < 750) {
            setPositionY(getPositionY() + role.getSpeed());
        }
    }

    public double getPositionX() {
        return role.getImage().getLayoutX();
    }

    public void setPositionX(double positionX) {
        role.getImage().setLayoutX(positionX);

    }

    public double getPositionY() {
        return role.getImage().getLayoutY();
    }

    public void setPositionY(double positionY) {
        role.getImage().setLayoutY(positionY);
    }


    public void setRight(boolean right) {
        this.right = right;
    }


    public void setLeft(boolean left) {
        this.left = left;
    }


    public void setUp(boolean up) {
        this.up = up;
    }


    public void setDown(boolean down) {
        this.down = down;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }


    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }


    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
