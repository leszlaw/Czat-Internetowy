package pl.ostek.internet_chat.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ostek.internet_chat.exception.ContactWithYourselfException;
import pl.ostek.internet_chat.exception.SuchContactExistsException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.Contact;
import pl.ostek.internet_chat.repository.ContactRepository;
import pl.ostek.internet_chat.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ContactValidatorTest {

    @InjectMocks
    private ContactValidator contactValidator;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void validate_UserNotExists_ExceptionThrown(){
        //given
        Contact contact=new Contact("1","1","2");
        given(userRepository.existsById("2")).willReturn(false);
        //expected
        assertThatThrownBy(() -> {
            contactValidator.validate(contact);
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id=2 not found");
    }

    @Test
    void validate_OwnerNotExists_ExceptionThrown(){
        //given
        Contact contact=new Contact("1","1","2");
        given(userRepository.existsById("2")).willReturn(true);
        given(userRepository.existsById("1")).willReturn(false);
        //expected
        assertThatThrownBy(() -> {
            contactValidator.validate(contact);
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id=1 not found");
    }

    @Test
    void validate_OwnerIdEqualsUserId_ExceptionThrown(){
        //given
        Contact contact=new Contact("1","1","1");
        //expected
        assertThatThrownBy(() -> {
            contactValidator.validate(contact);
        }).isInstanceOf(ContactWithYourselfException.class)
                .hasMessageContaining("You cannot add yourself to contacts!");
    }

    @Test
    void validate_ContactExists_ExceptionThrown(){
        //given
        Contact contact=new Contact("1","1","2");
        given(userRepository.existsById("2")).willReturn(true);
        given(userRepository.existsById("1")).willReturn(true);
        given(contactRepository.existsByOwnerIdAndUserId("1","2")).willReturn(true);
        //expected
        assertThatThrownBy(() -> {
            contactValidator.validate(contact);
        }).isInstanceOf(SuchContactExistsException.class)
                .hasMessageContaining("Contact to user with id=2 exists!");
    }
}