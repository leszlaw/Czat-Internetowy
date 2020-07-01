package pl.ostek.internet_chat.controller;

import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.ChatMessage;
import pl.ostek.internet_chat.model.ChatUser;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "users")
public class ChatController {

    private HashMap<Integer,ChatUser> users=new HashMap<>();

    @RequestMapping(method = RequestMethod.POST)
    public void addUser(@RequestBody ChatUser chatUser){
        users.put(chatUser.getId(),chatUser);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ChatUser getUser(@PathVariable int id){
        return users.get(id);
    }

    @RequestMapping(value = "/{senderId}/messages",method = RequestMethod.POST)
    public void sendMessage(@PathVariable int senderId,@RequestBody ChatMessage chatMessage){
        ChatUser sender=users.get(senderId);
        ChatUser receiver=users.get(chatMessage.getReceiverId());

        chatMessage.setSenderId(senderId);

        sender.getSent().add(chatMessage);
        receiver.getReceived().add(chatMessage);
    }

    @RequestMapping(value = "/{userId}/messages/received",method = RequestMethod.GET)
    public List<ChatMessage> getReceivedMessages(@PathVariable int userId){
        return users.get(userId).getReceived();
    }

    @RequestMapping(value = "/{userId}/messages/sent",method = RequestMethod.GET)
    public List<ChatMessage> getSentMessages(@PathVariable int userId){
        return users.get(userId).getSent();
    }

}
