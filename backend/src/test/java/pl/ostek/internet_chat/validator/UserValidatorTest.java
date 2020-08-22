package pl.ostek.internet_chat.validator;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ostek.internet_chat.exception.IncorrectUserException;
import pl.ostek.internet_chat.model.entity.User;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    @InjectMocks
    UserValidator userValidator;

    @ParameterizedTest(name = "For User with username={0}, password={1}, email={2} method throws exception")
    @CsvSource(value = {
            ",,",
            ",123,admin@office.pl",
            "admin,,admin@office.pl",
            "admin,123,",
    })
    void validate_BlankUser_ExceptionThrown(String username,String password,String email){
        //given
        User user=User.builder().username(username).password(password).email(email).build();
        //expected
        assertThatThrownBy(() -> {
            userValidator.validate(user);
        }).isInstanceOf(IncorrectUserException.class)
                .hasMessageContaining("User data should not be blank!");
    }

}
