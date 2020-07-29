package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.ProfileExistsException;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.model.UserProfile;
import pl.ostek.internet_chat.repository.UserProfileRepository;
import pl.ostek.internet_chat.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public void createProfile(UserProfile userProfile,String username){
        User user= userRepository.findByUsername(username);
        if(user.getUserProfile()!=null)
            throw new ProfileExistsException(user);
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);
    }

    public void editProfile(UserProfile userProfile,String username){
        User user=userRepository.findByUsername(username);
        UserProfile toEdit=userProfileRepository.findByUser_Id(user.getId());
        toEdit.setDescription(userProfile.getDescription());
        userProfileRepository.save(toEdit);
    }

}
