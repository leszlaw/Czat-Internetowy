package pl.ostek.internet_chat.exception;

public class InvalidMessageDataLengthException extends RuntimeException{
    public InvalidMessageDataLengthException(String message) {
        super(message);
    }
}
