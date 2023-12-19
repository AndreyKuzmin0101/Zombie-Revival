package ru.kpfu.itis.kuzmin.model;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.client.Client;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.defense.Wall;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.gun.Weapon;
import ru.kpfu.itis.kuzmin.model.role.Role;

import java.util.List;

public class Player implements IPlayer{
    public static final int SHOOT = 1;
    public static final int BUILD_WALL = 2;
    public static final int BUILD_TURRET = 3;
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
    private int mode;

    public Player(Client client, Role role, Weapon weapon) {
        this.client = client;
        this.role = role;
        this.weapon = weapon;
        this.hp = 100;
        this.mode = SHOOT;
    }

    @Override
    public void shoot(World world, double elapsedTime) {
        weapon.reduceInterval(elapsedTime);

        if (mode == SHOOT && shoot && weapon.getInterval() <= 0) {
            ImageView bulletView = new ImageView("/images/bullet.png");
            bulletView.setFitHeight(11);
            bulletView.setFitWidth(11);
            bulletView.setLayoutX(getPositionX() + 28);
            bulletView.setLayoutY(getPositionY() + 45);


            double dx = mouseX - bulletView.getLayoutX();
            double dy = mouseY - bulletView.getLayoutY();

            double sqrt = Math.sqrt(dx * dx + dy * dy);
            double vectorX = dx / sqrt;
            double vectorY = dy / sqrt;

            client.sendShot((float) vectorX, (float) vectorY);

            Bullet bullet = new Bullet(weapon.getDamage(), weapon.getBulletVelocity(), vectorX, vectorY, bulletView);


            world.addBullet(bullet);

            LevelController.addBullet(bulletView);

            weapon.resumeInterval();

        }
    }

    @Override
    public void move(World world, double elapsedTime) {
        List<Wall> walls = world.getWalls();

        if (left && getPositionX() > 0) {
            double oldPositionX = getPositionX();
            setPositionX(getPositionX() - role.getSpeed() * elapsedTime);

            for (Wall wall : walls) {
                if (getRole().getImage().getBoundsInParent().intersects(wall.getImage().getBoundsInParent())) {
                    setPositionX(oldPositionX);
                    break;
                }
            }

            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }
        if (right && getPositionX() < 1867) {
            double oldPositionX = getPositionX();
            setPositionX(getPositionX() + role.getSpeed() * elapsedTime);

            for (Wall wall : walls) {
                if (getRole().getImage().getBoundsInParent().intersects(wall.getImage().getBoundsInParent())) {
                    setPositionX(oldPositionX);
                    break;
                }
            }

            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }
        if (up && getPositionY() > 0) {
            double oldPositionY = getPositionY();
            setPositionY(getPositionY() - role.getSpeed() * elapsedTime);

            for (Wall wall : walls) {
                if (getRole().getImage().getBoundsInParent().intersects(wall.getImage().getBoundsInParent())) {
                    setPositionY(oldPositionY);
                    break;
                }
            }

            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }
        if (down && getPositionY() < 950) {
            double oldPositionY = getPositionY();
            setPositionY(getPositionY() + role.getSpeed() * elapsedTime);
            for (Wall wall : walls) {
                if (getRole().getImage().getBoundsInParent().intersects(wall.getImage().getBoundsInParent())) {
                    setPositionY(oldPositionY);
                    break;
                }
            }

            client.sendNewPosition((float) getPositionX(), (float) getPositionY());
        }


    }

    @Override
    public void setWall(World world) {
        if (role.getRoleCode() == Role.ENGINEER) {
            world.addWall(mouseX, mouseY);
            client.sendWall((float) mouseX, (float) mouseY);
        }
    }

    @Override
    public void setTurret(World world) {
        if (role.getRoleCode() == Role.ENGINEER) {
            world.addTurret(mouseX, mouseY);
            client.sendTurret((float) mouseX, (float) mouseY);
        }
    }

    @Override
    public double getPositionX() {
        return role.getImage().getLayoutX();
    }

    @Override
    public void setPositionX(double positionX) {
        role.getImage().setLayoutX(positionX);
        hpBar.setLayoutX(positionX);
    }

    @Override
    public double getPositionY() {
        return role.getImage().getLayoutY();
    }

    @Override
    public void setPositionY(double positionY) {
        role.getImage().setLayoutY(positionY);
        hpBar.setLayoutY(positionY - 20);
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
    public void changeMode(int newMode) {
        this.mode = newMode;
    }

    @Override
    public int getMode() {
        return mode;
    }

    @Override
    public double getHp() {
        return hp;
    }

    @Override
    public void setHpProgressBar(ProgressBar progressBar) {
        this.hpBar = progressBar;
    }

    @Override
    public void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    @Override
    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    @Override
    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public double getMouseX() {
        return mouseX;
    }

    @Override
    public double getMouseY() {
        return mouseY;
    }
}
