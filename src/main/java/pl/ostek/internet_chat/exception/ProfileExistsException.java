package pl.ostek.internet_chat.exception;

import pl.ostek.internet_chat.model.User;

public class ProfileExistsException extends RuntimeException{
    public ProfileExistsException(User user) {
        super(user.getUsername()+" has already created a profile!");
    }
}
