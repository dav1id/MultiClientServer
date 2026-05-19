package main.java.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Visuals.Controller;
import main.java.util.MessageLock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientApplication extends Application {
    private static volatile MessageLock messageLock = new MessageLock();

    // Client channel reference
    private final Client client;

    // Handles communication between back and front end
    private Controller controller;

    public ClientApplication(String[] args){
        launch(args);

        this.client = new Client();
        client.setMessageLock(messageLock);
    }
    public void configureStage(Parent root, Stage stage){
        String css = this.getClass().getResource("/resources/application.css").toExternalForm();

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(css);

        stage.setResizable(false);

        stage.setScene(mainScene);
        stage.show();
    }

    public void startClientChannel(){
        try{
            while(messageLock == null)
                Thread.sleep(1000);

            client.run();
        } catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    public void start(Stage stage) throws Exception {
        try(SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080))){
            ByteBuffer buff = ByteBuffer.allocate(16);
            if (socketChannel.read(buff) == 1) throw new IOException();
        } catch(IOException e){
            throw new Exception("Socket Error: Server socket is not operational....");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout.fxml"));
        Parent root = loader.load();

        this.controller = loader.getController();
        controller.setMessageLock(messageLock);

        configureStage(root, stage);
    }
}