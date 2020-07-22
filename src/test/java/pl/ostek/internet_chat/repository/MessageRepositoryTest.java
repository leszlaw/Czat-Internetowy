package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/schema.sql")
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    @Sql({"/schema.sql","/test-data.sql"})
    public void findAll_3MessagesInRepository_Return3Messages(){
        assertThat(messageRepository.findAll()).hasSize(3);
    }

    @Test
    public void findAll_EmptyRepository_ReturnEmptyArray(){
        assertThat(messageRepository.findAll()).isEmpty();
    }

}