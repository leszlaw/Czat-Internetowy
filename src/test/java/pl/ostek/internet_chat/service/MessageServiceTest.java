package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pl.ostek.internet_chat.exception.MessageServiceException;
import pl.ostek.internet_chat.model.Message;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

class MessageServiceTest {

    private MessageService messageService = new MessageService();

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
        Message message = new Message(messageString, receiverId);
        //when
        messageService.sendMessage(message);
        Map<String, List<Message>> messageRepository = messageService.getAllMessages();
        //then
        assertThat(message).isEqualTo(messageRepository.get(receiverId).get(0));
    }

    @ParameterizedTest(name = "Send \"{0}\" to Bob throws exception.")
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void sendMessage_blankMessageString_ExceptionThrown(String messageString){
        //given
        Message message = new Message(messageString, "Bob");
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(MessageServiceException.class).hasMessageContaining("Message string should not be empty array or null!");
    }

    @ParameterizedTest(name = "Send \"123\" to \"{0}\" throws exception.")
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void sendMessage_blankReceiverId_ExceptionThrown(String receiverId){
        //given
        Message message = new Message("123", receiverId);
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(MessageServiceException.class).hasMessageContaining("ReceiverId should not be empty array or null!");
    }

    @Test
    void sendMessage_NullMessageObject_ExceptionThrown() {
        //given
        Message message = null;
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(MessageServiceException.class).hasMessageContaining("Message object should not be null!");
    }

}