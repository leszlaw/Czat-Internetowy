package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.repository.UserRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/schema.sql", "/test-data.sql"})
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void createUser_CorrectUsername_NewUserCreated() throws Exception {
        //given
        String jsonBody = "{\"username\":\"adam\",\"password\":\"malysz\",\"email\":\"adam@office.pl\"}";
        //when
        ResultActions result = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());

        assertThat(userRepository.findByUsername("adam").getUsername()).isEqualTo("adam");
    }

    @Test
    void createUser_UserExists_ConflictShown() throws Exception {
        //given
        String jsonBody = "{\"username\":\"admin\",\"password\":\"admin\",\"email\":\"admin@office.pl\"}";
        //when
        ResultActions result = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isConflict())
                .andExpect(content().string("User with username=admin already exists!"))
                .andDo(print());
    }

    @Test
    void createUser_BlankUser_BadRequestShown() throws Exception {
        //given
        String jsonBody = "{\"username\":\"\",\"password\":\"admin\",\"email\":\"admin@office.pl\"}";
        //when
        ResultActions result = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("User data should not be blank!"))
                .andDo(print());
    }

    @Test
    void findUsers_UserStartsWithA_UserReturned() throws Exception {
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
