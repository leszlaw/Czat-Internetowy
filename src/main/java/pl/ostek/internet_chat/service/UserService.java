package pl.ostek.internet_chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.ostek.internet_chat.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user=userRepository.get(s);
        if(user==null)
            throw new UsernameNotFoundException("User "+s+" not found!");
        return user;
    }
}
