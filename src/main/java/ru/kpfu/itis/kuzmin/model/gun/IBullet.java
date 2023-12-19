package ru.kpfu.itis.kuzmin.model.gun;

import javafx.scene.image.ImageView;

public interface IBullet {
    double getDamage();

    int getVelocity();

    double getVectorX();

    void setVectorX(double vectorX);

    double getVectorY();

    void setVectorY(double vectorY);

    ImageView getImageView();

    void reduceLifeTime();

    int getLifetime();

    double getCenterX();

    double getCenterY();
}
