package MultipleClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.io.IOException;
import java.net.Socket;

/*
    The server is able to wait for the next client by making a new client handler for every client that connects to it. The client
    handler runs, and accepts/waits for input so that the server doesn't have to be stuck running scripts for one client when other clients
    are trying to connect to it.

    The run() method is overwritten and acts as the 'main' method inside of the class. It contains a while loop that
    only stops when the client types in "close". Hence why we need the clientHandler to be inside of its own thread, because
    it's supposed to run concurrently with the server script.
*/

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void run(){
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            String line;
            while ((line = in.readLine()).compareTo("close") == 0){
                if (line.compareTo("Hello, Server!") == 0){
                    out.println("Hello, Client!");
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
            //Also return the output on the clients side to be the error message
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();

            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }

    }
}
