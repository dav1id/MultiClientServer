package v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public void run(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try(SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
            Scanner in = new Scanner(System.in)){

            while(true){
                String message;
                if (in.hasNext() && (message = in.next()) != null){
                    byte[] messageBytes = message.getBytes();
                    buffer.put(messageBytes);
                    buffer.flip();

                    //DEBUG
                    System.out.println(String.format("Sent the message: %s...", message));

                    while(buffer.hasRemaining()){
                        /*
                            Writes the buffer by 'streaming' from one channel to another. Since it's non-blocking,
                            need to verify that buffer has completed the entire stream of bits before closing
                        */
                        socketChannel.write(buffer);
                    }

                    System.out.println("Wrote the message to server channel...");

                    buffer.clear();
                    buffer.flip();
                }
            }
        }  catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
