import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private boolean status = false;
    HashMap<String, LinkedBlockingQueue<String>> blockingQueues = new HashMap<>();

    /**
     **/
    public void ProducerInputThread(InputStream inputStream){ // Need a way to exit out of while loop. Maybe program a way for the client to exit
        try(BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))){
            while(status){
                String[] message = null;
                if (in.ready()){
                    message = (in.readLine()).split(" ", 1);

                    for (String client : blockingQueues.keySet()){
                        if (message[0].equals(client)){
                            blockingQueues.get(client).put(message[2]);
                        }
                    }
                }
            }
        } catch(IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    /**

     **/
    public void ConsumerOutputThread(OutputStream outputStream, String key){
        try(PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream))){
            LinkedBlockingQueue<String> queue = blockingQueues.get(key);
            while(status){
                String message = (String) queue.take();
                out.println(message);
            }
        } catch(InterruptedException f){
            System.out.println(f.getMessage());
        }
    };


    public void run(){
        ArrayList<Socket> clientsList = new ArrayList<>();

        ExecutorService inputThreadPool = Executors.newFixedThreadPool(3);
        ExecutorService outputThreadPool = Executors.newFixedThreadPool(3);

        status = true;
        try{
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket client = null;

            while(status){
                client = serverSocket.accept();
                blockingQueues.put(("Client" + blockingQueues.size()), new LinkedBlockingQueue<>());

                server.sendMessage("ClientsList"); //Need a way for the server to send the number of clients to packet receiver

                InputStream inputStream = client.getInputStream();
                OutputStream outputStream = client.getOutputStream();

                inputThreadPool.submit(() -> {
                    ProducerInputThread(inputStream);
                });

                outputThreadPool.submit( () -> {
                    ConsumerOutputThread(outputStream, ("Client" + blockingQueues.size()));
                });

            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}