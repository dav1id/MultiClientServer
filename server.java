package MultipleClients;

/*
    Creates a new socket connection to the client, and lets a thread handle it so that it can continue waiting for the next client.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class server {
    private static ExecutorService pool;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        pool = Executors.newFixedThreadPool(1);

        ArrayList<ClientHandler> clientArray = new ArrayList<>();

        BufferedReader in = null;
        try {
            serverSocket = new ServerSocket(9090);
            while (true){
                Socket socket = serverSocket.accept();
              //  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               // String string = in.readLine();

                ClientHandler client = new ClientHandler(socket);
                clientArray.add(client);

                client.run();
                if (!(client.getClientHandlerStatus())) break;
                /*
                pool.execute(client);

                for (ClientHandler i : clientArray){
                    if (!(i.getClientHandlerStatus())) break;
                } */

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
