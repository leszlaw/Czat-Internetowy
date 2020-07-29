package pl.ostek.internet_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,String> {
    public UserProfile findByUser_Id(String userId);
}
