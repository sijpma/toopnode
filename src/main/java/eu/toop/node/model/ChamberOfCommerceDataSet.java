package eu.toop.node.model;

import lombok.Data;

@Data
public class ChamberOfCommerceDataSet implements DataSet {

	private String companyCode;
	private String companyName;
	private Address headOfficeAddres;
	private String companyType;
	private String legalStatus;
	
	
}
