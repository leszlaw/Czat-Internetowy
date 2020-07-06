package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.ostek.internet_chat.exception.MessageServiceException;
import pl.ostek.internet_chat.model.Message;

import java.util.HashMap;
import java.util.List;

class MessageServiceTest {

    @Test
    void shouldSendMessage() {
        //given
        MessageService messageService = new MessageService();
        Message message = new Message("hi", "adam");
        //when
        messageService.sendMessage(message);
        HashMap<String, List<Message>> messageRepository = messageService.getMessageRepository();
        //then
        Assertions.assertEquals(message, messageRepository.get("adam").get(0));
        Assertions.assertDoesNotThrow(() -> {
            messageService.sendMessage(message);
        });
    }

    @ParameterizedTest(name = "Gives \"{2}\" after sent \"{0}\" to \"{1}\".")
    @CsvSource({
            "'',Adam,Message string should not be empty array or null!",
            "Elo,'',ReceiverId should not be empty array or null!",
            "'','',ReceiverId should not be empty array or null!",
    })
    void shouldNotSendMessage(String stringMessage, String receiverId, String expectedExceptionMessage) {
        //given
        MessageService messageService = new MessageService();
        Message message = new Message(stringMessage, receiverId);
        //expected
        Assertions.assertThrows(MessageServiceException.class, () -> {
            messageService.sendMessage(message);
        },expectedExceptionMessage);
    }

    @Test
    void shouldNotSendNullObjectMessage() {
        //given
        MessageService messageService = new MessageService();
        Message message = null;
        String expectedExceptionMessage="Message object should not be null!";
        //expected
        Assertions.assertThrows(MessageServiceException.class, () -> {
            messageService.sendMessage(message);
        },expectedExceptionMessage);
    }

    @ParameterizedTest(name = "Gives {0} messages for {0} messages sent.")
    @CsvSource({
            "0", "1", "50", "501", "999"
    })
    void shouldGetAllMessages(int count) {
        //given
        MessageService messageService = new MessageService();
        for (int i = 0; i < count; i++)
            messageService.sendMessage(new Message("123", "123"));
        //when
        int result = messageService.getAllMessages().size();
        //then
        Assertions.assertEquals(result, count);
    }
}