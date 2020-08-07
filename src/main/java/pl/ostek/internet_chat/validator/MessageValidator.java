package pl.ostek.internet_chat.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.exception.BlankMessageException;
import pl.ostek.internet_chat.exception.IncorrectContactException;
import pl.ostek.internet_chat.exception.IncorrectMessageException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.UserRepository;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
public class MessageValidator {

    private final UserRepository userRepository;

    public void validate(Message message){
        throwExceptionIfMessageIsBlank(message);
        String senderId=message.getSenderId();
        String receiverId=message.getReceiverId();
        throwExceptionIfUserNotFound(senderId);
        throwExceptionIfUserNotFound(receiverId);
        throwExceptionIfSenderIdEqualsReceiverId(senderId,receiverId);
    }

    private void throwExceptionIfMessageIsBlank(Message message) {
        if(message==null||isBlank(message.getMessage()))
            throw new BlankMessageException("Message should not be blank!");
    }

    private void throwExceptionIfUserNotFound(String userId) {
        if (!userRepository.existsById(userId))
            throw new UserNotFoundException("User with id=" + userId + " not found");
    }

    private void throwExceptionIfSenderIdEqualsReceiverId(String senderId, String receiverId) {
        if(senderId.equals(receiverId))
            throw new IncorrectMessageException("You cannot send message to yourself!");
    }

}
