package pl.ostek.internet_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.service.ContactService;
import pl.ostek.internet_chat.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = ContactController.class)
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactService contactService;

    @MockBean
    UserService userService;

    @MockBean
    BCryptPasswordEncoder encoder;

    @Test
    @WithMockUser(username="admin")
    void addContact_CorrectId_ContactAdded() throws Exception{
        //when
        ResultActions result = mvc.perform(post("/contacts?id=1").with(csrf()));

        result.andExpect(status().isOk())
                .andDo(print());
        //then
        verify(contactService).addContactById("1","admin");
    }

    @Test
    @WithMockUser(username="alice")
    void getContacts_GivenContacts_ReturnJsonArray() throws Exception{
        //given
        UserDto bob=new UserDto("2","bob","bob@office.pl");
        List<UserDto> users= Arrays.asList(bob);
        given(contactService.getContacts("alice")).willReturn(users);
        //when
        ResultActions result = mvc.perform(get("/contacts"));

        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(users)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(bob.getId())))
                .andExpect(jsonPath("$[0].username", is(bob.getUsername())))
                .andExpect(jsonPath("$[0].email", is(bob.getEmail())))
                .andDo(print());
        //then
        verify(contactService).getContacts("alice");
    }

}
