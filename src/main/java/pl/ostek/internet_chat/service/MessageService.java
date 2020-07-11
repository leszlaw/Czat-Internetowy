package pl.ostek.internet_chat.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.MessageServiceException;
import pl.ostek.internet_chat.model.Message;

import java.util.*;

@Service
public class MessageService {

    private final HashMap<String, List<Message>> messageRepository = new HashMap<>();

    public void sendMessage(Message message) {
        if (message == null)
            throw new MessageServiceException("Message object should not be null!");
        if (StringUtils.isBlank(message.getReceiverId()))
            throw new MessageServiceException("ReceiverId should not be empty array or null!");
        if (StringUtils.isBlank(message.getMessage()))
            throw new MessageServiceException("Message string should not be empty array or null!");
        String receiverId = message.getReceiverId();
        if (messageRepository.get(receiverId) == null)
            messageRepository.put(receiverId, new ArrayList<>());
        messageRepository.get(receiverId).add(message);
    }

    public Map<String, List<Message>> getAllMessages() {
        return Collections.unmodifiableMap(messageRepository);
    }

}
