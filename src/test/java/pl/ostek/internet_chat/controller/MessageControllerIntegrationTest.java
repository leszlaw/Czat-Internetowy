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
import pl.ostek.internet_chat.repository.MessageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schema.sql")
public class MessageControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void sendMessage_CorrectMessage_StatusOk() throws Exception {
        //given
        String jsonBody = "{\"message\":\"123\",\"receiverId\":\"Bob\",\"senderId\":null}";
        //when
        ResultActions result = mvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(messageRepository.findAll().get(0).getMessage())
                .isEqualTo("123");
        assertThat(messageRepository.findAll().get(0).getReceiverId())
                .isEqualTo("Bob");
    }

    @Test
    @Sql({"/schema.sql","/test-data.sql"})
    public void getAllMessages_ThreeMessages_ReturnJsonArray() throws Exception {
        //given
        String expectedJsonBody = "[{\"message\":\"123\",\"receiverId\":\"Alice\",\"senderId\":\"Eva\"}," +
                "{\"message\":\"123\",\"receiverId\":\"Alice\",\"senderId\":\"Bob\"}," +
                "{\"message\":\"123\",\"receiverId\":\"Bob\",\"senderId\":\"Alice\"}]";
        //when
        ResultActions result = mvc.perform(get("/messages")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedJsonBody))
                .andExpect(content().string(objectMapper.writeValueAsString(messageRepository.findAll())))
                .andDo(print());

    }
}