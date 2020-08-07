package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.service.MessageService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody Message message, Principal principal){
        messageService.sendMessage(message,principal.getName());
        log.info(message.toString()+" action=sendMessage status=successful");
    }

    @GetMapping
    public List<Message> getAllMessages(Principal principal){
        var allMessages = messageService.getAllMessages(principal.getName());
        log.info("action=getAllMessages status=successful");
        return allMessages;
    }

}
