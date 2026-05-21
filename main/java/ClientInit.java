package main.java;
import main.java.Client.Client;
import main.java.Client.ClientApplication;
import main.java.util.MessageLock;

import static javafx.application.Application.launch;

public class ClientInit {
    private final static MessageLock messageLock = new MessageLock();

    public static void main(String[] args){
        Thread clientThread = new Thread( () -> {
            Client client = new Client(messageLock);
            client.run();
        }, "Client-Thread");

        clientThread.start();

        launch(ClientApplication.class);
    }}
