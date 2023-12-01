package ru.kpfu.itis.kuzmin.model.zombie;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.AppClient;
import ru.kpfu.itis.kuzmin.model.World;

public abstract class Zombie {
    public static final byte SHAMBLING_CITIZEN = 1;
    private int id;
    private int type;
    private int damage;
    private float speed;
    private ImageView image;
    private ProgressBar hp;

    public Zombie(int id, int type, int hp, int damage, float speed, double positionX, double positionY) {
        this.id = id;
        this.type = type;
        this.damage = damage;
        this.speed = speed;

        this.image = new ImageView("images/shambling_citizen.png");
        image.setFitWidth(53);
        image.setFitHeight(70);

        this.hp = new ProgressBar(hp);

        setPositionX(positionX);
        setPositionY(positionY);
    }

    public double getPositionX() {
        return image.getLayoutX();
    }

    public void setPositionX(double positionX) {
        image.setLayoutX(positionX);
        hp.setLayoutX(positionX);
    }

    public double getPositionY() {
        return image.getLayoutY();
    }

    public void setPositionY(double positionY) {
        image.setLayoutY(positionY);
        hp.setLayoutY(positionY-20);
    }

    public void move(World world) {
//        НУЖНО РЕАЛИЗОВАТЬ
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
        return hp.getProgress();
    }

    public void setHp(double hp) {
        this.hp.setProgress(hp);
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


}
