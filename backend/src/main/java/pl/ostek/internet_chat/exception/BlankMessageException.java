package pl.ostek.internet_chat.exception;

public class BlankMessageException extends RuntimeException{
    public BlankMessageException(String message) {
        super(message);
    }
}
