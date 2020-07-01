package pl.ostek.internet_chat.model;

import java.util.ArrayList;
import java.util.List;

public class ChatUser {

    private int id;
    private String userName;
    private List<ChatMessage> sent = new ArrayList<>();
    private List<ChatMessage> received = new ArrayList<>();


    public ChatUser(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ChatMessage> getSent() {
        return sent;
    }

    public void setSent(List<ChatMessage> sent) {
        this.sent = sent;
    }

    public List<ChatMessage> getReceived() {
        return received;
    }

    public void setReceived(List<ChatMessage> received) {
        this.received = received;
    }


}
