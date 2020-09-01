package pl.ostek.internet_chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.ostek.internet_chat.model.entity.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private UserDto user;
    private Gender gender;
    private String description;

}
