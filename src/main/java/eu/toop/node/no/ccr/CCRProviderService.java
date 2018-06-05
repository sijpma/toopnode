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

	/*
	Deleted unit: https://data.brreg.no/enhetsregisteret/api/enheter/818511752
	https://data.brreg.no/enhetsregisteret/api/enheter/815597222
	 */

	public ChamberOfCommerceDataSet getDataSet(String orgNo) throws IOException {
		ChamberOfCommerceDataSet set = new ChamberOfCommerceDataSet();
		String json = super.get("https://data.brreg.no/enhetsregisteret/api/enheter/" + orgNo);
		JsonNode root = new ObjectMapper().readTree(json.getBytes(StandardCharsets.UTF_8));

		set.setCompanyCode(root.get("organisasjonsnummer").asText());
		set.setCompanyName(root.get("navn").asText());
		set.setCompanyType(root.get("organisasjonsform") == null ? "NOT_AVAILABLE" : root.get("organisasjonsform").get("kode").asText());
		set.setLegalStatus(GetLegalStatus(root));
		set.setLegalStatusEffectiveDate("NOT_AVAILABLE");
		set.setRegistrationAuthority("Central Coordinating Register for Legal Entities");
		set.setRegistrationDate(root.get("stiftelsesdato") == null ? "NOT_AVAILABLE" : root.get("stiftelsesdato").asText());
		set.setRegistrationNumber("NOT_APPLICABLE");
		set.setActivityDeclaration("NOT_AVAILABLE");
		
		Address address = new Address();
		JsonNode a = root.get("postadresse");
		if (a == null) {
			a = root.get("forretningsadresse");
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

	private String GetLegalStatus(JsonNode node) {
		if (node.get("slettedato") != null) {
			return "deleted";
		}
		if (node.get("konkurs").asText() != "false") {
			return "bankrupt";
		}
		if (node.get("underAvvikling").asText() != "false") {
			return "dissolving";
		}
		if (node.get("underTvangsavviklingEllerTvangsopplosning").asText() != "false") {
			return "dissolving-forced";
		}
		return "active";
	}
}
