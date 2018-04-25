package ru.spbau.egorov.hw_2.controller;

import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class linked with statistics.fxml, shows statistics to player.
 */
public class StatsController extends Controller implements Initializable {
    public TextFlow textFlow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Text text = new Text(statsMan.getStatistics());
        text.setStyle("-fx-text-alignment: center;-fx-font: 20px font");
        textFlow.getChildren().add(text);
    }
}
