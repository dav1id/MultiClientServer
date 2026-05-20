package main.java;
import main.Visuals.Controller;
import main.java.Client.Client;
import main.java.Client.ClientApplication;
import main.java.util.MessageLock;

import static javafx.application.Application.launch;

public class ClientInit {
    private final static MessageLock messageLock = new MessageLock();

    public static void main(String[] args){
        //Init
        launch(ClientApplication.class);

        Controller controller = ClientApplication.getController();
        controller.setMessageLock(messageLock);

        Client client = new Client(ClientApplication.getController(), messageLock);
        client.run();
    }}
