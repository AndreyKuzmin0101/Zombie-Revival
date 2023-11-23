package ru.kpfu.itis.kuzmin.contoller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.gun.Bullet;
import ru.kpfu.itis.kuzmin.model.Role;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;


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
                movePlayer();

                moveBullets();



                shoot();
            }
        };
        animationTimer.start();
    }

    private void movePlayer() {
        double deltaX = 0;
        double deltaY = 0;
        if (player.isLeft() && shooter.getLayoutX() > 0) {
            deltaX = - player.getRole().getSpeed();
        }
        if (player.isRight() && shooter.getLayoutX() < 1480) {
            deltaX = player.getRole().getSpeed();
        }
        if (player.isUp() && shooter.getLayoutY() > 0) {
            deltaY = - player.getRole().getSpeed();
        }
        if (player.isDown() && shooter.getLayoutY() < 750) {
            deltaY = player.getRole().getSpeed();
        }
        if (player.getRole().getRoleCode() == Role.SHOOTER) {
            shooter.setLayoutX(shooter.getLayoutX() + deltaX);
            shooter.setLayoutY(shooter.getLayoutY() + deltaY);
        }
    }

    private void moveBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.reduceLifeTime();

            if (bullet.getLifetime() <= 0) {
                iterator.remove();
                ((AnchorPane) scene.getRoot()).getChildren().remove(bullet.getImageView());
            }
            bullet.getImageView().setX(bullet.getImageView().getX() + bullet.getVelocity() * bullet.getVectorX());
            bullet.getImageView().setY(bullet.getImageView().getY() + bullet.getVelocity() * bullet.getVectorY());
        }
    }


    private void shoot() {
        player.getWeapon().reduceInterval();

        if (player.isShoot() && player.getWeapon().getInterval() <= 0) {
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


            Bullet bullet = new Bullet(player.getWeapon().getDamage(),5, vectorX, vectorY, bulletView);

            bullets.add(bullet);

            ((AnchorPane) scene.getRoot()).getChildren().add(bulletView);
            player.getWeapon().resumeInterval();
        }
    }
}
