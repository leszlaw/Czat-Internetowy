package pl.ostek.internet_chat.exception;

public class IncorrectMessageException extends RuntimeException{
    public IncorrectMessageException(String message) {
        super(message);
    }
}
