package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql","/test-data.sql"})
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername_UsernameAdmin_UserReturned(){
        assertThat(userRepository.findByUsername("admin")
                .getUsername()).isEqualTo("admin");
    }

    @Test
    void findByUsername_UsernameAdam_UserNotReturned(){
        assertThat(userRepository.findByUsername("adam")).isEqualTo(null);
    }

    @Test
    void existsByUsername_UserExists_ReturnTrue(){
        assertThat(userRepository.existsByUsername("admin")).isTrue();
    }

    @Test
    void existsByUsername_UserNotExists_ReturnFalse(){
        assertThat(userRepository.existsByUsername("adam")).isFalse();
    }

    @ParameterizedTest(name = "Select values that username start with \"{0}\" and email " +
            "start with {1} returned {2} objects.")
    @CsvSource(value = {"a,a,2","u,u,1","b,B,1"})
    void selectValuesThatBeginWith_StartsWithChar_ReturnValues(String startUsername
            ,String startEmail, int size) {
        assertThat(userRepository.selectValuesThatBeginWith(startUsername,startEmail)).hasSize(size);
    }

    @ParameterizedTest(name = "Find by username={0} return id={1}")
    @CsvSource(value = {"admin,1","user,2","alice,3","bob,4"})
    void findIdByUsername(String username,String id){
        assertThat(userRepository.findIdByUsername(username)).isEqualTo(id);
    }

    }
