package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,String> {

}
