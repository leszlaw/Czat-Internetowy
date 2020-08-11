package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.dto.MessageDto;
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
    public void sendMessage(@RequestBody MessageDto message, Principal principal){
        message.setSenderUsername(principal.getName());
        messageService.sendMessage(message);
        log.info(message.toString()+" action=sendMessage status=successful");
    }

    @GetMapping
    public List<MessageDto> getAllMessages(Principal principal){
        var allMessages = messageService.getAllReceiverMessages(principal.getName());
        log.info("action=getAllMessages status=successful");
        return allMessages;
    }

}
