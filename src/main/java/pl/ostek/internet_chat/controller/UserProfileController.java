package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.UserProfile;
import pl.ostek.internet_chat.service.UserProfileService;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public void createProfile(@RequestBody UserProfile userProfile, Principal principal){
        userProfileService.createProfile(userProfile,principal.getName());
        log.info(userProfile.toString()+" action=createProfile status=successful");
    }

    @PutMapping
    public void editProfile(@RequestBody UserProfile userProfile, Principal principal){
        userProfileService.editProfile(userProfile,principal.getName());
        log.info(userProfile.toString()+" action=editProfile status=successful");
    }

}
