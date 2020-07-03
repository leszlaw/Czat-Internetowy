package pl.ostek.internet_chat.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PropertiesReader {

	@Value("${app.version}")
	private String version;
	
}
