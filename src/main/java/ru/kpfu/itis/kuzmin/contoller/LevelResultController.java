package ru.kpfu.itis.kuzmin.contoller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.client.Client;
import ru.kpfu.itis.kuzmin.view.LevelResultView;

import java.io.IOException;

public class LevelResultController {

    public static Client client;

    @FXML
    void restart(ActionEvent event) {
        client.sendStartGame();
    }

    @FXML
    void exit(ActionEvent event) {
        client.leaveLobby();
    }

    @FXML
    void next(ActionEvent event) {

    }

    public static void showResult(byte result, Scene scene) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ((AnchorPane) scene.getRoot()).getChildren().add(LevelResultView.getView(result));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
