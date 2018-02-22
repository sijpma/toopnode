package eu.toop.node.model;

import lombok.Data;

@Data
public class Address {
	private String streetName;
	private String postalCode;
	private String city;
	private String country;
}