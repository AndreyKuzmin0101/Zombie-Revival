package ru.kpfu.itis.kuzmin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.kpfu.itis.kuzmin.client.Client;
import ru.kpfu.itis.kuzmin.model.Player;
import ru.kpfu.itis.kuzmin.contoller.LevelController;
import ru.kpfu.itis.kuzmin.protocol.Message;

import java.net.InetAddress;

public class AppClient extends Application {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5555;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Client client = new Client(InetAddress.getByName(HOST), PORT);

        // лобби
        // ...

        Player player = client.startGame(new Message());

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
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.setTitle("Зомби Возрождение");
        stage.show();

    }
}
