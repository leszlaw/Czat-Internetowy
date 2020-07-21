package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.service.MessageService;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody Message message){
        messageService.sendMessage(message);
        log.info(message.toString()+" action=sendMessage status=successful");
    }

    @GetMapping
    public Map<String, List<Message>> getAllMessages(){
        var allMessages = messageService.getAllMessages();
        log.info("action=getAllMessages status=successful");
        return allMessages;
    }

}
