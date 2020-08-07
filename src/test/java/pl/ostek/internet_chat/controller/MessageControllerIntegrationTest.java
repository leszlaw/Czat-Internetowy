package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.repository.MessageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Sql({"/schema.sql", "/test-data.sql"})
public class MessageControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MessageRepository messageRepository;

    TokenObtainer tokenObtainer;

    @BeforeAll
    void init(){
        tokenObtainer=new TokenObtainer(mvc);
    }

    @Test
    void sendMessage_CorrectMessage_StatusOk() throws Exception {
        //given
        String jsonBody = "{\"message\":\"123\",\"receiverId\":\"1\"}";
        String token = tokenObtainer.obtainAccessToken("user","user");
        //when
        ResultActions result = mvc.perform(post("/messages")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(messageRepository.findAll().get(0).getMessage())
                .isEqualTo("123");
        assertThat(messageRepository.findAll().get(0).getReceiverId())
                .isEqualTo("2");
    }

    @Test

    void getAllMessages_TwoMessages_ReturnJsonArray() throws Exception {
        //given
        String expectedJsonBody = "[{\"message\":\"123\",\"receiverId\":\"1\",\"senderId\":\"2\"}," +
                "{\"message\":\"123\",\"receiverId\":\"1\",\"senderId\":\"3\"}]";
        String token = tokenObtainer.obtainAccessToken("admin","admin");
        //when
        ResultActions result = mvc.perform(get("/messages")
                .header("Authorization", "Bearer " + token));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedJsonBody))
                .andExpect(content().string(objectMapper.writeValueAsString(messageRepository.findByReceiverUsername("admin"))))
                .andDo(print());
    }
}