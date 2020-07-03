package pl.ostek.internet_chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ostek.internet_chat.model.PropertiesReader;

@RestController
@RequiredArgsConstructor
public class PropertiesController {

    private final PropertiesReader propertiesReader;

    @GetMapping("/version")
    public String getVersion() {
        return propertiesReader.getVersion();
    }

}
