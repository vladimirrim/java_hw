package ru.spbau.egorov.hw_4.FTPGUI.controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.spbau.egorov.hw_4.FTPGUI.network.Client;
import ru.spbau.egorov.hw_4.FTPGUI.network.InvalidProtocolException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class handles interactions with user.
 */
public class Controller {
    public TextArea textArea;
    public ListView<String> files;
    private static Client client;
    private static final int DIRECTORY_PREFIX_SIZE = 11;
    private static final int FILE_PREFIX_SIZE = 6;
    private static final String FILE_SEP = System.getProperty("file.separator");

    public void setClient(Client client) {
        Controller.client = client;
    }

    /**
     * Handles clicks on filenames in list view.
     */
    public void onClick() {
        String filename = files.getSelectionModel().getSelectedItem();
        if (filename == null)
            return;
        if (filename.charAt(0) == 'D') {
            try {
                String sep = (FILE_SEP.charAt(0) == textArea.getText().charAt(textArea.getText().length() - 1) ? "" : FILE_SEP);
                onDirectoryClicked(textArea.getText() + sep + filename.substring(DIRECTORY_PREFIX_SIZE));
                textArea.setText(textArea.getText() + sep + filename.substring(DIRECTORY_PREFIX_SIZE) + FILE_SEP);
            } catch (IOException | InvalidProtocolException e) {
                showInfoDialog(e.getLocalizedMessage());
            }
        } else {
            Stage window = new Stage();
            TextArea dest = new TextArea();
            dest.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    String dirName = dest.getText();
                    try {
                        String sep = (FILE_SEP.charAt(0) == textArea.getText().charAt(textArea.getText().length() - 1) ? "" : FILE_SEP);
                        onFileClicked(textArea.getText() + sep + filename.substring(FILE_PREFIX_SIZE), dirName);
                    } catch (IOException | InvalidProtocolException e) {
                        showInfoDialog(e.getLocalizedMessage());
                    }
                    event.consume();
                    window.close();
                }
            });
            Scene scene = new Scene(dest, 500, 30);
            window.setMinHeight(30);
            window.setTitle("Write path to the destination file");
            window.setScene(scene);
            window.showAndWait();
        }
    }

    /**
     * Handles "enter" press in text area.
     *
     * @param keyEvent is the pressed button.
     */
    public void onDirectorySubmitted(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String dirName = textArea.getText();
            try {
                onDirectoryClicked(dirName);
            } catch (IOException | InvalidProtocolException e) {
                showInfoDialog(e.getLocalizedMessage());
            }
            keyEvent.consume();
        }
    }

    /**
     * Save file from server in given location.
     *
     * @param src  is the file path on server.
     * @param dest is the path to save the file.
     * @throws IOException              if there is file or connection problems.
     * @throws InvalidProtocolException if server answer doesn`t match protocol.
     */
    private void onFileClicked(String src, String dest) throws IOException, InvalidProtocolException {
        client.get(src, Files.newOutputStream(Paths.get(dest)));
    }

    /**
     * List files from the server at the given location.
     *
     * @param dirName is the given location.
     * @throws IOException              if there is file or connection problems.
     * @throws InvalidProtocolException if server answer doesn`t match protocol.
     */
    private void onDirectoryClicked(String dirName) throws IOException, InvalidProtocolException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        client.listNames(dirName, baos);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        int size = dis.readInt();
        files.getItems().clear();
        for (int i = 0; i < size; i++) {
            int stringSize = dis.readInt();
            byte[] buffer = new byte[stringSize];
            dis.readFully(buffer);
            boolean isDir = dis.readBoolean();
            files.getItems().add(files.getItems().size(), (isDir ? "Directory: " : "File: ") + new String(buffer));
        }
    }

    private void showInfoDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oops");
        alert.setHeaderText("Looks like there was an error!");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
