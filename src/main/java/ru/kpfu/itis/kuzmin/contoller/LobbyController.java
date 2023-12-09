package ru.kpfu.itis.kuzmin.contoller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import ru.kpfu.itis.kuzmin.client.Client;

public class LobbyController {
    public static String playerNickname;
    public static String teammateNickname;
    public static int code;
    public static Scene scene;
    public static Client client;
    private static Label teammateLabel;

    @FXML
    private Label codeLabel;

    @FXML
    private Label playerLabel;



    @FXML
    void initialize() {
        codeLabel.setText(codeLabel.getText() + code);

        playerLabel.setText(playerNickname);
    }


    @FXML
    void changeLevel(ActionEvent event) {

    }

    @FXML
    void startLevel(ActionEvent event) {

    }

    @FXML
    void leaveLobby(ActionEvent event) {
        client.leaveLobby();
    }

    public static void addPlayer() {
        Platform.runLater(() -> {
            teammateLabel = new Label(teammateNickname);
            teammateLabel.setStyle("-fx-text-fill: white");
            teammateLabel.setFont(Font.font(30));
            teammateLabel.setLayoutX(650);
            teammateLabel.setLayoutY(469);
            ((AnchorPane) scene.getRoot()).getChildren().add(teammateLabel);
        });
    }

    public static void removePlayer() {
        Platform.runLater(() -> {
            ((AnchorPane) scene.getRoot()).getChildren().remove(teammateLabel);
        });
    }

}
