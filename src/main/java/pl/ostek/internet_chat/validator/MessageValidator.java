package pl.ostek.internet_chat.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.exception.BlankMessageException;
import pl.ostek.internet_chat.exception.IncorrectMessageException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.model.entity.Message;
import pl.ostek.internet_chat.model.entity.User;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
public class MessageValidator {

    public void validate(Message message){
        throwExceptionIfMessageIsBlank(message);
        throwExceptionIfUserIsNull(message.getReceiver(),"Receiver not found!");
        throwExceptionIfUserIsNull(message.getSender(),"Sender not found!");
        throwExceptionIfSenderEqualsReceiver(message.getSender(),message.getReceiver());
    }

    private void throwExceptionIfMessageIsBlank(Message message) {
        if(message==null||isBlank(message.getMessage()))
            throw new BlankMessageException("Message should not be blank!");
    }

    private void throwExceptionIfUserIsNull(User user,String message) {
        if (user==null)
            throw new UserNotFoundException(message);
    }

    private void throwExceptionIfSenderEqualsReceiver(User sender, User receiver) {
        if(sender.equals(receiver))
            throw new IncorrectMessageException("You cannot send message to yourself!");
    }

}
