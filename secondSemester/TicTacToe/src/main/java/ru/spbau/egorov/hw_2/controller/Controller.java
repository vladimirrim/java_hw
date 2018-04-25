package ru.spbau.egorov.hw_2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ru.spbau.egorov.hw_2.StatisticsManager;

import java.io.IOException;


/**
 * This class implements common functions in Main and Stats controllers and contains common statistics manager.
 */
public class Controller {
    protected static StatisticsManager statsMan = new StatisticsManager();

    public void toStartMenu(ActionEvent actionEvent) {
        String fxmlFile = "/fxml/startMenu.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("TicTacToe");
        assert root != null;
        stage.setScene(new Scene(root));
        stage.setMinHeight(350);
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
