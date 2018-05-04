package ru.spbau.egorov.cr_2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import ru.spbau.egorov.cr_2.UI.GameCell;
import ru.spbau.egorov.cr_2.controller.Controller;

/**
 * This class runs the application.
 */
public class MainApp extends Application {
    private static int gameFieldSize;

    public static void main(String[] args) {
        gameFieldSize = Integer.valueOf(args[0]);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller(gameFieldSize, stage);

        GridPane gridPane = new GridPane();

        for (int i = 0; i < controller.getGameFieldSize(); i++) {
            for (int j = 0; j < controller.getGameFieldSize(); j++) {
                GameCell cell = new GameCell(controller, i, j);

                GridPane.setConstraints(cell.getButton(), i, j);
                gridPane.getChildren().add(cell.getButton());
            }
        }

        for (int i = 0; i < controller.getGameFieldSize(); i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(columnConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        stage.setTitle("Game Field");
        stage.setScene(new Scene(gridPane, 500, 500));
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.show();
    }
}