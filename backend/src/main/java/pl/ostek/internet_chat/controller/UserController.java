package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.dto.UserDto;
import pl.ostek.internet_chat.model.entity.User;
import pl.ostek.internet_chat.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
        log.info(user.toString() + " action=createUser status=successful");
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable(name = "id") String id) {
        return userService.findUser(id);
    }

    @GetMapping
    public List<UserDto> findUsers(@RequestParam(required = false) String username,
                                   @RequestParam(required = false) String email) {
        return userService.findUsersThatBeginWith(username,email);
    }

}