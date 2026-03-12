package MultipleClients;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Runnable;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerHandler implements Runnable{
    private Socket client;
    
    public ServerHandler(Socket client){
        this.client = client;
    }

    public void run(){
        BufferedReader in = null;
        Scanner scanner;
        PrintWriter out = null;

        try{ // Out is going to be what we're sending to the server, and in is what the server is giving us
           // in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(client.getOutputStream());
            out.println(new InputStreamReader(System.in));

            scanner = new Scanner(System.in);

            while (true){
                String input = scanner.nextLine();
                if (input != null){
                    System.out.println(input);
                    out.println(input);

                    if (input.equals("close"))
                        break;
                }
            }

            /*
            String line;
            while(true){
                if ((line = in.readLine()) != null){
                    out.println(line);
                    if (line.equals("Socket closed")) break;
                }
            } */

            System.out.println("Exiting out");
            
        } catch(IOException e){
            System.out.println(e.getMessage());
        } finally{
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
