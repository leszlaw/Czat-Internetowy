package pl.ostek.internet_chat.validator;

import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.exception.IncorrectProfileException;
import pl.ostek.internet_chat.model.entity.UserProfile;

@Component
public class UserProfileValidator {

    public void validate(UserProfile userProfile){
        if (userProfile == null)
            throw new IncorrectProfileException("UserProfile should not be null!");
        if (stringLongerThan(255, userProfile.getDescription()))
            throw new IncorrectProfileException("Description should be shorter than 256 characters!");
    }

    private boolean stringLongerThan(int length, String string) {
        return string != null && string.length() > length;
    }

}
