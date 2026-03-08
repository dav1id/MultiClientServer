package MultipleClients;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Runnable;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket client;
    
    public ServerHandler(Socket client){
        this.client = client;
    }

    public void run(){
        BufferedReader in;
        PrintWriter out; 
        
        try{
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(client.getOutputStream());

            String line;
            while(true){
                System.out.println(("Server says: " + out));
                line = in.readLine();
                if (line.compareTo("close") == 0) break;
            }
            
        } catch(IOException e){
            
        }
    };
}
