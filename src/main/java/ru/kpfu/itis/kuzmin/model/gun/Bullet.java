package ru.kpfu.itis.kuzmin.model.gun;

import javafx.scene.image.ImageView;

public class Bullet {
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

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public double getVectorX() {
        return vectorX;
    }

    public void setVectorX(double vectorX) {
        this.vectorX = vectorX;
    }

    public double getVectorY() {
        return vectorY;
    }

    public void setVectorY(double vectorY) {
        this.vectorY = vectorY;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void reduceLifeTime() { lifetime -= 1; }
    public int getLifetime() { return lifetime; }
}
