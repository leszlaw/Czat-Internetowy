package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.Contact;
import pl.ostek.internet_chat.model.UserDto;
import pl.ostek.internet_chat.repository.ContactRepository;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.ContactValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactValidator contactValidator;

    public void addContactById(String userId,String ownerUsername){
        if(!userRepository.existsByUsername(ownerUsername))
            throw new UserNotFoundException("User with username="+ownerUsername+" not found");
        String ownerId=userRepository.findByUsername(ownerUsername).getId();
        Contact contact=Contact.builder()
                .ownerId(ownerId)
                .userId(userId).build();
        contactValidator.validate(contact);
        contactRepository.save(contact);
    }

    public List<UserDto> getContacts(String ownerUsername){
        if(!userRepository.existsByUsername(ownerUsername))
            throw new UserNotFoundException("User with username="+ownerUsername+" not found");
        return contactRepository.selectUserDtoListByContactsOwner(ownerUsername);
    }

}
