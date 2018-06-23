package ru.spbau.egorov.cr_2.controller;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.egorov.cr_2.UI.GameCell;
import ru.spbau.egorov.cr_2.engine.GameEngine;
import ru.spbau.egorov.cr_2.engine.InvalidGameFieldSizeException;

/**
 * This class control Pairs game flow.
 */
public class Controller {
    private final int gameFieldSize;
    private final GameEngine gameEngine;
    private ClickMode mode = ClickMode.FIRST_CLICK;
    private GameCell firstCell;
    private Stage mainStage;

    public Controller(int size, @NotNull Stage stage) throws InvalidGameFieldSizeException {
        gameFieldSize = size;
        mainStage = stage;
        gameEngine = new GameEngine(size);
    }

    public int getGameFieldSize() {
        return gameFieldSize;
    }

    /**
     * Handles button click depending on current game state.
     *
     * @param cell is the button that was click.
     */
    public void clickCell(@NotNull GameCell cell) {
        cell.disable(String.valueOf(gameEngine.getCellNumber(cell.getRow(), cell.getCol())));
        gameEngine.setInactive(cell.getRow(), cell.getCol());
        if (gameEngine.isEnded()) {
            showInfoDialog();
            return;
        }
        if (mode == ClickMode.FIRST_CLICK) {
            firstCell = cell;
            mode = ClickMode.SECOND_CLICK;
        } else {
            mode = ClickMode.FIRST_CLICK;
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ignored) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(event -> {
                if (gameEngine.getCellNumber(cell.getRow(), cell.getCol()) != gameEngine.getCellNumber(firstCell.getRow(), firstCell.getCol())) {
                    gameEngine.setActive(firstCell.getRow(), firstCell.getCol());
                    gameEngine.setActive(cell.getRow(), cell.getCol());
                    firstCell.enable();
                    cell.enable();
                }
            });
            new Thread(sleeper).start();
        }
    }

    private void showInfoDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Results");
        alert.setHeaderText("You won!");
        alert.setContentText("Congratulations!");
        alert.showAndWait();
        mainStage.close();
    }

    private enum ClickMode {
        FIRST_CLICK, SECOND_CLICK
    }
}
