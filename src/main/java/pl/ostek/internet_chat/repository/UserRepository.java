package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    public User findByUsername(String userName);
    public boolean existsByUsername(String username);

}
