package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.InternetChatApplication;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.MessageRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void initEach(){
        messageRepository.clear();
    }

    @Test
    public void sendMessage_CorrectMessage_StatusOk() throws Exception {
        //given
        Message message = new Message("123", "Bob");
        String jsonBody = "{\"message\":\"123\",\"receiverId\":\"Bob\",\"senderId\":null}";
        //when
        ResultActions result = mvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(messageRepository.get(message.getReceiverId()).get(0))
                .isEqualTo(message);
    }

    @Test
    public void getAllMessages_ThreeMessages_ReturnJsonArray() throws Exception {
        //given
        Message message1 = new Message("123", "Alice");
        Message message2 = new Message("123", "Alice");
        Message message3 = new Message("123", "Bob");
        messageRepository.put("Alice", Arrays.asList(message1,message2));
        messageRepository.put("Bob", Arrays.asList(message3));
        String expectedString = "{\"Bob\":[{\"message\":\"123\",\"receiverId\":" +
                "\"Bob\",\"senderId\":null}],\"Alice\":" +
                "[{\"message\":\"123\",\"receiverId\":\"" +
                "Alice\",\"senderId\":null},{\"message\":" +
                "\"123\",\"receiverId\":\"Alice\",\"senderId\":null}]}";
        //when
        ResultActions result = mvc.perform(get("/messages")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(expectedString))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.Alice.*", hasSize(2)))
                .andExpect(jsonPath("$.Bob.*", hasSize(1)))
                .andExpect(content().string(objectMapper.writeValueAsString(messageRepository)))
                .andDo(print());

    }
}