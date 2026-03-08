package MultipleClients;


/*
    This class is going to create a new socket connection to the server based on a random port and a fixed ip address.
    It's going to give input to the server using the clientHandler, and recieve output from the server using the serverHandler
*/

import java.net.Socket;

import java.io.IOException;

public class client {
    private static int portNum = 9090;
    private static String serverIP = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        Socket clientSocket = null;
        ServerHandler serverHandler;

        try {
            clientSocket = new Socket(serverIP, portNum);
            serverHandler = new ServerHandler(clientSocket);
            
            serverHandler.run();
            
        } finally{
            if (clientSocket != null) clientSocket.close();
        }
    }
}
