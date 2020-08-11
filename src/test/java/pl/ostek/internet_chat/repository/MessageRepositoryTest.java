package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pl.ostek.internet_chat.model.dto.MessageDto;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql", "/test-data.sql"})
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;

    @Test
    void findDtoByReceiverUsername_CorrectUsername_AllSenderMessagesReturned(){
        MessageDto message1=new MessageDto("123","admin","user");
        MessageDto message2=new MessageDto("123","admin","alice");
        assertThat(messageRepository.findDtoByReceiverUsername("admin")).isEqualTo(Arrays.asList(message1,message2));
    }
}