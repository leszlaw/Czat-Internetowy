package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.ostek.internet_chat.exception.MessageServiceException;
import pl.ostek.internet_chat.model.Message;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

class MessageServiceTest {

    @ParameterizedTest(name = "Send {0} to {1}.")
    @CsvSource(value = {
            "Hi:Adam",
            "AdaM:Hi",
            "123:123",
            "@#$:~`*'\"",
            "/\\/:,,,"
    }, delimiter = ':')
    void sendMessage_MessageSent_ReceiverHasMesssage(String messageString, String receiverId) {
        //given
        MessageService messageService = new MessageService();
        Message message = new Message(messageString, receiverId);
        //when
        messageService.sendMessage(message);
        HashMap<String, List<Message>> messageRepository = messageService.getMessageRepository();
        //then
        assertThat(message).isEqualTo(messageRepository.get(receiverId).get(0));

    }


    @ParameterizedTest(name = "Send {0} to {1} not throw exception..")
    @CsvSource(value = {
            "Hi:Adam",
            "AdaM:Hi",
            "123:123",
            "@#$:~`*'\"",
            "/\\/:,,,"
    }, delimiter = ':')
    void sendMessage_CorrectData_ExceptionNotThrown(String messageString, String receiverId) {
        //given
        MessageService messageService = new MessageService();
        Message message = new Message(messageString, receiverId);
        //expected
        assertThatCode(() -> {
            messageService.sendMessage(message);
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "Gives \"{2}\" after sent \"{0}\" to \"{1}\".")
    @CsvSource({
            "'',Adam,Message string should not be empty array or null!",
            "Elo,'',ReceiverId should not be empty array or null!",
            "'','',ReceiverId should not be empty array or null!",
    })
    void sendMessage_InvalidData_ExceptionThrown(String stringMessage, String receiverId, String expectedExceptionMessage) {
        //given
        MessageService messageService = new MessageService();
        Message message = new Message(stringMessage, receiverId);
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(MessageServiceException.class).hasMessageContaining(expectedExceptionMessage);
    }

    @Test
    void sendMessage_NullMessageObject_ExceptionThrown() {
        //given
        MessageService messageService = new MessageService();
        Message message = null;
        String expectedExceptionMessage = "Message object should not be null!";
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(MessageServiceException.class).hasMessageContaining(expectedExceptionMessage);
    }

    @ParameterizedTest(name = "Gives {0} messages for {0} messages sent.")
    @CsvSource({
            "0", "1", "50", "501", "999"
    })
    void getAllMessages_SendXMessages_ReturnXMessages(int count) {
        //given
        MessageService messageService = new MessageService();
        for (int i = 0; i < count; i++)
            messageService.sendMessage(new Message("123", "123"));
        //when
        int result = messageService.getAllMessages().size();
        //then
        assertThat(result).isEqualTo(count);
    }
}