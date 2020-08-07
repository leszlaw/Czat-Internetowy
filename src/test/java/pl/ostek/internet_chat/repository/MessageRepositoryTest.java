package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pl.ostek.internet_chat.model.Message;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql", "/test-data.sql"})
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;

    @Test
    void findBySenderUsername_CorrectUsername_AllSenderMessagesReturned(){
        Message message1=new Message("2","123","1","2");
        Message message2=new Message("4","123","1","3");
        assertThat(messageRepository.findByReceiverUsername("admin")).isEqualTo(Arrays.asList(message1,message2));
    }
}