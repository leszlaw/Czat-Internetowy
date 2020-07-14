package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.InternetChatApplication;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.service.MessageService;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = InternetChatApplication.class)
@AutoConfigureMockMvc
public class MessageControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MessageService messageService;

    @Test
    public void sendMessage_SendMessage_StatusOk() throws Exception {
        //given
        Message message = new Message("123", "Bob");
        //when
        ResultActions result = mvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(message)));
        //then
        result.andExpect(status().isOk());
    }

    @Test
    public void getAllMessages_GetAllMessages_StatusOk() throws Exception {
        //given
        Message message1 = new Message("123", "Alice");
        Message message2 = new Message("123", "Alice");
        Message message3 = new Message("123", "Bob");
        messageService.sendMessage(message1);
        messageService.sendMessage(message2);
        messageService.sendMessage(message3);
        //when
        ResultActions result = mvc.perform(get("/messages")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.Alice.*", hasSize(2)))
                .andExpect(jsonPath("$.Bob.*", hasSize(1)));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}