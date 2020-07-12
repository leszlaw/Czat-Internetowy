package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.service.MessageService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void sendMessage_MessageSent_StatusOk() throws Exception {
        Message message = new Message("123", "Bob");
        mvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(message)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllMessages_GetAllMessages_ReturnJsonArray() throws Exception {
        //given
        Message message1 = new Message("123", "Alice");
        Message message2 = new Message("123", "Alice");
        Map<String, List<Message>> allMessages = new HashMap<>();
        allMessages.put("Alice", Arrays.asList(message1, message2));
        given(messageService.getAllMessages()).willReturn(allMessages);
        //expected
        mvc.perform(get("/messages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.Alice.*", hasSize(2)));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}