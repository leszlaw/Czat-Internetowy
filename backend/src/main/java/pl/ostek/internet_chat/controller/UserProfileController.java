package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.dto.UserProfileDto;
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
        userProfileService.partialUpdateProfile(updates, principal.getName());
        log.info(updates.toString() + "\" action=editProfile status=successful");
    }

    @GetMapping
    public UserProfileDto getProfile(Principal principal){
        return userProfileService.getUserProfileDtoByUsername(principal.getName());
    }

    @GetMapping("/{id}")
    public UserProfileDto getProfile(@PathVariable("id") String id){
        return userProfileService.getUserProfileDtoById(id);
    }


}
