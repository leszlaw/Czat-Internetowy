package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.repository.ContactRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Sql({"/schema.sql", "/test-data.sql"})
public class ContactControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ContactRepository contactRepository;

    TokenObtainer tokenObtainer;

    //
    @BeforeAll
    void init(){
        tokenObtainer=new TokenObtainer(mvc);
    }

    @Test
    void addContact_CorrectId_ContactAdded() throws Exception{
        //given
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(post("/contacts?id=3")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(contactRepository.existsByOwnerIdAndUserId("2","3")).isTrue();
    }

    @Test
    void addContact_EmptyToken_StatusUnauthorized() throws Exception{
        //given
        //when
        ResultActions result = mvc.perform(post("/contacts?id=3"));
        //then
        result.andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void addContact_WrongId_IsNotFound() throws Exception{
        //given
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(post("/contacts?id=100")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isNotFound())
                .andExpect(content().string("User with id=100 not found"))
                .andDo(print());
    }

    @Test
    void addContact_ContactOwnerId_isConflict() throws Exception{
        //given
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(post("/contacts?id=2")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isConflict())
                .andExpect(content().string("You cannot add yourself to contacts!"))
                .andDo(print());
    }

    @Test
    void addContact_IdAlreadyInContacts_isConflict() throws Exception{
        //given
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(post("/contacts?id=1")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isConflict())
                .andExpect(content().string("Contact to user with id=1 exists!"))
                .andDo(print());
    }

    @Test
    void getContacts_GivenContacts_ReturnJsonArray() throws Exception{
        //given
        String expectedJsonBody = "[{\"id\":\"2\",\"username\":\"user\",\"email\":\"user@office.pl\"}" +
                ",{\"id\":\"3\",\"username\":\"alice\",\"email\":\"alice@office.pl\"}" +
                ",{\"id\":\"4\",\"username\":\"bob\",\"email\":\"Bob@office.pl\"}]";
        String token = tokenObtainer.obtainAccessToken("admin","admin");
        //when
        ResultActions result = mvc.perform(get("/contacts")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedJsonBody))
                .andExpect(content()
                        .string(objectMapper
                                .writeValueAsString(contactRepository
                                        .selectUserDtoListByContactsOwner("admin"))))
                .andDo(print());
    }

}
