package pl.ostek.internet_chat.exception;

public class ContactWithYourselfException extends RuntimeException{
    public ContactWithYourselfException() {
        super("You cannot add yourself to contacts!");
    }
}
