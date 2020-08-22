package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.entity.Message;
import pl.ostek.internet_chat.model.dto.MessageDto;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,String> {
    @Query("select new pl.ostek.internet_chat.model.dto.MessageDto(m.message,m.receiver.username,m.sender.username)" +
            " from Message m where m.receiver.username=?1")
    List<MessageDto> findDtoByReceiverUsername(String username);
}
