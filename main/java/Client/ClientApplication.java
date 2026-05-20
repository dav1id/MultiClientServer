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
    private static Controller controller;

    public void configureStage(Parent root, Stage stage){
        String css = this.getClass().getResource("/resources/application.css").toExternalForm();

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(css);

        stage.setResizable(false);

        stage.setScene(mainScene);
        stage.show();
    }

    public static Controller getController(){
        return controller;
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

        controller = loader.getController();

        configureStage(root, stage);
    }
}