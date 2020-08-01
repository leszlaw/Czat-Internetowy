package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.ostek.internet_chat.exception.SuchUserExistsException;
import pl.ostek.internet_chat.exception.UserNotFountException;
import pl.ostek.internet_chat.model.SimplifiedUser;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.repository.UserRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    public void initEach() {
        userRepository = mock(UserRepository.class);
        encoder = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepository, encoder);
    }

    @Test
    void loadByUserName_CorrectUsername_ReturnUser() {
        //given
        User expectedUser=new User("1","admin","admin","admin",null);
        given(userRepository.findByUsername("admin"))
                .willReturn(expectedUser);
        //when
        User user=userRepository.findByUsername("admin");
        //then
        assertThat(user).isEqualTo(expectedUser);
        verify(userRepository).findByUsername("admin");
    }

    @ParameterizedTest(name = "Load by username \"{0}\" throws exception.")
    @NullAndEmptySource
    @ValueSource(strings = {"12345","  ", "\t", "\n"})
    void loadByUserName_WrongUsername_ExceptionThrown(String username) {
        //given
        given(userRepository.findByUsername(username)).willReturn(null);
        //expected
        assertThatThrownBy(() -> {
            userService.loadUserByUsername(username);
        }).isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User "+username+" not found!");
        verify(userRepository).findByUsername(username);
    }

    @Test
    void createUser_UserNotExists_CreateNewUserWithEncodedPasswordAndUserRole(){
        //given
        User user=User.builder().username("user").password("user").build();
        String encodedPassword="$2y$10$aj59zdvYC0hzTIoWuj8ALe79aotkQVE2pkLywYOFWNa60X9DwMRDy";
        given(userRepository.existsByUsername("user")).willReturn(false);
        given(encoder.encode("user"))
                .willReturn(encodedPassword);
        //when
        userService.createUser(user);
        //then
        assertThat(user.getPassword()).isEqualTo(encodedPassword);
        assertThat(user.getRole()).isEqualTo("user");
        verify(userRepository).existsByUsername("user");
        verify(encoder).encode("user");
    }

    @Test
    void createUser_UserExists_ExceptionThrown(){
        //given
        User user=User.builder().username("admin").password("admin").build();
        given(userRepository.existsByUsername("admin")).willReturn(true);
        //expected
        assertThatThrownBy(() -> {
            userService.createUser(user);
        }).isInstanceOf(SuchUserExistsException.class)
                .hasMessageContaining("User \""+user.getUsername()+"\""+" already exists!");
        verify(userRepository).existsByUsername("admin");
    }

    @Test
    void findUser_CorrectId_UserReturned(){
        //given
        User given=User.builder().id("1").username("admin").build();
        given(userRepository.findById("1")).willReturn(Optional.of(given));
        //when
        User result=userService.findUser("1");
        //then
        assertThat(result).isEqualTo(given);
    }

    @Test
    void findUser_WrongId_ExceptionThrown(){
        //given
        given(userRepository.findById("1")).willReturn(Optional.empty());
        assertThatThrownBy(() -> {
            userService.findUser("1");
        }).isInstanceOf(UserNotFountException.class)
                .hasMessageContaining("User with id=1 not found");
        verify(userRepository).findById("1");
    }

    @Test
    void findUsersThatBeginWith_CommonValues_ArrayReturned(){
        //given
        List<Object[]> values= Arrays.asList(new Object[]{"1","adam"},
                new Object[]{"2","alice"});
        given(userRepository.selectValuesThatBeginWith("a")).willReturn(values);
        //when
        List<SimplifiedUser> simplifiedUsers=userService.findUsersThatBeginWith("a");
        //then
        assertThat(simplifiedUsers.get(0).getUserId()).isEqualTo("1");
        assertThat(simplifiedUsers.get(0).getUsername()).isEqualTo("adam");
        assertThat(simplifiedUsers.get(1).getUserId()).isEqualTo("2");
        assertThat(simplifiedUsers.get(1).getUsername()).isEqualTo("alice");
        assertThat(simplifiedUsers).hasSize(2);
        verify(userRepository).selectValuesThatBeginWith("a");
    }

    @Test
    void findUsersThatBeginWith_NullSource_ArrayReturned(){
        //given
        List<Object[]> values= Arrays.asList(new Object[]{"1","adam"},
                new Object[]{"2","alice"});
        given(userRepository.selectValuesThatBeginWith("")).willReturn(values);
        //when
        List<SimplifiedUser> simplifiedUsers=userService.findUsersThatBeginWith(null);
        //then
        assertThat(simplifiedUsers.get(0).getUserId()).isEqualTo("1");
        assertThat(simplifiedUsers.get(0).getUsername()).isEqualTo("adam");
        assertThat(simplifiedUsers.get(1).getUserId()).isEqualTo("2");
        assertThat(simplifiedUsers.get(1).getUsername()).isEqualTo("alice");
        assertThat(simplifiedUsers).hasSize(2);
        verify(userRepository).selectValuesThatBeginWith("");
    }



}
