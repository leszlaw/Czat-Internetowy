package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.model.dto.UserProfileDto;
import pl.ostek.internet_chat.model.entity.Gender;
import pl.ostek.internet_chat.model.entity.UserProfile;
import pl.ostek.internet_chat.service.UserProfileService;
import pl.ostek.internet_chat.service.UserService;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserProfileController.class)
@AutoConfigureMockMvc
public class UserProfileControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    BCryptPasswordEncoder encoder;

    @MockBean
    UserProfileService userProfileService;

    @Test
    @WithMockUser
    void createProfile_PostOnProfile_ProfileCreated() throws Exception {
        //given
        UserProfile userProfile=new UserProfile();
        //when
        ResultActions result = mvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfile))
                .with(csrf())
        );
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        verify(userProfileService).createProfile(userProfile,"user");
    }

    @Test
    @WithMockUser
    void saveProfile_PutOnProfile_ProfileSaved() throws Exception {
        //given
        UserProfile userProfile=new UserProfile();
        //when
        ResultActions result = mvc.perform(put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfile))
                .with(csrf())
        );
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        verify(userProfileService).updateProfile(userProfile,"user");
    }

    @Test
    @WithMockUser
    void editProfile_PatchOnProfile_ProfileEdited() throws Exception {
        //given
        Map<String,String> updates=Map.of("description","123");
        //when
        ResultActions result = mvc.perform(patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updates))
                .with(csrf())
        );
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        verify(userProfileService).partialUpdateProfile(updates,"user");
    }

    @Test
    @WithMockUser
    void getProfile_GetOnProfile_ProfileReturned() throws Exception{
        //given
        UserDto userDto=new UserDto("1","user","user@office.pl");
        UserProfileDto userProfileDto=new UserProfileDto(userDto, Gender.MALE,"123");
        given(userProfileService.getUserProfileDtoByUsername("user")).willReturn(userProfileDto);
        //when
        ResultActions result = mvc.perform(get("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileDto))
                .with(csrf())
        );
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userProfileDto)))
                .andExpect(jsonPath("$.user.id", is(userDto.getId())))
                .andExpect(jsonPath("$.user.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.user.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.gender", is(userProfileDto.getGender().toString())))
                .andExpect(jsonPath("$.description", is(userProfileDto.getDescription())))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getProfile_GetOnProfileWithId_ProfileReturned() throws Exception{
        //given
        UserDto userDto=new UserDto("1","admin","admin@office.pl");
        UserProfileDto userProfileDto=new UserProfileDto(userDto, Gender.MALE,"123");
        given(userProfileService.getUserProfileDtoById("1")).willReturn(userProfileDto);
        //when
        ResultActions result = mvc.perform(get("/profile/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileDto))
                .with(csrf())
        );
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userProfileDto)))
                .andExpect(jsonPath("$.user.id", is(userDto.getId())))
                .andExpect(jsonPath("$.user.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.user.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.gender", is(userProfileDto.getGender().toString())))
                .andExpect(jsonPath("$.description", is(userProfileDto.getDescription())))
                .andDo(print());
    }




}
