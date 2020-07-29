package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.SuchUserExistsException;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.repository.UserRepository;

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

}
