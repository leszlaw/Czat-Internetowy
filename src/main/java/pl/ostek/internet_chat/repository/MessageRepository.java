package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,String> {
    @Query("select m from Message m left join User u on m.receiverId=u.id where u.username=?1")
    List<Message> findByReceiverUsername(String username);
}
