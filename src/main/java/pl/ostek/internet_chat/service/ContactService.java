package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.ContactWithYourselfException;
import pl.ostek.internet_chat.exception.SuchContactExistsException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.Contact;
import pl.ostek.internet_chat.model.SimplifiedUser;
import pl.ostek.internet_chat.repository.ContactRepository;
import pl.ostek.internet_chat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public void addContactById(String userId,String ownerUsername){
        if(!userRepository.existsByUsername(ownerUsername))
            throw new UserNotFoundException("User with username="+ownerUsername+" not found");
        if(!userRepository.existsById(userId))
            throw new UserNotFoundException("User with id="+userId+" not found");
        String ownerId=userRepository.findByUsername(ownerUsername).getId();
        if(ownerId==userId)
            throw new ContactWithYourselfException();
        if(!contactRepository.existsByOwnerIdAndUserId(ownerId,userId)){
            Contact contact=Contact.builder()
                    .ownerId(ownerId)
                    .userId(userId).build();
            contactRepository.save(contact);
        }else
            throw new SuchContactExistsException(userId);
    }

    public List<SimplifiedUser> getContacts(String ownerUsername){
        if(!userRepository.existsByUsername(ownerUsername))
            throw new UserNotFoundException("User with username="+ownerUsername+" not found");
        List<SimplifiedUser> simplifiedUsers=new ArrayList<>();
        contactRepository.selectValuesOfUsersByContactsOwner(ownerUsername).stream().forEach((o)->{
            simplifiedUsers.add(new SimplifiedUser((String)o[0],(String)o[1], (String)o[2]));
        });
        return simplifiedUsers;
    }

}
