/*
    Server thread waits for a client to connect and creates a list of the clients that have connected, receives a message
    and adds it to a message buffer. While it waits for a message it can remove clients, and then send a server message
    to the clients of a change in the number of clients.

    This first server thread is connected to three additional threads (this is going to be a inputthreadpool, specific class that extends threadpool) that are going to alternate messages between them.
    i.e., if one thread is called then the second one is called and then finally the third one is called, and then it goes
    back to the second (can make this an enumerator, and then call next()). Calling messageList.add() will call next on the
    enumerator, the three threads will check the enum in their while true loop.

    A second thread that reads from the FIFO queue, decodes the message, and sends it to the proper client while adding
    the client that sends it as a prefix.

    I might need to create a unique serverQueue, just to show how I might be able to create a routing protocol to
    handle messages depending on their priority (which would depend on the client, might randomise one client to be
    priority1 and handle their messages before others in the queue)

    For example:
    Client sends: client3 hello how are you doing -> client3 is going to be the receiver, server knows this in the
    message decode function. Server appends client(sender) as the prefix after the decoder.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.TimeUnit;


enum ThreadNum{
    ThreadOne{
        public ThreadNum next(){
            return ThreadNum.ThreadTwo;
        }
    },
    ThreadTwo{
        public ThreadNum next(){
            return ThreadNum.ThreadThree;
        }
    },
    ThreadThree{
        public ThreadNum next(){
            return ThreadNum.ThreadOne;
        }
    };

    public abstract ThreadNum next();
}


public class ServerInit {
    private Deque<String> messageQueue;
    private boolean status;

    public void decodeMessage(){

    }

    public void InputThread(){ //One thread that acts as input

    }

    public void setStatus(Boolean bool){
        status = bool;
    }


    public void run(){
        ArrayList<Socket> clientsList = new ArrayList<>();
        status = false;

        try{ // the client here has to be final, need a thread pool and worker threads for input and output (2 for input, 2 for output) aka this wont work
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket client = serverSocket.accept();

            Thread inputThread = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    while (!(status)) { // have to create a second try and catch inside of a while loop?
                        String message;
                        if (in.ready()) {
                            if ((message = in.readLine()) != null)
                                messageQueue.add(message);
                        }
                    }
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
            });
            inputThread.start();

            while(true){
                client = serverSocket.accept();
                System.out.println("Client connected to the server... ");
                clientsList.add(client);


                // Need to learn about thread pools here...
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}