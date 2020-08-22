package pl.ostek.internet_chat.exception;

public class IncorrectUserException extends RuntimeException{
    public IncorrectUserException(String message) {
        super(message);
    }
}
