package pl.ostek.internet_chat.exception;

public class MessageServiceException extends RuntimeException{
    public MessageServiceException(String message) {
        super(message);
    }
}
