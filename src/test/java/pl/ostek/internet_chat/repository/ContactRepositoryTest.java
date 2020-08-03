package pl.ostek.internet_chat.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql","/test-data.sql"})
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void selectValuesOfUsersByContactsOwner_UsernameAdmin_ReturnArray(){
        //given
        Object[] o1=new Object[]{"2","user","user@office.pl"};
        Object[] o2=new Object[]{"3","alice","alice@office.pl"};
        Object[] o3=new Object[]{"4","bob","Bob@office.pl"};
        //when
        List<Object[]> values=contactRepository.selectValuesOfUsersByContactsOwner("admin");
        //then
        assertThat(values.get(0)).isEqualTo(o1);
        assertThat(values.get(1)).isEqualTo(o2);
        assertThat(values.get(2)).isEqualTo(o3);
        assertThat(values).hasSize(3);
    }

    @ParameterizedTest(name = "Given ownerId={0} & userId={1} return {2}")
    @CsvSource(value = {"1,3,true","3,1,false","2,1,true"})
    public void existsByOwnerIdAndUserId_GivenIds_ReturnCorrectResult(String ownerId,String userId,boolean result){
        assertThat(contactRepository.existsByOwnerIdAndUserId(ownerId,userId)).isEqualTo(result);
    }

}
