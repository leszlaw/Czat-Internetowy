package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pl.ostek.internet_chat.exception.BlankMessageException;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.MessageRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;
    private MessageRepository messageRepository;

    @BeforeEach
    public void initEach(){
        messageRepository=mock(MessageRepository.class);
        messageService=new MessageService(messageRepository);
    }

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
        Message message = Message.builder().message(messageString).receiverId(receiverId).build();
        //when
        messageService.sendMessage(message);
        //then
        verify(messageRepository).save(message);
    }

    @ParameterizedTest(name = "Send \"{0}\" to Bob throws exception.")
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void sendMessage_BlankMessageString_ExceptionThrown(String messageString){
        //given
        Message message = Message.builder().message(messageString).receiverId("Bob").build();
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(BlankMessageException.class).hasMessageContaining("Message string should not be empty array or null!");
    }

    @ParameterizedTest(name = "Send \"123\" to \"{0}\" throws exception.")
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void sendMessage_BlankReceiverId_ExceptionThrown(String receiverId){
        //given
        Message message = Message.builder().message("123").receiverId(receiverId).build();
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(BlankMessageException.class).hasMessageContaining("ReceiverId should not be empty array or null!");
    }

    @Test
    void sendMessage_NullMessageObject_ExceptionThrown() {
        //given
        Message message = null;
        //expected
        assertThatThrownBy(() -> {
            messageService.sendMessage(message);
        }).isInstanceOf(BlankMessageException.class).hasMessageContaining("Message object should not be null!");
    }

}