package ru.kpfu.itis.kuzmin.contoller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.Game;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.Teammate;
import ru.kpfu.itis.kuzmin.model.World;
import ru.kpfu.itis.kuzmin.model.role.Role;



public class LevelController {
    @FXML
    private ImageView shooter, engineer;
    public static Player player;
    public static World world;
    public static Teammate teammate;
    public static Game game;

    public static Scene scene;

    @FXML
    void initialize() {
        if (player.getRole().getRoleCode() == Role.SHOOTER) {
            player.getRole().setImage(shooter);
            teammate.getRole().setImage(engineer);

        } else {
            player.getRole().setImage(engineer);
            teammate.getRole().setImage(shooter);
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
        ((AnchorPane) scene.getRoot()).getChildren().add(bulletView);
    }

    public static void removeBullet(ImageView bulletView) {
        ((AnchorPane) scene.getRoot()).getChildren().remove(bulletView);
    }
}
