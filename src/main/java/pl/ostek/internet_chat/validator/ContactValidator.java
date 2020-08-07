package pl.ostek.internet_chat.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.exception.IncorrectContactException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.Contact;
import pl.ostek.internet_chat.repository.ContactRepository;
import pl.ostek.internet_chat.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class ContactValidator{

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public void validate(Contact contact) {
        String userId=contact.getUserId();
        String ownerId=contact.getOwnerId();

        throwExceptionIfUserNotFound(userId);
        throwExceptionIfUserNotFound(ownerId);
        throwExceptionIfUserIdEqualsOwnerId(userId, ownerId);
        throwExceptionIfSuchContactExists(userId, ownerId);
    }

    private void throwExceptionIfSuchContactExists(String userId, String ownerId) {
        if(contactRepository.existsByOwnerIdAndUserId(ownerId,userId))
            throw new IncorrectContactException("Contact to user with id="+userId+" exists!");
    }

    private void throwExceptionIfUserIdEqualsOwnerId(String userId, String ownerId) {
        if(userId.equals(ownerId))
            throw new IncorrectContactException("You cannot add yourself to contacts!");
    }

    private void throwExceptionIfUserNotFound(String userId) {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException("User with id=" + userId + " not found");
    }
}
