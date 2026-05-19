package main.java;
import main.java.Client.ClientApplication;

public class ClientInit {
    public static void main(String[] args){
        ClientApplication clientApplication = new ClientApplication(args);
        clientApplication.startClientChannel();
    }}
