package ru.kpfu.itis.kuzmin.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.AppClient;
import ru.kpfu.itis.kuzmin.Game;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.model.role.Engineer;
import ru.kpfu.itis.kuzmin.model.role.Role;
import ru.kpfu.itis.kuzmin.model.zombie.Zombie;

import java.io.IOException;

public class LevelView implements View {
    private Player player;
    private Game game;
    public LevelView (Player player, Game game) {
        this.player = player;
        this.game = game;
    }
    public Scene getScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(AppClient.class.getResource("/level_view.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        LevelController.scene = scene;

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {
                player.setLeft(true);
            }
            if (e.getCode() == KeyCode.D) {
                player.setRight(true);
            }
            if (e.getCode() == KeyCode.W) {
                player.setUp(true);
            }
            if (e.getCode() == KeyCode.S) {
                player.setDown(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) {
                player.setLeft(false);
            }
            if (e.getCode() == KeyCode.D) {
                player.setRight(false);
            }
            if (e.getCode() == KeyCode.W) {
                player.setUp(false);
            }
            if (e.getCode() == KeyCode.S) {
                player.setDown(false);
            }
        });
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            player.setMouseX(event.getX());
            player.setMouseY(event.getY());
            if (player.getMode() == Player.SHOOT) {
                player.setShoot(true);
            } else if (player.getMode() == Player.BUILD_WALL) {
                player.changeMode(Player.SHOOT);

                Engineer engineer = (Engineer) player.getRole();
                if (engineer.getTimer() > 0) return;

                ImageView testImage = new ImageView();
                testImage.setLayoutX(player.getMouseX() - 20);
                testImage.setLayoutY(player.getMouseY() - 20);
                testImage.setFitHeight(40);
                testImage.setFitHeight(40);

                for (Zombie zombie: game.getWorld().getZombies()) {
                    if (zombie.getImage().getBoundsInParent().intersects(testImage.getBoundsInParent())) {
                        player.changeMode(Player.SHOOT);
                        return;
                    }
                }

                if (player.getRole().getImage().getBoundsInParent().intersects(testImage.getBoundsInParent()) ||
                        game.getTeammate().getRole().getImage().getBoundsInParent().intersects(testImage.getBoundsInParent())) {
                    player.changeMode(Player.SHOOT);
                    return;
                }

                player.setWall(game.getWorld());

                engineer.resetTimer();
            }
        });


        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            player.setMouseX(event.getX());
            player.setMouseY(event.getY());
            player.setShoot(false);
        });
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            player.setMouseX(event.getX());
            player.setMouseY(event.getY());
        });

        if (player.getRole().getRoleCode() == Role.ENGINEER) {
            Button wallButton = new Button("", new ImageView("/images/stone_wall.png"));
            wallButton.setLayoutX(1737);
            wallButton.setLayoutY(908);
            wallButton.setPrefWidth(100);
            wallButton.setPrefHeight(100);

            wallButton.setOnAction(event -> {
                player.setShoot(false);
                player.changeMode(Player.BUILD_WALL);
            });
            pane.getChildren().add(wallButton);
        }

        return scene;
    }
}
