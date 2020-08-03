package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,String> {
    public UserProfile findByUser_Id(String userId);
    @Query("select p from UserProfile p left join User u on p.user=u.id where u.username = ?1")
    public UserProfile findByUsername(String username);
}
