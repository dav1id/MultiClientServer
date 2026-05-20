package main.java.util;

import java.util.ArrayList;

public class MessageLock {
    private ArrayList<String> messageList;

    public String getMessage(){
        String message = messageList.getFirst();
        messageList.removeFirst();

        return message;
    }

    public void setMessage(String message){
        messageList.add(message);
    }

}
