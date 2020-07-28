package pl.ostek.internet_chat.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.User;

import java.util.HashMap;

@Repository
public class UserRepository extends HashMap<String, User> {

    public UserRepository(BCryptPasswordEncoder encoder){

        User user1=new User("admin",encoder.encode("admin"),"admin");

        put("admin",user1);

    }

}
