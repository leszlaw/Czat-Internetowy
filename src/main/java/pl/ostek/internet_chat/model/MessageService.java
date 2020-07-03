package pl.ostek.internet_chat.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MessageService {

    private final HashMap<String, List<Message>> messageRepository=new HashMap<>();

    public void sendMessage(Message message){

        String receiverId = message.getReceiverId();

        if(messageRepository.get(receiverId) == null)
            messageRepository.put(receiverId,new ArrayList<>());

        messageRepository.get(receiverId).add(message);

    }

}
