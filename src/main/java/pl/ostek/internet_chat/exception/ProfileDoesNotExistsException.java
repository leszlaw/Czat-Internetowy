package pl.ostek.internet_chat.exception;

import pl.ostek.internet_chat.model.User;

public class ProfileDoesNotExistsException extends RuntimeException{

    public ProfileDoesNotExistsException(User user) {
        super("Profile that belongs to "+ user.getUsername()+" does not exists!");
    }
}
