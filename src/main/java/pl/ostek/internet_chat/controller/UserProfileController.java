package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.entity.Gender;
import pl.ostek.internet_chat.model.entity.UserProfile;
import pl.ostek.internet_chat.service.UserProfileService;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public void createProfile(@RequestBody UserProfile userProfile, Principal principal) {
        userProfileService.createProfile(userProfile, principal.getName());
        log.info(userProfile.toString() + " action=createProfile status=successful");
    }

    @PutMapping
    public void saveProfile(@RequestBody UserProfile userProfile, Principal principal) {
        userProfileService.updateProfile(userProfile, principal.getName());
        log.info(userProfile.toString() + " action=saveProfile status=successful");
    }

    @PatchMapping
    public void editProfile(@RequestBody Map<String, String> updates, Principal principal) {
        UserProfile userProfile = new UserProfile();
        updates.forEach((s, v) -> {
            if (s.equals("description"))
                userProfile.setDescription(v);
            else if (s.equals("gender"))
                userProfile.setGender(Gender.valueOf(v));
        });
        userProfileService.partialUpdateProfile(userProfile, principal.getName());
        log.info(updates.toString() + "\" action=editProfile status=successful");
    }

}
