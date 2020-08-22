package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    public User findByUsername(String userName);
    @Query("select id from User where username=?1")
    public String findIdByUsername(String username);
    public boolean existsByUsername(String username);
    @Query("select id,username,email from User where username like ?1% and email like ?2%")
    public List<Object[]> selectValuesThatBeginWith(String startUsername,String email);

}
