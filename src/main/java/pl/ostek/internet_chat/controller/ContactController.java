package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import pl.ostek.internet_chat.model.SimplifiedUser;
import pl.ostek.internet_chat.service.ContactService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public void addContact(@RequestParam String id, Principal principal) {
        contactService.addContactById(id, principal.getName());
        log.info(principal.getName() + "added a contact with userId=" + id + " action=addContact status=successful");
    }


    @GetMapping
    public List<SimplifiedUser> getContacts(Principal principal) {
        val simplifiedUsers = contactService.getContacts(principal.getName());
        log.info(principal.getName() + "gets all contacts action=getContacts status=successful");
        return simplifiedUsers;
    }

}
