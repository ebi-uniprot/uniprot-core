package uk.ac.ebi.uniprot.json.parser.uniparc;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;
import org.uniprot.core.uniparc.builder.UniParcDBCrossReferenceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

public class UniParcDbCrossReferenceTest {
	@Test
	void test() {
		UniParcDBCrossReferenceBuilder builder = new UniParcDBCrossReferenceBuilder();
		builder.databaseType(UniParcDatabaseType.TREMBL)
		.id("A0A0C4DHG2" )
		.versionI(1)
		.version(1)
		.active(true)
		.created(LocalDate.of(2015, 4, 1))
		.lastUpdated(LocalDate.of(2019, 5, 8));
		List<Property> properties = new ArrayList<>();
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "7227"));
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "Gelsolin, isoform J"));
		properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "Gel"));
		builder.properties(properties);
		
		UniParcDBCrossReference xref = builder.build();
		
		ValidateJson.verifyJsonRoundTripParser(UniParcJsonConfig.getInstance().getFullObjectMapper(), xref);
		
		try {
	           ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(xref);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
	}
	@Test
	void testNoProperty() {
		UniParcDBCrossReferenceBuilder builder = new UniParcDBCrossReferenceBuilder();
		builder.databaseType(UniParcDatabaseType.ENSEMBL_VERTEBRATE)
		.id("CG1106-PB" )
		.versionI(1)
		.active(false)
		.created(LocalDate.of(2003, 4, 1))
		.lastUpdated(LocalDate.of(2007, 11, 22));
		UniParcDBCrossReference xref = builder.build();
		ValidateJson.verifyJsonRoundTripParser(UniParcJsonConfig.getInstance().getFullObjectMapper(), xref);
		
		try {
	           ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(xref);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
	}
}

