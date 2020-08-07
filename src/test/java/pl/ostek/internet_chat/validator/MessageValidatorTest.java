package pl.ostek.internet_chat.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ostek.internet_chat.exception.BlankMessageException;
import pl.ostek.internet_chat.exception.IncorrectMessageException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MessageValidatorTest {

    @InjectMocks
    MessageValidator messageValidator;
    @Mock
    UserRepository userRepository;

    @ParameterizedTest(name = "Validate message with message=\"{0}\" throws exception.")
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void validate_BlankMessage_ExceptionThrown(String msg){
        //given
        Message message = Message.builder().message(msg).build();
        //expected
        assertThatThrownBy(() -> {
            messageValidator.validate(message);
        }).isInstanceOf(BlankMessageException.class).hasMessageContaining("Message should not be blank!");
    }

    @Test
    void validate_SenderNotExists_ExceptionThrown(){
        //given
        Message message = Message.builder().message("123").senderId("1").build();
        assertThatThrownBy(() -> {
            messageValidator.validate(message);
        }).isInstanceOf(UserNotFoundException.class).hasMessageContaining("User with id=1 not found");
    }

    @Test
    void validate_ReceiverNotExists_ExceptionThrown(){
        //given
        given(userRepository.existsById("1")).willReturn(true);
        Message message = Message.builder().message("123").senderId("1").receiverId("2").build();
        assertThatThrownBy(() -> {
            messageValidator.validate(message);
        }).isInstanceOf(UserNotFoundException.class).hasMessageContaining("User with id=2 not found");
    }

    @Test
    void validate_SenderEqualsReceiver_ExceptionThrown(){
        //given
        given(userRepository.existsById("1")).willReturn(true);
        Message message = Message.builder().message("123").senderId("1").receiverId("1").build();
        assertThatThrownBy(() -> {
            messageValidator.validate(message);
        }).isInstanceOf(IncorrectMessageException.class).hasMessageContaining("You cannot send message to yourself!");
    }

}
