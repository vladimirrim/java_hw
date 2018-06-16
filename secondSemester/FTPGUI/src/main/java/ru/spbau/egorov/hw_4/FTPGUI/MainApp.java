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

    /**
     * Runs the application on local server.
     *
     * @param args are the hostname of the local server and the port on which server will be running.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid number of arguments.Expected 2: <hostname> <port>.");
            return;
        }
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error occurred during parsing port. Error: " + e.getLocalizedMessage());
            return;
        }
        Server server = new Server(port);
        new Thread(server::start).start();
        try {
            sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Error occurred during initializing server. Error: " + e.getLocalizedMessage());
            return;
        }
        hostName = args[0];
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/currentDirectory.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setClient(new Client(hostName, port));
        stage.setTitle("FTP");
        stage.setScene(new Scene(root));
        stage.setMinHeight(350);
        stage.show();
    }
}
