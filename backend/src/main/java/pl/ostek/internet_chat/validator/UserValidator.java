package pl.ostek.internet_chat.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.exception.IncorrectUserException;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.repository.UserRepository;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
public class UserValidator {

    public void validate(User user){
        throwExceptionIfUserIsBlank(user);
    }

    private void throwExceptionIfUserIsBlank(User user) {
        if(user==null||isBlank(user.getUsername())||isBlank(user.getPassword())||isBlank(user.getEmail()))
            throw new IncorrectUserException("User data should not be blank!");
    }

}
