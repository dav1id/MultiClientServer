package main.java;
import javafx.application.Application;
import main.Visuals.Controller;
import main.java.Client.Client;
import main.java.Client.ClientApplication;
import main.java.util.MessageLock;

public class ServerInit {
    private static final MessageLock messageLock = new MessageLock();

    public static void main(String[] args){
        new Thread(
                () -> {
                    Server server = new Server(3, 1024);
                    server.run();
                }
        ).start();

        Application.launch(ClientApplication.class);

        Controller controller = ClientApplication.getController();
        controller.setMessageLock(messageLock);

        Client dummyClient = new Client(controller, messageLock);
        dummyClient.run();
    }
}