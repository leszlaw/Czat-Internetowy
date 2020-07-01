package pl.ostek.internet_chat.model;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
