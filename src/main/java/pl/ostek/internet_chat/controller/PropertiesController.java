package pl.ostek.internet_chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesController {
	
	private PropertiesReader propertiesReader;
	
	@Autowired
	public PropertiesController(PropertiesReader propertiesReader) {
		this.propertiesReader=propertiesReader;
	}
	
	@RequestMapping("/version")
	public String getVersion() {
		return propertiesReader.getVersion();
	}
	

}
