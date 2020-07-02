package pl.ostek.internet_chat.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {

    private int id;
    private String message;
    private int receiverId;
    private int senderId;

    public ChatMessage(int id, String message, int receiverId) {
        this.id = id;
        this.message = message;
        this.receiverId=receiverId;
    }

}
