package ru.kpfu.itis.kuzmin.model.defense;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public interface IWall {
    double getHp();

    void setHp(double hp);

    ImageView getImage();

    ProgressBar getProgressBar();
}
