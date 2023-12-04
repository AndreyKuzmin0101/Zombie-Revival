package ru.kpfu.itis.kuzmin.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.AppClient;
import ru.kpfu.itis.kuzmin.protocol.Message;

import java.io.IOException;

public class LevelResultView {
    public static Parent getView(byte result) throws IOException {
        FXMLLoader loader = null;
        if (result == Message.LOSE) {
            loader = new FXMLLoader(AppClient.class.getResource("/lose_view.fxml"));

        } else if (result == Message.VICTORY) {
            loader = new FXMLLoader(AppClient.class.getResource("/victory_view.fxml"));
        } else {
            throw new RuntimeException("Value of result is undefined.");
        }

        AnchorPane pane = loader.load();
        pane.setLayoutX(450);
        pane.setLayoutY(300);

        return pane;
    }
}
