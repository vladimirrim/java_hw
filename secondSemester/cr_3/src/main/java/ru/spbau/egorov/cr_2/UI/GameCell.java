package ru.spbau.egorov.cr_2.UI;

import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;
import ru.spbau.egorov.cr_2.controller.Controller;

/**
 * This class represent game cell in the Pairs game.
 */
public class GameCell {

    private final @NotNull Button button;
    private final int row;
    private final int col;


    /**
     * Creates game cell with button linked to the given controller.
     * @param controller is the handler of button clicks.
     * @param row is the row of the button on the game field.
     * @param col is the col of the button on the game field.
     */
    public GameCell(@NotNull Controller controller,int row,int col) {
        this.row=row;
        this.col =col;

        button = new Button();
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        button.setOnAction(event -> controller.clickCell(GameCell.this));
    }

    @NotNull
    public Button getButton() {
        return button;
    }

    public int getCol() {
        return col;
    }

    /**
     * Clears text on the button and makes it active.
     */
    public void enable(){
        button.setText("");
        button.setDisable(false);
    }

    /**
     * Writes given text on the button
     * @param text isthe text to write.
     */
    public void disable(String text){
        button.setText(text);
        button.setStyle("-fx-text-alignment: center;-fx-font: 10px font");
        button.setDisable(true);
    }

    public int getRow() {
        return row;
    }
}
