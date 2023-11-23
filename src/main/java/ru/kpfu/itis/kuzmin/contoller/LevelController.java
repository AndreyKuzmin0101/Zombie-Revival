package ru.kpfu.itis.kuzmin.contoller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Bullet;
import ru.kpfu.itis.kuzmin.model.Role;

import java.util.ArrayList;


public class LevelController {
    @FXML
    private ImageView shooter;

    public static Player player;
    public static Scene scene;

    private ArrayList<Bullet> bullets = new ArrayList<>();

    @FXML
    void initialize() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double deltaX = 0;
                double deltaY = 0;
                if (player.isLeft()) {
                    deltaX = - player.getRole().getSpeed();
                }
                if (player.isRight()) {
                    deltaX = player.getRole().getSpeed();
                }
                if (player.isUp()) {
                    deltaY = - player.getRole().getSpeed();
                }
                if (player.isDown()) {
                    deltaY = player.getRole().getSpeed();
                }
                if (player.getRole().getRoleCode() == Role.SHOOTER) {
                    shooter.setLayoutX(shooter.getLayoutX() + deltaX);
                    shooter.setLayoutY(shooter.getLayoutY() + deltaY);
                }

                for (Bullet bullet: bullets) {
                    bullet.getImageView().setX(bullet.getImageView().getX() + bullet.getVelocity() * bullet.getVectorX());
                    bullet.getImageView().setY(bullet.getImageView().getY() + bullet.getVelocity() * bullet.getVectorY());
                }


                if (player.isShoot()) {
                    ImageView bulletView = new ImageView("/images/bullet.png");
                    bulletView.setFitHeight(11);
                    bulletView.setFitWidth(11);
                    bulletView.setLayoutX(shooter.getLayoutX() + 28);
                    bulletView.setLayoutY(shooter.getLayoutY() + 54);

                    double mouseX = player.getMouseX();
                    double mouseY = player.getMouseY();

                    double dx = mouseX - bulletView.getLayoutX();
                    double dy = mouseY - bulletView.getLayoutY();

                    double sqrt = Math.sqrt(dx*dx + dy*dy);
                    double vectorX = dx/sqrt;
                    double vectorY = dy/sqrt;


                    Bullet bullet = new Bullet(5, vectorX, vectorY, bulletView);

                    bullets.add(bullet);
                    ((AnchorPane) scene.getRoot()).getChildren().add(bulletView);
                }
            }
        };
        animationTimer.start();
    }
}
