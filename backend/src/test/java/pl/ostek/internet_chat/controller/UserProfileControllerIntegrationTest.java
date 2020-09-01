package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
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
import pl.ostek.internet_chat.model.entity.Gender;
import pl.ostek.internet_chat.repository.UserProfileRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql({"/schema.sql", "/test-data.sql"})
public class UserProfileControllerIntegrationTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserProfileRepository userProfileRepository;

    TokenObtainer tokenObtainer;

    @BeforeAll
    void init(){
        tokenObtainer=new TokenObtainer(mvc);
    }

    @Test
    void createProfile_ProfileDoesNotExist_ProfileCreated() throws Exception {
        //given
        String jsonBody = "{\"gender\":\"FEMALE\",\"description\":\"123\"}";
        String token = tokenObtainer.obtainAccessToken("alice","user");
        //when
        ResultActions result = mvc.perform(post("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(userProfileRepository.findByUsername("alice").getGender()).isEqualTo(Gender.FEMALE);
        assertThat(userProfileRepository.findByUsername("alice").getDescription()).isEqualTo("123");
    }

    @Test
    void createProfile_ProfileExist_isConflict() throws Exception {
        //given
        String jsonBody = "{\"gender\":\"MALE\",\"description\":\"123\"}";
        String token = tokenObtainer.obtainAccessToken("admin","admin");
        //when
        ResultActions result = mvc.perform(post("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isConflict())
                .andExpect(content().string("admin has already created a profile!"))
                .andDo(print());
    }

    @Test
    void createProfile_256CharsDescription_isConflict() throws Exception {
        //given
        String generatedString = RandomStringUtils.random(256, true, true);
        String jsonBody = "{\"gender\":\"MALE\",\"description\":\""+generatedString+"\"}";
        String token = tokenObtainer.obtainAccessToken("admin","admin");
        //when
        ResultActions result = mvc.perform(post("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("Description should be shorter than 256 characters!"))
                .andDo(print());
    }

    @Test
    void updateProfile_CorrectData_ProfileUpdated() throws Exception {
        //given
        String jsonBody = "{\"gender\":\"MALE\",\"description\":\"321\"}";
        String token = tokenObtainer.obtainAccessToken("admin","admin");
        //when
        ResultActions result = mvc.perform(put("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(userProfileRepository.findByUsername("admin").getGender()).isEqualTo(Gender.MALE);
        assertThat(userProfileRepository.findByUsername("admin").getDescription()).isEqualTo("321");
    }

    @Test
    void editProfile_PartialDataUpdates_ProfileUpdated() throws Exception{
        //given
        String jsonBody = "{\"description\":\"new description\"}";
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(patch("/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(userProfileRepository.findByUsername("user").getGender()).isEqualTo(Gender.MALE);
        assertThat(userProfileRepository.findByUsername("user").getDescription()).isEqualTo("new description");
    }

    @Test
    void getProfile_ProfileExists_isOk() throws Exception{
        //given
        String expectedJsonBody = "{\"user\":{\"id\":\"2\",\"username\":\"user\"" +
                ",\"email\":\"user@office.pl\"}" +
                ",\"gender\":\"MALE\",\"description\":\"I am user\"}";
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(get("/profile")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedJsonBody))
                .andDo(print());
    }

    @Test
    void getProfile_ProfileDoesNotExists_isNotFound() throws Exception{
        //given
        String token = tokenObtainer.obtainAccessToken("bob","user");
        //when
        ResultActions result = mvc.perform(get("/profile")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isNotFound())
                .andExpect(content().string("Profile that belongs to bob not found!"))
                .andDo(print());
    }

    @Test
    void getProfile_IdEquals1_isOk() throws Exception{
        //given
        String expectedJsonBody = "{\"user\":{\"id\":\"1\",\"username\"" +
                ":\"admin\",\"email\":\"admin@office.pl\"}" +
                ",\"gender\":\"FEMALE\",\"description\":\"I am admin\"}";
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(get("/profile/1")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedJsonBody))
                .andDo(print());
    }

    @Test
    void getProfile_IdEquals5_isNotFound() throws Exception{
        //given
        String token = tokenObtainer.obtainAccessToken("bob","user");
        //when
        ResultActions result = mvc.perform(get("/profile/5")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isNotFound())
                .andExpect(content().string("Profile that belongs to 5 not found!"))
                .andDo(print());
    }

}
