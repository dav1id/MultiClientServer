package v2;

/*
   First-Level: Loops through a list of selectionKeys, identifies the server selection channel and checks if a client
   is waiting to be accepted. Checks if other client channels are waiting to be read or written from. Generates a task
   and submits it to a ThreadPool as a ConsumerTask (reading) and a ProducerTask (writing).
*/

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private boolean status;
    public int clientCounter;

    public boolean consumerTask(Set<SelectionKey>  selectionKeys, SelectionKey sender, ExecutorService consumerThreadPool) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try(SocketChannel client = (SocketChannel) sender.channel()){
            int result;
            do {
                result = client.read(buffer);

                if (result == 0){
                    throw new RuntimeException(); // Don't know what to do here
                }
            } while(result != -1);

            String message = new String(buffer.array(), 0, buffer.limit());
            String[] messageArray = message.split(" ");

            for(SelectionKey receiver : selectionKeys){
                ClientMeta clientMeta = (ClientMeta) receiver.attachment();

                if (clientMeta.getClientName().equals(messageArray[0])){

                    consumerThreadPool.submit(
                            () -> {
                                producerTaskWithoutIteration(sender, receiver);
                            }
                    );

                    return true;
                }
            }


        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void producerTaskWithoutIteration(SelectionKey senderKey, SelectionKey receiverKey){
    }


    public boolean producerTask(Set<SelectionKey> selectionKeys, SelectionKey producer){
        ByteBuffer buffer = ByteBuffer.allocate(1024); // Need to understand ThreadLocals to know where to go next with this
        return true;
    }

    public void run(){
        status = true;
        try(
                ExecutorService producerThreadPool = Executors.newFixedThreadPool(3);
                ExecutorService consumerThreadPool = Executors.newFixedThreadPool(3);

                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){

            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(status){
                selector.select();

                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> selectedKeyIterator = selectionKeySet.iterator();

                while(selectedKeyIterator.hasNext()){
                    var selectKey = selectedKeyIterator.next();
                    selectedKeyIterator.remove();

                    if (selectKey.isAcceptable()){
                        try(SocketChannel client = serverSocketChannel.accept()){

                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_ACCEPT);
                            System.out.println("New client has been connected!");

                            selectKey.attach(new ClientMeta(client.getLocalAddress(), clientCounter));
                        }
                        // Accept a new socket channel connection here
                    } else if (selectKey.isReadable()){
                        consumerThreadPool.submit( () -> {
                            consumerTask(selectionKeySet, selectKey, consumerThreadPool);
                            // Space for some future backlog
                        });
                    } else if (selectKey.isWritable()){
                        producerThreadPool.submit( () -> {
                            producerTask(selectionKeySet, selectKey);
                            // Space for some future backlog
                        });
                    }
                }
            }

        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
