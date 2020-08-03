package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.ostek.internet_chat.exception.ContactWithYourselfException;
import pl.ostek.internet_chat.exception.SuchContactExistsException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.Contact;
import pl.ostek.internet_chat.model.SimplifiedUser;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.repository.ContactRepository;
import pl.ostek.internet_chat.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ContactServiceTest {

    private ContactService contactService;
    private ContactRepository contactRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach(){
        contactRepository=mock(ContactRepository.class);
        userRepository=mock(UserRepository.class);
        contactService=new ContactService(contactRepository,userRepository);
    }

    @Test
    void addContactById_CorrectId_ContactAdded(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(true);
        given(userRepository.findByUsername("admin")).willReturn(User.builder().id("1").build());
        given(userRepository.existsById("2")).willReturn(true);
        given(contactRepository.existsByOwnerIdAndUserId("1","2")).willReturn(false);
        //when
        contactService.addContactById("2","admin");
        //then
        verify(contactRepository).save(Contact.builder().ownerId("1").userId("2").build());
    }

    @Test
    void addContactById_OwnerNotExists_ExceptionThrown(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(false);
        //expected
        assertThatThrownBy(() -> {
            contactService.addContactById("2","admin");
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with username=admin not found");
    }

    @Test
    void addContactById_UserNotExists_ExceptionThrown(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(true);
        given(userRepository.existsById("2")).willReturn(false);
        //expected
        assertThatThrownBy(() -> {
            contactService.addContactById("2","admin");
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id=2 not found");
    }

    @Test
    void addContactById_ContactExists_ExceptionThrown(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(true);
        given(userRepository.findByUsername("admin")).willReturn(User.builder().id("1").build());
        given(userRepository.existsById("2")).willReturn(true);
        given(contactRepository.existsByOwnerIdAndUserId("1","2")).willReturn(true);
        assertThatThrownBy(() -> {
            contactService.addContactById("2","admin");
        }).isInstanceOf(SuchContactExistsException.class)
                .hasMessageContaining("Contact to user with id=2 exists!");
    }
    @Test
    void addContactById_ContactWithYourself_ExceptionThrown(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(true);
        given(userRepository.findByUsername("admin")).willReturn(User.builder().id("1").build());
        given(userRepository.existsById("1")).willReturn(true);
        assertThatThrownBy(() -> {
            contactService.addContactById("1","admin");
        }).isInstanceOf(ContactWithYourselfException.class)
                .hasMessageContaining("You cannot add yourself to contacts!");
    }

    @Test
    void getAllContacts_CorrectUser_ArrayReturned(){
        //given
        Object[] adam=new Object[]{"1","adam","adam@office.pl"};
        Object[] alice=new Object[]{"2","alice","alice@office.pl"};
        List<Object[]> values= Arrays.asList(adam, alice);
        given(userRepository.existsByUsername("admin")).willReturn(true);
        given(contactRepository.selectValuesOfUsersByContactsOwner("admin")).willReturn(values);
        //when
        List<SimplifiedUser> simplifiedUsers=contactService.getContacts("admin");
        //then
        assertThat(simplifiedUserToArray(simplifiedUsers.get(0))).isEqualTo(adam);
        assertThat(simplifiedUserToArray(simplifiedUsers.get(1))).isEqualTo(alice);
        assertThat(simplifiedUsers).hasSize(2);
    }

    @Test
    void getAllContacts_UsernNotExists_ExceptionThrown(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(false);
        //expected
        assertThatThrownBy(() -> {
            contactService.addContactById("2","admin");
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with username=admin not found");
    }

    private Object[] simplifiedUserToArray(SimplifiedUser simplifiedUser){
        return new Object[]{simplifiedUser.getUserId(),simplifiedUser.getUsername(),simplifiedUser.getEmail()};
    }

}
