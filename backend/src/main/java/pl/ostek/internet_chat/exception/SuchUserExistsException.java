package pl.ostek.internet_chat.exception;

import pl.ostek.internet_chat.model.entity.User;

public class SuchUserExistsException extends RuntimeException{
    public SuchUserExistsException(User user) {
        super("User \""+user.getUsername()+"\""+" already exists!");
    }
}
