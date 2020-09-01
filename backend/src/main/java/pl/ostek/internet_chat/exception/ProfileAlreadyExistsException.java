package pl.ostek.internet_chat.exception;

import pl.ostek.internet_chat.model.entity.UserProfile;

public class ProfileAlreadyExistsException extends RuntimeException{
    public ProfileAlreadyExistsException(UserProfile userProfile) {
        super(userProfile.getUser().getUsername()+" has already created a profile!");
    }
}
