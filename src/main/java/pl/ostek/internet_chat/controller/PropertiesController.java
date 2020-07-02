package pl.ostek.internet_chat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ostek.internet_chat.model.PropertiesReader;

@RestController
public class PropertiesController {
	
	private PropertiesReader propertiesReader;

	public PropertiesController(PropertiesReader propertiesReader) {
		this.propertiesReader=propertiesReader;
	}
	
	@RequestMapping("/version")
	public String getVersion() {
		return propertiesReader.getVersion();
	}
	

}
