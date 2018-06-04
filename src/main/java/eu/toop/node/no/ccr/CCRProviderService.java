package eu.toop.node.no.ccr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.toop.node.model.Address;
import eu.toop.node.model.ChamberOfCommerceDataSet;
import eu.toop.node.util.RestClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class CCRProviderService extends RestClient {

	public CCRProviderService() {
		super();
	}

	public ChamberOfCommerceDataSet getDataSet(String kvk) throws IOException {
		ChamberOfCommerceDataSet set = new ChamberOfCommerceDataSet();
		String json = super.get("https://data.brreg.no/enhetsregisteret/api/enheter?navn=" + kvk);
		JsonNode root = new ObjectMapper().readTree(json.getBytes(StandardCharsets.UTF_8));
		JsonNode node = root.get("_embedded").get("enheter").get(0);

		set.setCompanyCode(node.get("organisasjonsnummer").asText());
		set.setCompanyName(node.get("navn").asText());
		set.setCompanyType("NOT_AVAILABLE");
		set.setLegalStatus("NOT_AVAILABLE");
		set.setLegalStatusEffectiveDate("NOT_AVAILABLE");
		set.setRegistrationAuthority("Central Coordinating Register for Legal Entities");
		set.setRegistrationDate(node.get("stiftelsesdato") == null ? "NOT_AVAILABLE" : node.get("stiftelsesdato").asText());
		set.setRegistrationNumber("NOT_APPLICABLE");
		set.setActivityDeclaration("NOT_AVAILABLE");
		
		Address address = new Address();
		JsonNode a = node.get("postadresse");
		if (a == null) {
			a = node.get("forretningsadresse");
		}

		if (a != null) {
			String streetAddress = "";
			for (final JsonNode addressPart : a.get("adresse")) {
				streetAddress = streetAddress + addressPart.asText() + "\n";
			}
			address.setStreetName(streetAddress.trim());
			address.setPostalCode(a.get("postnummer").asText());
			address.setCity(a.get("poststed").asText());
			address.setCountry(a.get("landkode").asText());
			set.setHeadOfficeAddres(address);
		}
		return set;
	}
}
