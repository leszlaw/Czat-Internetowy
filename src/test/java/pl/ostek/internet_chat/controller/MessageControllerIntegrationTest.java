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
import pl.ostek.internet_chat.InternetChatApplication;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.repository.MessageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = InternetChatApplication.class)
@AutoConfigureMockMvc
@Sql({"/schema.sql","/data.sql"})
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
        Message message = Message.builder().message("123").receiverId("Bob").build();
        //when
        ResultActions result = mvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        assertThat(messageRepository.findAll().get(2).getMessage()).isEqualTo("123");
        assertThat(messageRepository.findAll().get(2).getReceiverId()).isEqualTo("Bob");

    }

    @Test
    public void getAllMessages_ThreeMessages_ReturnJsonArray() throws Exception {
        //given
        //when
        ResultActions result = mvc.perform(get("/messages")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(messageRepository.findAll())))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].receiverId", is("Bob")))
                .andExpect(jsonPath("$[0].message", is("123")))
                .andExpect(jsonPath("$[1].receiverId", is("Eva")))
                .andExpect(jsonPath("$[1].message", is("123")))
                .andDo(print());

    }
}