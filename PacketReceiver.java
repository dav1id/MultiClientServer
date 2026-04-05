import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class PacketReceiver implements Runnable {
    private PacketTransceiver packetTransceiver = null;
    private final Socket socket;
    private boolean status;

    private Queue<String> messageQueue;

    public PacketReceiver(PacketTransceiver packetTransceiver, Socket socket){
        this.packetTransceiver = packetTransceiver;
        this.socket = socket;

        status = true;

        messageQueue = new ArrayDeque<>();
    }

    public void run(){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            Thread inputFilter = new Thread(() -> {

                String message;
                while (status){
                    if ((message = messageQueue.peek()) != null){
                        String[] concat = message.split("-", 1);

                        if (concat[0].equals("server")){
                            if ((packetTransceiver.messageFunc(message, "setServerMessage"))){
                                System.out.println(message);
                            }
                        }
                    }
                }
            });
            inputFilter.start();

            while (status){
                String message;
                if ((message = in.readLine()) != null){
                    messageQueue.add(message);
                }
            }
        } catch (IOException e){
            Thread.currentThread().interrupt();
            System.out.println("Packet Receiver Error: Client cannot sucessfully receive input from the server.");
        }
    }

    public void setStatus(Boolean status){
        status = false;
    }
}