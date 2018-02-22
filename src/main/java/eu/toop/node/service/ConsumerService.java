package eu.toop.node.service;

import eu.toop.node.model.DataSet;

public interface ConsumerService {

	public DataSet provide(String country, String nr);
	
}
