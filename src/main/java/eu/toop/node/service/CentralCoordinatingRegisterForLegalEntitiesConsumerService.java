package eu.toop.node.service;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.toop.node.model.DataSet;

@Service
public class CentralCoordinatingRegisterForLegalEntitiesConsumerService implements ConsumerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CentralCoordinatingRegisterForLegalEntitiesConsumerService.class);

	@Autowired
	private CountrySpecificProviderService service;
	
	@Autowired
	private Map<String, String> providerServices;
	
	@Override
	public DataSet provide(String country, String id) {
		LOGGER.info("Getting dataset from {} ProviderService for id {}.", country, id);
		String url = providerServices.get(country);
		try {
			return service.getDataSet(url, id);
		} catch (IOException e) {
			return null;
		}
	}

}
