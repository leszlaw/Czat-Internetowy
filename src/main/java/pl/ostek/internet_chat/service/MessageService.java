package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.BlankMessageException;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.MessageRepository;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void sendMessage(Message message) {
        if (message == null)
            throw new BlankMessageException("Message object should not be null!");
        if (isBlank(message.getReceiverId()))
            throw new BlankMessageException("ReceiverId should not be empty array or null!");
        if (isBlank(message.getMessage()))
            throw new BlankMessageException("Message string should not be empty array or null!");
        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

}
