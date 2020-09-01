package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.ProfileNotFoundException;
import pl.ostek.internet_chat.exception.ProfileAlreadyExistsException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.mapper.UserProfileMapper;
import pl.ostek.internet_chat.model.dto.UserProfileDto;
import pl.ostek.internet_chat.model.entity.Gender;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.model.entity.UserProfile;
import pl.ostek.internet_chat.repository.UserProfileRepository;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.UserProfileValidator;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final UserProfileValidator userProfileValidator;
    private final UserProfileMapper userProfileMapper;

    public void createProfile(UserProfile userProfile, String username) {
        userProfileValidator.validate(userProfile);
        User user = userRepository.findByUsername(username);
        userProfile.setUser(user);
        if (user == null)
            throw new UserNotFoundException("User " + username + " not found!");
        if (user.getUserProfile() != null)
            throw new ProfileAlreadyExistsException(user.getUserProfile());
        userProfileRepository.save(userProfile);
    }

    public void updateProfile(UserProfile userProfile, String username) {
        userProfileValidator.validate(userProfile);
        UserProfile toEdit = getUserProfileByUsername(username);
        toEdit.setDescription(userProfile.getDescription());
        toEdit.setGender(userProfile.getGender());
    }


    public void partialUpdateProfile(Map<String, String> updates, String username) {
        UserProfile toEdit = getUserProfileByUsername(username);
        updates.forEach((s, v) -> {
            if (s.equals("description"))
                toEdit.setDescription(v);
            else if (s.equals("gender"))
                toEdit.setGender(Gender.valueOf(v));
        });
    }

    public UserProfile getUserProfileByUsername(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        if(userProfile==null)
            throw new ProfileNotFoundException(username);
        return userProfile;
    }

    public UserProfileDto getUserProfileDtoById(String id) {
        UserProfile userProfile = userProfileRepository.findByUser_Id(id);
        if(userProfile==null)
            throw new ProfileNotFoundException(id);
        return userProfileMapper.convertToDto(userProfile);
    }

    public UserProfileDto getUserProfileDtoByUsername(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        if(userProfile==null)
            throw new ProfileNotFoundException(username);
        return userProfileMapper.convertToDto(userProfile);
    }

}
