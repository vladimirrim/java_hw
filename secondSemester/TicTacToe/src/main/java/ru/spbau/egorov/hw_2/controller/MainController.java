package ru.spbau.egorov.hw_2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.spbau.egorov.hw_2.engine.Bot;
import ru.spbau.egorov.hw_2.engine.EasyBot;
import ru.spbau.egorov.hw_2.engine.HardBot;

import java.io.IOException;

import static ru.spbau.egorov.hw_2.controller.MainController.CellType.CROSS;
import static ru.spbau.egorov.hw_2.controller.MainController.CellType.NOUGHT;


/**
 * This class linked with gameField.fxml and startMenu.fxml, controls game flow and interactions between player and bot.
 */
public class MainController extends Controller {

    private static GameMode mode = GameMode.EASY_BOT;
    private static HardBot hardBot = new HardBot();
    private static EasyBot easyBot = new EasyBot();
    private static Bot hotSeatBot = new EasyBot();
    private final static String EASY_BOT_WON = "Easy bot won.";
    private final static String EASY_BOT_DRAW = "Draw with easy bot.";
    private final static String EASY_BOT_LOST = "Easy bot lost.";
    private final static String HARD_BOT_LOST = "Hard bot lost.";
    private final static String HARD_BOT_WON = "Hard bot won.";
    private final static String HARD_BOT_DRAW = "Draw with hard bot.";
    private final static String HOT_SEAT_X_WON = "X won in hot seat mode.";
    private final static String HOT_SEAT_O_WON = "O won in hot seat mode.";
    private final static String HOT_SEAT_DRAW = "Draw in hot seat mode.";
    private static CellType curPlayer = CROSS;

    /**
     * Draws certain type in the chosen cell and interacts with player depending on selected game mode.
     *
     * @param actionEvent is the button that was chosen.
     */
    public void onClickMethod(ActionEvent actionEvent) {
        Button button = ((Button) actionEvent.getSource());
        int j = GridPane.getColumnIndex(button), i = GridPane.getRowIndex(button);
        GridPane field = (GridPane) button.getParent();
        makeMoveOnField(field, j * 3 + i, curPlayer);
        switch (mode) {
            case EASY_BOT:
                easyBot.setCell(CROSS, j, i);
                if (easyBot.isDraw()) {
                    statsMan.writeStatistics(EASY_BOT_DRAW);
                    showInfoDialog(EASY_BOT_DRAW, actionEvent);
                    return;
                }
                if (easyBot.isWin(CROSS)) {
                    statsMan.writeStatistics(EASY_BOT_LOST);
                    showInfoDialog(EASY_BOT_LOST, actionEvent);
                    return;
                }
                makeMoveOnField((GridPane) button.getParent(), easyBot.makeMove(CROSS), NOUGHT);
                if (easyBot.isWin(NOUGHT)) {
                    statsMan.writeStatistics(EASY_BOT_WON);
                    showInfoDialog(EASY_BOT_WON, actionEvent);
                    return;
                }
                break;

            case HARD_BOT:
                hardBot.setCell(CROSS, j, i);
                if (hardBot.isDraw()) {
                    statsMan.writeStatistics(HARD_BOT_DRAW);
                    showInfoDialog(HARD_BOT_DRAW, actionEvent);
                    return;
                }
                if (hardBot.isWin(CROSS)) {
                    statsMan.writeStatistics(HARD_BOT_LOST);
                    showInfoDialog(HARD_BOT_LOST, actionEvent);
                    return;
                }
                makeMoveOnField((GridPane) button.getParent(), hardBot.makeMove(CROSS), NOUGHT);
                if (hardBot.isWin(NOUGHT)) {
                    statsMan.writeStatistics(HARD_BOT_WON);
                    showInfoDialog(HARD_BOT_WON, actionEvent);
                    return;
                }
                break;

            case HOT_SEAT:
                hotSeatBot.setCell(curPlayer, j, i);
                if (hotSeatBot.isDraw()) {
                    statsMan.writeStatistics(HOT_SEAT_DRAW);
                    showInfoDialog(HOT_SEAT_DRAW, actionEvent);
                    return;
                }
                if (hotSeatBot.isWin(CROSS)) {
                    statsMan.writeStatistics(HOT_SEAT_X_WON);
                    showInfoDialog(HOT_SEAT_X_WON, actionEvent);
                    return;
                }
                if (hotSeatBot.isWin(NOUGHT)) {
                    statsMan.writeStatistics(HOT_SEAT_O_WON);
                    showInfoDialog(HOT_SEAT_O_WON, actionEvent);
                    return;
                }
                curPlayer = curPlayer == CROSS ? NOUGHT : CROSS;
                break;
        }
    }

    /**
     * Shows new game field in chosen game mode.
     *
     * @param actionEvent is the mode that was chosen.
     */
    public void toGameField(ActionEvent actionEvent) {
        Button button = ((Button) (actionEvent.getSource()));
        easyBot.clearGameField();
        hardBot.clearGameField();
        hotSeatBot.clearGameField();
        curPlayer = CROSS;
        switch (button.getId()) {
            case "hotSeat":
                mode = GameMode.HOT_SEAT;
                break;
            case "hard":
                mode = GameMode.HARD_BOT;
                break;
            case "easy":
                mode = GameMode.EASY_BOT;
                break;
        }

        String fxmlFile = "/fxml/gameField.fxml";
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle("Game Field");
            stage.setScene(new Scene(root));
            stage.setMinHeight(350);
            stage.setMinWidth(350);
            stage.show();
            button.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows game statistics for the current session.
     *
     * @param actionEvent is the button that was chosen.
     */
    public void toGameStats(ActionEvent actionEvent) {
        String fxmlFile = "/fxml/statistics.fxml";
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle("Game Statistics");
            stage.setScene(new Scene(root));
            stage.setMinHeight(350);
            stage.show();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInfoDialog(String content, ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Results");
        alert.setHeaderText("The results are");
        alert.setContentText(content);
        alert.showAndWait();
        toStartMenu(actionEvent);
    }

    private void makeMoveOnField(GridPane pane, int cell, CellType type) {
        Button button = (Button) pane.getChildren().get(cell);
        button.setStyle("-fx-text-alignment: center;-fx-font: 50px font");
        button.setText(type == NOUGHT ? "O" : "X");
        button.setDisable(true);
    }

    public enum CellType {
        EMPTY, NOUGHT, CROSS
    }

    private enum GameMode {
        EASY_BOT, HARD_BOT, HOT_SEAT
    }

}
