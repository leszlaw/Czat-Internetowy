package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.MessageRepository;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.MessageValidator;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    MessageValidator messageValidator;

    @Test
    void sendMessage_MessageSent_ReceiverHasMessage() {
        //given
        Message message = Message.builder().build();
        given(userRepository.findIdByUsername("admin")).willReturn("1");
        //when
        messageService.sendMessage(message,"admin");
        //then
        verify(messageRepository).save(message);
        assertThat(message.getSenderId()).isEqualTo("1");
    }

}