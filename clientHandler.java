package MultipleClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.io.IOException;
import java.net.Socket;

public class clientHandler implements Runnable {
    private Socket socket;

    public clientHandler(Socket socket){
        this.socket = socket;
    }

    public void run(){
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            String line;
            while ((line = in.readLine()) != null){
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
