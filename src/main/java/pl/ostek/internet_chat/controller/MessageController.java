package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.model.MessageService;


@RestController
@RequestMapping(value = "messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("")
    public void sendMessage(@RequestBody Message message){
        messageService.sendMessage(message);
    }

}
