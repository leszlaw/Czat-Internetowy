package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.ostek.internet_chat.exception.BlankMessageException;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.MessageRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class MessageServiceTest {

    private MessageService messageService;

    private MessageRepository messageRepository;

    @BeforeEach
    public void initAll(){
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
        Message message = Message.builder().receiverId(receiverId).message(messageString).build();
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
        Message message = Message.builder().receiverId("Bob").message(messageString).build();
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
        Message message = Message.builder().receiverId(receiverId).message("123").build();
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