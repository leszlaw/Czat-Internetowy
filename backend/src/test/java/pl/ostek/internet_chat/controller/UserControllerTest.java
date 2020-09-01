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
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.service.UserService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    BCryptPasswordEncoder encoder;

    @Test
    @WithMockUser
    void createUser_CorrectUsername_NewUserCreated() throws Exception {
        //given
        User user=User.builder().username("bob").build();
        //when
        ResultActions result = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf())
        );
        //then
        result.andExpect(status().isOk())
                .andDo(print());

        verify(userService).createUser(user);
    }


    @Test
    @WithMockUser
    void findUsers_UserStartsWithA_UserReturned() throws Exception {
        UserDto userDto1=new UserDto("1","admin","admin@office.pl");
        UserDto userDto2=new UserDto("3", "alice","alice@office.pl");
        given(userService.findUsersThatBeginWith("a","a")).willReturn(
                Arrays.asList(userDto1,userDto2));
        //when
        ResultActions result = mvc.perform(get("/users?username=a&email=a")
                .contentType(MediaType.APPLICATION_JSON).with(csrf()));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(userDto1,userDto2))))
                .andDo(print());
    }

}
