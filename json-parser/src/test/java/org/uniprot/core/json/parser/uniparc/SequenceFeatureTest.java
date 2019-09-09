package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniparc.UniParcJsonConfig;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;
import org.uniprot.core.uniparc.builder.SequenceFeatureBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

public class SequenceFeatureTest {
	@Test
	void testInterProGroup() {
		InterproGroup domain =
		new InterProGroupBuilder()
		.id("IPR007123")
		.name("Gelsolin-like domain")
		.build();
		
		 ValidateJson.verifyJsonRoundTripParser(UniParcJsonConfig.getInstance().getFullObjectMapper(), domain);
		 
		 try {
	           ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(domain);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
	}
	
	
	@Test
	void testSequenceFeature() {
		SequenceFeatureBuilder builder = new SequenceFeatureBuilder();
		builder.signatureDbType(SignatureDbType.PFAM)
		.signatureDbId("PF00626")
		.addLocation(new Location(81, 163))
		.addLocation(new Location(202, 267))
		.addLocation(new Location(330, 398))
		.addLocation(new Location(586, 653))
		.addLocation(new Location(692, 766))
		.interproGroup(
		new InterProGroupBuilder()
		.id("IPR007123")
		.name("Gelsolin-like domain")
		.build());
		SequenceFeature sf = builder.build();
		ValidateJson.verifyJsonRoundTripParser(UniParcJsonConfig.getInstance().getFullObjectMapper(), sf);
		
		try {
	           ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sf);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
		
	}

}

