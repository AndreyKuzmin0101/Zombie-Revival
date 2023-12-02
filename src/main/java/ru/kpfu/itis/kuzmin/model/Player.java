package ru.kpfu.itis.kuzmin.model;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.client.Client;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.role.Role;

public class Player {
    private ProgressBar hpBar;
    private double hp;
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;
    private boolean shoot;


    private double mouseX;
    private double mouseY;

    private Client client;
    private Weapon weapon;
    private Role role;

    public Player(Client client, Role role, Weapon weapon) {
        this.client = client;
        this.role = role;
        this.weapon = weapon;
        this.hp = 100;
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

            client.sendShot((float) vectorX, (float) vectorY);

            Bullet bullet = new Bullet(weapon.getDamage(), weapon.getBulletVelocity(), vectorX, vectorY, bulletView);


            world.addBullet(bullet);

            LevelController.addBullet(bulletView);

            weapon.resumeInterval();

        }
    }
    public void move() {
        if (left && getPositionX() > 0) {
            setPositionX(getPositionX() - role.getSpeed());
            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }
        if (right && getPositionX() < 1480) {
            setPositionX(getPositionX() + role.getSpeed());
            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }
        if (up && getPositionY() > 0) {
            setPositionY(getPositionY() - role.getSpeed());
            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }
        if (down && getPositionY() < 750) {
            setPositionY(getPositionY() + role.getSpeed());
            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }
    }

    public double getPositionX() {
        return role.getImage().getLayoutX();
    }

    public void setPositionX(double positionX) {
        role.getImage().setLayoutX(positionX);
        hpBar.setLayoutX(positionX);
    }

    public double getPositionY() {
        return role.getImage().getLayoutY();
    }

    public void setPositionY(double positionY) {
        role.getImage().setLayoutY(positionY);
        hpBar.setLayoutY(positionY-20);
    }

    public void setHp(double hp) {
        if (hp <= 0) {
            this.hp = 0;
            this.hpBar.setProgress(0);
        } else {
            this.hp = hp;
            this.hpBar.setProgress(hp/100);
        }
    }

    public double getHp() {
        return hp;
    }


    public void setHpProgressBar(ProgressBar progressBar) {
        this.hpBar = progressBar;
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
