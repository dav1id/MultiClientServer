package main.java;
import main.java.Client.Client;
import main.java.Client.ClientApplication;

import static javafx.application.Application.launch;

public class ClientInit {
    public static void main(String[] args){
        launch(ClientApplication.class);

        Client client = new Client();
        client.run();
    }}
