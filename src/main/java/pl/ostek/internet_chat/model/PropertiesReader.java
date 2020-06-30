package pl.ostek.internet_chat.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesReader {

	@Value("${app.version}")
	private String version;
	
	public String getVersion() {
		return version;
	}
	
}
