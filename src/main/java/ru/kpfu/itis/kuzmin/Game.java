package ru.kpfu.itis.kuzmin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.model.Shooter;

public class Game extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Game.class.getResource("/level_view.fxml"));
        AnchorPane pane = loader.load();


        Player player = new Player(new Shooter());
        LevelController.player = player;

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
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            player.setMouseX(event.getX());
            player.setMouseY(event.getY());
        });

        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setTitle("Зомби Возрождение");
        stage.sizeToScene();
        stage.show();

    }
}
