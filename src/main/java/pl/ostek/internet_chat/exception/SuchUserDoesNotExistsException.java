package pl.ostek.internet_chat.exception;

public class SuchUserDoesNotExistsException extends RuntimeException{
    public SuchUserDoesNotExistsException(String message) {
        super(message);
    }
}
