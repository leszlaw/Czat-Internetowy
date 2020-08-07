package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.MessageRepository;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.MessageValidator;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageValidator messageValidator;

    public void sendMessage(Message message,String senderUsername) {
        String senderId=userRepository.findIdByUsername(senderUsername);
        message.setSenderId(senderId);
        messageValidator.validate(message);
        messageRepository.save(message);
    }

    public List<Message> getAllMessages(String username) {
        return messageRepository.findByReceiverUsername(username);
    }


}
