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
        ServerSocket serverSocket = null;
        pool = Executors.newFixedThreadPool(4);
        try {
            serverSocket = new ServerSocket(9090);
            while (true){
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);

                pool.execute(client);
                // Need a way to close this
            }
        } catch (IOException e){
            if (serverSocket != null) serverSocket.close();
        }
    }
}



/*
Ideas:
    - Give a unique name to each client trying to connect to the server, or even unique names to the sockets themselves

*/
