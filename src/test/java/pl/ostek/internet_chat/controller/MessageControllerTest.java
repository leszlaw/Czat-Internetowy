package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.model.dto.MessageDto;
import pl.ostek.internet_chat.service.MessageService;
import pl.ostek.internet_chat.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MessageService messageService;

    @MockBean
    UserService userService;

    @MockBean
    BCryptPasswordEncoder encoder;

    @Mock
    Principal principal;

    @Test
    void sendMessage_CorrectMessage_StatusOk() throws Exception {
        //given
        MessageDto message = new MessageDto("123","Bob",null);
        MessageDto expected = new MessageDto("123","Bob","Alice");
        given(principal.getName()).willReturn("Alice");
        //when
        ResultActions result = mvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)).principal(principal));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
        verify(messageService).sendMessage(expected);
    }

    @Test
    void getAllMessages_ThreeMessages_ReturnJsonArray() throws Exception {
        //given
        MessageDto message1 = new MessageDto("123","Alice","Bob");
        MessageDto message2 = new MessageDto("123","Alice","Bob");
        List<MessageDto> allMessages= Arrays.asList(message1,message2);
        given(messageService.getAllReceiverMessages("Alice")).willReturn(allMessages);
        given(principal.getName()).willReturn("Alice");
        //when
        ResultActions result = mvc.perform(get("/messages")
                .contentType(MediaType.APPLICATION_JSON).principal(principal));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allMessages)))
                .andDo(print());

        verify(messageService).getAllReceiverMessages("Alice");
    }

}