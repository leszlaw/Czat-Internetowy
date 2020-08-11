package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.InvalidProfileException;
import pl.ostek.internet_chat.exception.ProfileDoesNotExistsException;
import pl.ostek.internet_chat.exception.ProfileExistsException;
import pl.ostek.internet_chat.exception.SuchUserDoesNotExistsException;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.model.entity.UserProfile;
import pl.ostek.internet_chat.repository.UserProfileRepository;
import pl.ostek.internet_chat.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public void createProfile(UserProfile userProfile, String username) {
        checkIfProfileIsCorrect(userProfile);
        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new SuchUserDoesNotExistsException(username);
        if (user.getUserProfile() != null)
            throw new ProfileExistsException(user.getUserProfile());
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);
    }

    public void updateProfile(UserProfile userProfile, String username) {
        checkIfProfileIsCorrect(userProfile);
        UserProfile toEdit = getUserProfile(username);
        toEdit.setDescription(userProfile.getDescription());
        toEdit.setGender(userProfile.getGender());
        userProfileRepository.save(toEdit);
    }

    public void partialUpdateProfile(UserProfile userProfile,String username) {
        checkIfProfileIsCorrect(userProfile);
        UserProfile toEdit = getUserProfile(username);
        if(userProfile.getDescription()!=null)
            toEdit.setDescription(userProfile.getDescription());
        if(userProfile.getGender()!=null)
            toEdit.setGender(userProfile.getGender());
        userProfileRepository.save(toEdit);
    }

    private boolean stringLongerThan(int length, String string) {
        return string != null && string.length() > length;
    }

    public void checkIfProfileIsCorrect(UserProfile userProfile) {
        if (userProfile == null)
            throw new InvalidProfileException("UserProfile should not be null!");
        if (stringLongerThan(255, userProfile.getDescription()))
            throw new InvalidProfileException("Description should be shorter than 256 characters!");
    }

    public UserProfile getUserProfile(String username) {
        UserProfile toEdit = userProfileRepository.findByUsername(username);
        if (toEdit == null)
            throw new ProfileDoesNotExistsException(username);
        return toEdit;
    }

}
