package pl.ostek.internet_chat.exception;

public class SuchContactExistsException extends RuntimeException{
    public SuchContactExistsException(String userId) {
        super("Contact to user with id="+userId+" exists!");
    }
}
