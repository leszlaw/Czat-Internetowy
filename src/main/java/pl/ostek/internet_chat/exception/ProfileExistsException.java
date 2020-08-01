package pl.ostek.internet_chat.exception;

import pl.ostek.internet_chat.model.UserProfile;

public class ProfileExistsException extends RuntimeException{
    public ProfileExistsException(UserProfile userProfile) {
        super(userProfile.getUser().getUsername()+" has already created a profile!");
    }
}
