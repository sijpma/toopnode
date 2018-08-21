package eu.toop.node.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.toop.node.model.DataSet;
import eu.toop.node.no.ccr.CCRProviderService;
import eu.toop.node.provider.ProviderService;

@Service
public class CentralCoordinatingRegisterForLegalEntitiesProviderService implements ProviderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CentralCoordinatingRegisterForLegalEntitiesProviderService.class);
	
	@Autowired
	private CCRProviderService service;
	
	@Override
	public DataSet provide(String id) {
		LOGGER.info("Getting dataset from original source for id {}.", id);
		try {
			return service.getDataSet(id);
		} catch (IOException e) {
			return null;
		}
	}

}
