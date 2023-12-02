package ru.kpfu.itis.kuzmin.model.zombie;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;

public abstract class Zombie {
    public static final byte SHAMBLING_CITIZEN = 1;
    private int id;
    private int type;
    private int damage;
    private float speed;
    private ImageView image;
    private ProgressBar hpProgressBar;
    private double startHp;
    private double hp;
    private int speedDamage;
    private int intervalDamage;


    public Zombie(int id, int type, int hp, int damage, float speed, int speedDamage, double positionX, double positionY) {
        this.id = id;
        this.type = type;
        this.damage = damage;
        this.speed = speed;
        this.speedDamage = speedDamage;
        this.intervalDamage = speedDamage;

        this.image = new ImageView("/images/shambling_citizen.png");
        image.setFitWidth(53);
        image.setFitHeight(70);

        this.hp = hp;
        startHp = hp;
        hpProgressBar = new ProgressBar(1);
        hpProgressBar.setPrefWidth(53);
        hpProgressBar.setPrefHeight(15);
        LevelController.addHpBar(hpProgressBar);

        setPositionX(positionX);
        setPositionY(positionY);

    }

    public int getIntervalDamage() {
        return intervalDamage;
    }

    public void resetIntervalDamage() {
        intervalDamage = speedDamage;
    }

    public void reduceIntervalDamage() {
        intervalDamage -= 1;
    }

    public double getPositionX() {
        return image.getLayoutX();
    }

    public void setPositionX(double positionX) {
        image.setLayoutX(positionX);
        hpProgressBar.setLayoutX(positionX);
    }

    public double getPositionY() {
        return image.getLayoutY();
    }

    public void setPositionY(double positionY) {
        image.setLayoutY(positionY);
        hpProgressBar.setLayoutY(positionY-20);
    }

    public double damage(Player player, Teammate teammate) {
        if (player.getRole().getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
            player.setHp(player.getHp() - getDamage());
        }
        if (teammate.getRole().getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
            teammate.setHp(teammate.getHp() - getDamage());
        }
        return player.getHp();
    }


    public void move(Player player, Teammate teammate) {
        double dx1 = player.getPositionX() - getPositionX();
        double dy1 = player.getPositionY() - getPositionY();

        double playerDistance = Math.sqrt(dx1*dx1 + dy1*dy1);

        double dx2 = teammate.getPositionX() - getPositionX();
        double dy2 = teammate.getPositionY() - getPositionY();

        double teammateDistance = Math.sqrt(dx2*dx2 + dy2*dy2);

        double vectorX, vectorY;
        if (playerDistance < teammateDistance) {
            vectorX = dx1/playerDistance;
            vectorY = dy1/playerDistance;
        } else {
            vectorX = dx2/teammateDistance;
            vectorY = dy2/teammateDistance;
        }

        setPositionX(getPositionX() + vectorX * speed);
        setPositionY(getPositionY() + vectorY * speed);

    }


    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
        this.hpProgressBar.setProgress(hp/this.startHp);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ProgressBar getHpProgressBar() {
        return hpProgressBar;
    }
}
