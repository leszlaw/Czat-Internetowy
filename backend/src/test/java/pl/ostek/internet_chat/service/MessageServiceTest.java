package pl.ostek.internet_chat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ostek.internet_chat.mapper.MessageMapper;
import pl.ostek.internet_chat.model.dto.MessageDto;
import pl.ostek.internet_chat.model.entity.Message;
import pl.ostek.internet_chat.repository.MessageRepository;
import pl.ostek.internet_chat.validator.MessageValidator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;
    @Mock
    MessageValidator messageValidator;
    @Mock
    MessageMapper messageMapper;

    @Test
    void sendMessage_MessageSent_ReceiverHasMessage() {
        //given
        MessageDto messageDto=new MessageDto();
        Message message = new Message();
        given(messageMapper.convertToEntity(messageDto)).willReturn(message);
        //when
        messageService.sendMessage(messageDto);
        //then
        verify(messageMapper).convertToEntity(messageDto);
        verify(messageValidator).validate(message);
        verify(messageRepository).save(message);
    }

}