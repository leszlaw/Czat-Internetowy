package pl.ostek.internet_chat.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.ostek.internet_chat.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "messages")
public class ChatController {

    private ArrayList<ChatMessage> messages=new ArrayList<>();

    @RequestMapping(method = RequestMethod.POST)
    public void sendMessage(@RequestBody ChatMessage chatMessage){
        messages.add(chatMessage);
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<ChatMessage> getMessages(){
        return messages;
    }

}
