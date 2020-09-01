package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.exception.IncorrectUserException;
import pl.ostek.internet_chat.exception.UserAlreadyExistsException;
import pl.ostek.internet_chat.exception.UserNotFoundException;
import pl.ostek.internet_chat.mapper.UserMapper;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.repository.UserRepository;
import pl.ostek.internet_chat.validator.UserValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserValidator userValidator;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user=userRepository.findByUsername(s);
        if(user==null)
            throw new UsernameNotFoundException("User "+s+" not found!");
        return user;
    }

    public void createUser(User user){
        user.setRole("user");
        userValidator.validate(user);
        if(userRepository.existsByUsername(user.getUsername()))
            throw new UserAlreadyExistsException(user);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<UserDto> findUsersThatBeginWith(String startUsername, String email){
        if(startUsername==null)
            startUsername="";
        if(email==null)
            email="";
        return userRepository.selectUserDtoThatBeginWith(startUsername,email);
    }

}
