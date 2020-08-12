package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.entity.Contact;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.repository.ContactRepository;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.ContactValidator;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ContactValidator contactValidator;

    @Test
    void addContactById_CorrectId_ContactAdded(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(true);
        given(userRepository.findByUsername("admin")).willReturn(User.builder().id("1").build());
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
    void getAllContacts_CorrectUser_ArrayReturned(){
        //given
        UserDto adam=new UserDto("1","adam","adam@office.pl");
        UserDto alice=new UserDto("2","alice","alice@office.pl");
        List<UserDto> values= Arrays.asList(adam, alice);
        given(userRepository.existsByUsername("admin")).willReturn(true);
        given(contactRepository.selectUserDtoListByContactsOwner("admin")).willReturn(values);
        //when
        List<UserDto> userDtos =contactService.getContacts("admin");
        //then
        assertThat(userDtos).isEqualTo(values);
        assertThat(userDtos).hasSize(2);
    }

    @Test
    void getAllContacts_UserNotExists_ExceptionThrown(){
        //given
        given(userRepository.existsByUsername("admin")).willReturn(false);
        //expected
        assertThatThrownBy(() -> {
            contactService.addContactById("2","admin");
        }).isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with username=admin not found");
    }

}
