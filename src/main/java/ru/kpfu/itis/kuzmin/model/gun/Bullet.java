package ru.kpfu.itis.kuzmin.model.gun;

import javafx.scene.image.ImageView;

public class Bullet implements IBullet{
    private double damage;
    private int velocity;
    private double vectorX;
    private double vectorY;
    private int lifetime;
    private boolean inactive;
    private ImageView imageView;

    public Bullet(double damage, int velocity, double vectorX, double vectorY, ImageView imageView) {
        this.damage = damage;
        this.velocity = velocity;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
        this.imageView = imageView;
        lifetime = 500;
        inactive = false;
    }

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public int getVelocity() {
        return velocity;
    }

    @Override
    public double getVectorX() {
        return vectorX;
    }

    @Override
    public void setVectorX(double vectorX) {
        this.vectorX = vectorX;
    }

    @Override
    public double getVectorY() {
        return vectorY;
    }

    @Override
    public void setVectorY(double vectorY) {
        this.vectorY = vectorY;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void reduceLifeTime() {
        lifetime -= 1;
    }

    @Override
    public int getLifetime() {
        return lifetime;
    }

    @Override
    public double getCenterX() {
        return getImageView().getLayoutX() + 5.5;
    }

    @Override
    public double getCenterY() {
        return getImageView().getLayoutY() + 5.5;
    }
}
