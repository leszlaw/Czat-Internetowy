package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql","/test-data.sql"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_UsernameAdmin_UserReturned(){
        assertThat(userRepository.findByUsername("admin")
                .getUsername()).isEqualTo("admin");
    }

    @Test
    public void findByUsername_UsernameAdam_UserNotReturned(){
        assertThat(userRepository.findByUsername("adam")).isEqualTo(null);
    }

    @Test
    public void existsByUsername_UserExists_ReturnTrue(){
        assertThat(userRepository.existsByUsername("admin")).isTrue();
    }

    @Test
    public void existsByUsername_UserNotExists_ReturnFalse(){
        assertThat(userRepository.existsByUsername("adam")).isFalse();
    }


}
