package ru.kpfu.itis.kuzmin.contoller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.kpfu.itis.kuzmin.client.Client;

public class MainMenuController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField codeField;

    public static Client client;

    @FXML
    void createLobby(ActionEvent event) {
        if (nameField.getText() != null && !nameField.getText().equals("")) {
            client.start(nameField.getText(), null);
        }
    }

    @FXML
    void joinLobby(ActionEvent event) {
        if (nameField.getText() != null && !nameField.getText().equals("")
                && codeField != null && !codeField.getText().equals("")) {
            Integer code = null;
            try {
                code = Integer.valueOf(codeField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Лобби с таким кодом не существует");
            }
            client.start(nameField.getText(), code);
        }
    }

}
