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
import pl.ostek.internet_chat.model.entity.Message;
import pl.ostek.internet_chat.model.entity.User;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class MessageValidatorTest {

    @InjectMocks
    MessageValidator messageValidator;

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
        Message message = Message.builder().message("123").receiver(new User()).build();
        assertThatThrownBy(() -> {
            messageValidator.validate(message);
        }).isInstanceOf(UserNotFoundException.class).hasMessageContaining("Sender not found!");
    }

    @Test
    void validate_ReceiverNotExists_ExceptionThrown(){
        //given
        Message message = Message.builder().message("123").build();
        assertThatThrownBy(() -> {
            messageValidator.validate(message);
        }).isInstanceOf(UserNotFoundException.class).hasMessageContaining("Receiver not found!");
    }

    @Test
    void validate_SenderEqualsReceiver_ExceptionThrown(){
        //given
        User user=new User();
        Message message = Message.builder().message("123").receiver(user).sender(user).build();
        assertThatThrownBy(() -> {
            messageValidator.validate(message);
        }).isInstanceOf(IncorrectMessageException.class).hasMessageContaining("You cannot send message to yourself!");
    }

}
