package v1;/*

    Reserving two threads per client on the server side
*/

public class ServerInit {
    public static void main(String[] args){
        Server server = new Server();
        server.run();

    }
}
