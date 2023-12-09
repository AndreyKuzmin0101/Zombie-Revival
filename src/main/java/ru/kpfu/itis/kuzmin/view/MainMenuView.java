package ru.kpfu.itis.kuzmin.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.kuzmin.AppClient;

import java.io.IOException;

public class MainMenuView implements View{
    @Override
    public Scene getScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(AppClient.class.getResource("/main_menu.fxml"));
        AnchorPane pane = loader.load();

        Scene scene = new Scene(pane);
        return scene;
    }
}
