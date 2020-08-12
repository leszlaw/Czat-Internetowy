package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pl.ostek.internet_chat.model.dto.UserDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql", "/test-data.sql"})
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void selectValuesOfUsersByContactsOwner_UsernameAdmin_ReturnArray(){
        //given
        UserDto u1=new UserDto("2","user","user@office.pl");
        UserDto u2=new UserDto("3","alice","alice@office.pl");
        UserDto u3=new UserDto("4","bob","Bob@office.pl");
        //when
        List<UserDto> values=contactRepository.selectUserDtoListByContactsOwner("admin");
        //then
        assertThat(values.get(0)).isEqualTo(u1);
        assertThat(values.get(1)).isEqualTo(u2);
        assertThat(values.get(2)).isEqualTo(u3);
        assertThat(values).hasSize(3);
    }

    @ParameterizedTest(name = "Given ownerId={0} & userId={1} return {2}")
    @CsvSource(value = {"1,3,true","3,1,false","2,1,true"})
    public void existsByOwnerIdAndUserId_GivenIds_ReturnCorrectResult(String ownerId,String userId,boolean result){
        assertThat(contactRepository.existsByOwnerIdAndUserId(ownerId,userId)).isEqualTo(result);
    }

}
