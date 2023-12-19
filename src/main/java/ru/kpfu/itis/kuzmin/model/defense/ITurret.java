package ru.kpfu.itis.kuzmin.model.defense;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.model.World;

public interface ITurret {
    void shoot(World world, double elapsedTime);

    double getPositionX();

    double getPositionY();

    double getCenterX();

    double getCenterY();

    double getHp();

    void setHp(double hp);

    ImageView getImage();

    ProgressBar getProgressBar();
}
