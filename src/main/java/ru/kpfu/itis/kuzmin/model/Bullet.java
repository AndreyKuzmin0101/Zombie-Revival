package ru.kpfu.itis.kuzmin.model;

import javafx.scene.image.ImageView;

public class Bullet {
    private int velocity;
    private double vectorX;
    private double vectorY;
    private ImageView imageView;

    public Bullet(int velocity, double vectorX, double vectorY, ImageView imageView) {
        this.velocity = velocity;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
        this.imageView = imageView;
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
}
