package pl.ostek.internet_chat.service;

import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.model.Message;

import java.util.*;

@Service
public class MessageService {

    private final HashMap<String, List<Message>> messageRepository = new HashMap<>();

    public void sendMessage(Message message) {
        String receiverId = message.getReceiverId();
        if (messageRepository.get(receiverId) == null)
            messageRepository.put(receiverId, new ArrayList<>());
        messageRepository.get(receiverId).add(message);
    }

    public Map<String, List<Message>> getAllMessages() {
        return Collections.unmodifiableMap(messageRepository);
    }

}
