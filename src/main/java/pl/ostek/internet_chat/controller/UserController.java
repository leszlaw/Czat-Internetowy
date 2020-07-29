package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ostek.internet_chat.model.User;
import pl.ostek.internet_chat.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.createUser(user);
        log.info(user.toString()+" action=createUser status=successful");
    }

}
