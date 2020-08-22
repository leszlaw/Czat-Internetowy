package pl.ostek.internet_chat.mapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.model.entity.Message;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.model.dto.MessageDto;
import pl.ostek.internet_chat.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final UserRepository userRepository;

    public Message convertToEntity(MessageDto messageDto) {
        Message message = new Message();

        User receiver = userRepository.findByUsername(messageDto.getReceiverUsername());
        User sender = userRepository.findByUsername(messageDto.getSenderUsername());

        message.setMessage(messageDto.getMessage());
        message.setReceiver(receiver);
        message.setSender(sender);

        return message;
    }

    public MessageDto convertToDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message.getMessage());
        messageDto.setReceiverUsername(message.getReceiver().getUsername());
        messageDto.setSenderUsername(message.getSender().getUsername());
        return messageDto;
    }

}
