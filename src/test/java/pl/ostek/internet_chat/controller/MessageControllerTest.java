package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.model.Message;
import pl.ostek.internet_chat.service.MessageService;

import java.util.*;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageService messageService;

    @Test
    public void sendMessage_CorrectMessage_StatusOk() throws Exception {
        //given
        Message message = Message.builder().receiverId("Bob").message("123").build();
        //when
        ResultActions result = mvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        verify(messageService).sendMessage(message);
    }

    @Test
    public void getAllMessages_ThreeMessages_ReturnJsonArray() throws Exception {
        //given
        Message message1 = Message.builder().receiverId("Alice").message("123").build();
        Message message2 = Message.builder().receiverId("Bob").message("123").build();
        List<Message> allMessages = Arrays.asList(message1,message2);

        given(messageService.getAllMessages()).willReturn(allMessages);
        //when
        ResultActions result = mvc.perform(get("/messages")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allMessages)))
                .andDo(print());

    }

}