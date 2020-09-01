package pl.ostek.internet_chat.exception;

public class ProfileNotFoundException extends RuntimeException{

    public ProfileNotFoundException(String username) {
        super("Profile that belongs to "+ username+" not found!");
    }
}
