package ru.kpfu.itis.kuzmin.contoller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.Game;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.role.Role;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;


public class LevelController {
    @FXML
    private ImageView shooter, engineer;
    @FXML
    private ProgressBar shooterHp, engineerHp;

    public static Player player;
    public static World world;
    public static Teammate teammate;
    public static Game game;

    public static Scene scene;

    @FXML
    void initialize() {
        if (player.getRole().getRoleCode() == Role.SHOOTER) {
            player.getRole().setImage(shooter);
            player.setHpProgressBar(shooterHp);

            teammate.getRole().setImage(engineer);
            teammate.setHpProgressBar(engineerHp);

        } else {
            player.getRole().setImage(engineer);
            player.setHpProgressBar(engineerHp);

            teammate.getRole().setImage(shooter);
            teammate.setHpProgressBar(shooterHp);
        }

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                game.prepareOneFrame();
            }
        };
        animationTimer.start();
    }


    public static void addBullet(ImageView bulletView) {
        Platform.runLater(() -> {
            ((AnchorPane) scene.getRoot()).getChildren().add(bulletView);
        });

    }

    public static void removeBullet(ImageView bulletView) {
        Platform.runLater(() -> {
            ((AnchorPane) scene.getRoot()).getChildren().remove(bulletView);
        });
    }

    public static void addZombie(ImageView zombieView) {
        Platform.runLater(() -> {
            ((AnchorPane) scene.getRoot()).getChildren().add(zombieView);
        });
    }

    public static void removeZombie(Zombie zombie) {
        Platform.runLater(() -> {
            ((AnchorPane) scene.getRoot()).getChildren().remove(zombie.getImage());
            ((AnchorPane) scene.getRoot()).getChildren().remove(zombie.getHpProgressBar());
        });
    }

    public static void addHpBar(ProgressBar hpBar) {
        Platform.runLater(() -> {
            ((AnchorPane) scene.getRoot()).getChildren().add(hpBar);
        });
    }
}
