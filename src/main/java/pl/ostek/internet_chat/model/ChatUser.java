package pl.ostek.internet_chat.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ChatUser {

    private int id;
    private String userName;
    private List<ChatMessage> sent = new ArrayList<>();
    private List<ChatMessage> received = new ArrayList<>();

    public ChatUser(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

}
