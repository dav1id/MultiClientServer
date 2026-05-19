package main.java;
import main.java.Client.ClientApplication;

public class ServerInit {
    public static void main(String[] args){
        new Thread(
                () -> {
                    Server server = new Server(3, 1024);
                    server.run();
                }
        ).start();

        ClientApplication dummyClient = new ClientApplication(args);
        dummyClient.startClientChannel();
    }
}