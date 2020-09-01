package pl.ostek.internet_chat.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.model.dto.UserProfileDto;
import pl.ostek.internet_chat.model.entity.UserProfile;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {

    private final UserMapper userMapper;

    public UserProfileDto convertToDto(UserProfile userProfile){
        UserProfileDto userProfileDto=new UserProfileDto();
        UserDto userDto=userMapper.convertToDto(userProfile.getUser());
        userProfileDto.setUser(userDto);
        userProfileDto.setGender(userProfile.getGender());
        userProfileDto.setDescription(userProfile.getDescription());
        return userProfileDto;
    }

}
