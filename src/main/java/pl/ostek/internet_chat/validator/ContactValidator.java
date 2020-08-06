package pl.ostek.internet_chat.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.exception.ContactWithYourselfException;
import pl.ostek.internet_chat.exception.SuchContactExistsException;
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
        if(userId.equals(ownerId))
            throw new ContactWithYourselfException();
        if(!userRepository.existsById(userId))
            throw new UserNotFoundException("User with id="+userId+" not found");
        if(!userRepository.existsById(ownerId))
            throw new UserNotFoundException("User with id="+ownerId+" not found");
        if(contactRepository.existsByOwnerIdAndUserId(ownerId,userId))
            throw new SuchContactExistsException(userId);
    }

}
