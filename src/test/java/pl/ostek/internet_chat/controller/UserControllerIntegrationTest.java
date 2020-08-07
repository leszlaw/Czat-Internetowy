package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.model.UserDto;
import pl.ostek.internet_chat.repository.UserRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Sql({"/schema.sql", "/test-data.sql"})
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUser_CorrectUsername_NewUserCreated() throws Exception {
        //given
        String jsonBody = "{\"username\":\"adam\",\"password\":\"malysz\"}";
        //when
        ResultActions result = mvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());

        assertThat(userRepository.findByUsername("adam").getUsername()).isEqualTo("adam");
    }

    @Test
    public void createUser_UsernameExists_ConflictMessageShown() throws Exception {
        //given
        String jsonBody = "{\"username\":\"admin\",\"password\":\"admin\"}";
        //when
        ResultActions result = mvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isConflict())
                .andExpect(content().string("User \"admin\"" + " already exists!"))
                .andDo(print());
    }

    @Test
    public void findUser_UserExists_UserReturned() throws Exception {
        //given
        String expectedJsonBody = "{\"id\":\"1\",\"username\":\"admin\"," +
                "\"email\":\"admin@office.pl\"," +
                "\"userProfile\":{\"gender\":\"FEMALE\",\"description\":\"I am admin\"}}";
        //when
        ResultActions result = mvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedJsonBody))
                .andExpect(content().string(objectMapper.writeValueAsString(userRepository.findById("1"))))
                .andDo(print());
    }

    @Test
    public void findUser_UserNotExist_NotFoundMessageShown() throws Exception {
        //when
        ResultActions result = mvc.perform(get("/users/5")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isNotFound())
                .andExpect(content().string("User with id=5 not found"))
                .andDo(print());
    }

    @Test
    public void findUsers_UserStartsWithA_UserReturned() throws Exception {
        String expectedJsonBody = "[{\"id\":\"1\",\"username\":\"admin\",\"email\":\"admin@office.pl\"}" +
                ",{\"id\":\"3\",\"username\":\"alice\",\"email\":\"alice@office.pl\"}]";
        //when
        ResultActions result = mvc.perform(get("/users?username=a")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedJsonBody))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(new UserDto("1", "admin","admin@office.pl"),new UserDto("3", "alice","alice@office.pl")))))
                .andDo(print());
    }

}
