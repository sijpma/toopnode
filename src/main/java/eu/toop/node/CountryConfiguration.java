package eu.toop.node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryConfiguration {

	@Bean
	public Map<String, String> providerServices() {
		Map<String, String> providerServices = Collections.synchronizedMap(new HashMap<>());
		providerServices.put("NO", "http://localhost:8081/toopnode/provider/provide?id=");
		return providerServices;
	}
}
