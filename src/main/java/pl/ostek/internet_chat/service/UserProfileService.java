package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.InvalidProfileException;
import pl.ostek.internet_chat.exception.ProfileDoesNotExistsException;
import pl.ostek.internet_chat.exception.ProfileExistsException;
import pl.ostek.internet_chat.exception.SuchUserDoesNotExistsException;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.model.UserProfile;
import pl.ostek.internet_chat.repository.UserProfileRepository;
import pl.ostek.internet_chat.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public void createProfile(UserProfile userProfile, String username) {
        checkIfProfileIsCorrect(userProfile);
        User user = findUser(username);
        if (user.getUserProfile() != null)
            throw new ProfileExistsException(user.getUserProfile());
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);
    }

    public void saveProfile(UserProfile userProfile, String username) {
        checkIfProfileIsCorrect(userProfile);
        User user=findUser(username);
        UserProfile toEdit = user.getUserProfile();
        if (toEdit == null)
            throw new ProfileDoesNotExistsException(user);
        toEdit.setDescription(userProfile.getDescription());
        userProfileRepository.save(toEdit);
    }

    public void editProfileDescription(String description,String username) {
        User user=findUser(username);
        UserProfile userProfile=user.getUserProfile();
        userProfile.setDescription(description);
        userProfileRepository.save(userProfile);
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

    public User findUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new SuchUserDoesNotExistsException(username);
        return user;
    }

}
