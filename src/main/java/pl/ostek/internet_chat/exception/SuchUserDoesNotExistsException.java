package pl.ostek.internet_chat.exception;

public class SuchUserDoesNotExistsException extends RuntimeException{
    public SuchUserDoesNotExistsException(String username) {
        super("User " + username + " does not exists!");
    }
}
