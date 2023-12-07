package ru.kpfu.itis.kuzmin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.kuzmin.client.Client;
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

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        stage.setOnCloseRequest(e -> System.exit(0));

        Client client = new Client(InetAddress.getByName(HOST), PORT, this);
        client.start();

        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setTitle("Зомби Возрождение");
        stage.show();
    }

    public void setView(View view) {
        Platform.runLater(() -> {
            try {
                stage.setScene(view.getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Scene getView() {
        return stage.getScene();
    }
}
