package pl.ostek.internet_chat.repository;

import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.Message;
import java.util.HashMap;
import java.util.List;

@Repository
public class MessageRepository extends HashMap<String,List<Message>> {
}
