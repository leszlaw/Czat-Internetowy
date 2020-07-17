package pl.ostek.internet_chat.service;

import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.BlankMessageException;
import pl.ostek.internet_chat.model.Message;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class MessageService {

    private final HashMap<String, List<Message>> messageRepository = new HashMap<>();

    public void sendMessage(Message message) {
        if (message == null)
            throw new BlankMessageException("Message object should not be null!");
        if (isBlank(message.getReceiverId()))
            throw new BlankMessageException("ReceiverId should not be empty array or null!");
        if (isBlank(message.getMessage()))
            throw new BlankMessageException("Message string should not be empty array or null!");
        String receiverId = message.getReceiverId();
        if (messageRepository.get(receiverId) == null)
            messageRepository.put(receiverId, new ArrayList<>());
        messageRepository.get(receiverId).add(message);
    }

    public Map<String, List<Message>> getAllMessages() {
        return Collections.unmodifiableMap(messageRepository);
    }

}
