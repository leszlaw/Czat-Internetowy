package pl.ostek.internet_chat.exception;

public class ProfileDoesNotExistsException extends RuntimeException{

    public ProfileDoesNotExistsException(String username) {
        super("Profile that belongs to "+ username+" does not exists!");
    }
}
