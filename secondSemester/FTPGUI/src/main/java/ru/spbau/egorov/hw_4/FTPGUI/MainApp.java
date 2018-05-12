package ru.spbau.egorov.hw_4.FTPGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.spbau.egorov.hw_4.FTPGUI.controller.Controller;
import ru.spbau.egorov.hw_4.FTPGUI.network.Client;
import ru.spbau.egorov.hw_4.FTPGUI.network.Server;

import static java.lang.Thread.sleep;

/**
 * This class runs the application.
 */
public class MainApp extends Application {
    private static String hostName;
    private static int port;
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(8888);
        new Thread(server::start).start();
        sleep(200);
        hostName = args[0];
        port = Integer.parseInt(args[1]);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/currentDirectory.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setClient(new Client(hostName,port));
        stage.setTitle("FTP");
        stage.setScene(new Scene(root));
        stage.setMinHeight(350);
        stage.show();
    }
}
