package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql", "/test-data.sql"})
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @ParameterizedTest(name = "Find by userId = \"{0}\" returned UserProfile.")
    @CsvSource(value = {"1,I am admin",
            "2,I am user"})
    public void findByUser_Id_CorrectId_UserProfileReturned(String id, String description) {
        assertThat(userProfileRepository.findByUser_Id(id)
                .getDescription()).isEqualTo(description);
    }

    @Test
    public void findByUser_Id_IncorrectId_ReturnedNull() {
        assertThat(userProfileRepository.findByUser_Id("3")).isEqualTo(null);
    }

    @ParameterizedTest(name = "Find by username = \"{0}\" returned UserProfile.")
    @CsvSource(value = {"admin,I am admin",
            "user,I am user"})
    public void findByUsername_CorrectUsername_UserProfileReturned(String username,String description) {
        assertThat(userProfileRepository.findByUsername(username)
                .getDescription()).isEqualTo(description);
    }

    @Test
    public void findByUsername_IncorrectUsername_ReturnedNull() {
        assertThat(userProfileRepository.findByUser_Id("adam")).isEqualTo(null);
    }

}
