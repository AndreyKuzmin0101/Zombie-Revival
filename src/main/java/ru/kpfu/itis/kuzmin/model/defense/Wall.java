package ru.kpfu.itis.kuzmin.model.defense;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.kuzmin.contoller.LevelController;

public abstract class Wall implements IWall{
    private double hp;
    private byte type;
    private double startHp;
    private ImageView image;
    private ProgressBar progressBar;

    public Wall(byte type, double hp, double positionX, double positionY) {
        this.type = type;
        this.hp = hp;
        this.startHp = hp;
        ImageView imageView = new ImageView("/images/stone_wall.png");
        imageView.setLayoutX(positionX - 20);
        imageView.setLayoutY(positionY - 20);
        this.image = imageView;

        progressBar = new ProgressBar(1);
        progressBar.setPrefWidth(40);
        progressBar.setPrefHeight(15);
        progressBar.setLayoutX(positionX - 20);
        progressBar.setLayoutY(positionY - 7);

        LevelController.addWall(this.image);
        LevelController.addHpBar(progressBar);


    }

    @Override
    public double getHp() {
        return hp;
    }

    @Override
    public void setHp(double hp) {
        this.hp = hp;
        this.progressBar.setProgress(hp / this.startHp);
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
