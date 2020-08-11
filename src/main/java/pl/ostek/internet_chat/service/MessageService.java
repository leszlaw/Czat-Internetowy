package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.model.entity.Message;
import pl.ostek.internet_chat.mapper.MessageMapper;
import pl.ostek.internet_chat.model.dto.MessageDto;
import pl.ostek.internet_chat.repository.MessageRepository;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.MessageValidator;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageValidator messageValidator;
    private final MessageMapper messageMapper;

    public void sendMessage(MessageDto message) {
        Message msg=messageMapper.convertToEntity(message);
        messageValidator.validate(msg);
        messageRepository.save(msg);
    }

    public List<MessageDto> getAllReceiverMessages(String username) {
        return messageRepository.findDtoByReceiverUsername(username);
    }


}
