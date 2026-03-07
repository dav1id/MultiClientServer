package MultipleClients;

/*
    Creates a new socket connection to the client, and lets a thread handle it so that it can continue waiting for the next client.
*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class server {
    private static ExecutorService pool;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        pool = Executors.newFixedThreadPool(4);

        while (true) {
            Socket socket = serverSocket.accept();
            clientHandler client = new clientHandler(socket);
            pool.execute(client);

        }
    }
}



/*
Ideas:
    - Give a unique name to each client trying to connect to the server, or even unique names to the sockets themselves

*/
