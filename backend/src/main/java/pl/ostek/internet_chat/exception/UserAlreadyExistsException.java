package pl.ostek.internet_chat.exception;

import pl.ostek.internet_chat.model.entity.User;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(User user) {
        super("User with username="+user.getUsername()+" already exists!");
    }
}
