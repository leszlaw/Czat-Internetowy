package pl.ostek.internet_chat.mapper;

import org.springframework.stereotype.Component;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.model.entity.User;

@Component
public class UserMapper {

    public UserDto convertToDto(User user){
        UserDto userDto=new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

}
