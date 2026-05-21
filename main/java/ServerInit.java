package main.java;
import main.java.util.MessageLock;

public class ServerInit {
    private static final MessageLock messageLock = new MessageLock();

    public static void main(String[] args){
        Server server = new Server(3, 1024);
        server.run();
    }
}