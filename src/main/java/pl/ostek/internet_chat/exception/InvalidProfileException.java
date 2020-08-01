package pl.ostek.internet_chat.exception;

public class InvalidProfileException extends RuntimeException{
    public InvalidProfileException(String message) {
        super(message);
    }
}
