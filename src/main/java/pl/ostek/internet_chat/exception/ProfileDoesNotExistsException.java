package pl.ostek.internet_chat.exception;

import pl.ostek.internet_chat.model.User;

public class ProfileDoesNotExistsException extends RuntimeException{

    public ProfileDoesNotExistsException(String username) {
        super("Profile that belongs to "+ username+" does not exists!");
    }
}
