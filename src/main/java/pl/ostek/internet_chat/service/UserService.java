package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.SuchUserExistsException;
import pl.ostek.internet_chat.exception.UserNotFountException;
import pl.ostek.internet_chat.model.SimplifiedUser;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user=userRepository.findByUsername(s);
        if(user==null)
            throw new UsernameNotFoundException("User "+s+" not found!");
        return user;
    }

    public void createUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("user");
        if(userRepository.existsByUsername(user.getUsername()))
            throw new SuchUserExistsException(user);
        userRepository.save(user);
    }

    public User findUser(String id){
        return userRepository.findById(id).orElseThrow(
                ()->{throw new UserNotFountException("User with id="+id+" not found");});
    }

    public List<SimplifiedUser> findUsersThatBeginWith(String startUsername){
        if(startUsername==null)
            startUsername="";
        List<SimplifiedUser> simplifiedUsers=new ArrayList<>();
        userRepository.selectValuesThatBeginWith(startUsername).stream().forEach((o)->{
            simplifiedUsers.add(new SimplifiedUser((String)o[0],(String)o[1]));
        });
        return simplifiedUsers;
    }

}
