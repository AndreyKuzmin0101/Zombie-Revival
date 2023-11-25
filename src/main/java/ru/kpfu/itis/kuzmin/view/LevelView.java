package ru.kpfu.itis.kuzmin.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.AppClient;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Player;

import java.io.IOException;

public class LevelView implements View {
    private Player player;
    public LevelView (Player player) {
        this.player = player;
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
            player.setShoot(true);
        });


        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            player.setShoot(false);
        });
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            player.setMouseX(event.getX());
            player.setMouseY(event.getY());
        });

        return scene;
    }
}
