package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ostek.internet_chat.service.PropertiesReader;

@RestController
@RequiredArgsConstructor
public class PropertiesController {

    private final PropertiesReader propertiesReader;

    @GetMapping("/version")
    @CrossOrigin(origins = "http://localhost:4200")
    public String getVersion() {
        return propertiesReader.getVersion();
    }

}
