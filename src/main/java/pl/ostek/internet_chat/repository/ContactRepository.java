package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.Contact;
import pl.ostek.internet_chat.model.UserDto;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {

    @Query(value = "select new pl.ostek.internet_chat.model.UserDto(u1.id,u1.username,u1.email)" +
            " from Contact c" +
            " left join User u1 on c.userId=u1.id" +
            " left join User u2 on c.ownerId=u2.id" +
            " where u2.username = ?1")
    public List<UserDto> selectUserDtoListByContactsOwner(String ownerUsername);
    public boolean existsByOwnerIdAndUserId(String ownerId,String userId);
}
