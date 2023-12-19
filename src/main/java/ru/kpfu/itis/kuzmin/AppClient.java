package ru.kpfu.itis.kuzmin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.kuzmin.client.Client;
import ru.kpfu.itis.kuzmin.contoller.LevelResultController;
import ru.kpfu.itis.kuzmin.contoller.LobbyController;
import ru.kpfu.itis.kuzmin.contoller.MainMenuController;
import ru.kpfu.itis.kuzmin.view.MainMenuView;
import ru.kpfu.itis.kuzmin.view.View;

import java.io.IOException;
import java.net.InetAddress;

public class AppClient extends Application {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5555;

    public static void main(String[] args) {
        launch();
    }

    private Stage stage;
    private Client client;

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        stage.setOnCloseRequest(e -> System.exit(0));



        client = new Client(InetAddress.getByName(HOST), PORT, this);
        MainMenuController.client = client;
        LobbyController.client = client;
        LevelResultController.client = client;

        MainMenuView mainMenuView = new MainMenuView();
        stage.setScene(mainMenuView.getScene());

        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setTitle("Zombie Revival");
        stage.show();
    }

    public void setView(View view) {
        Platform.runLater(() -> {
            try {
                Scene scene = view.getScene();
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Scene getView() {
        return stage.getScene();
    }
}
