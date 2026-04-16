package v2;

public class ServerInit {
    public static void main(String[] args){
        Client mainClient = new Client();
        Client dummyClient = new Client();

        Server server = new Server();

        server.run();

        mainClient.run();
        dummyClient.run();
    }
}
