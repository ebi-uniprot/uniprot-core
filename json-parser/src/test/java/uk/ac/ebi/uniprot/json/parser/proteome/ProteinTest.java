package uk.ac.ebi.uniprot.json.parser.proteome;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.ebi.uniprot.domain.proteome.CanonicalProtein;
import uk.ac.ebi.uniprot.domain.proteome.GeneNameType;
import uk.ac.ebi.uniprot.domain.proteome.Protein;
import uk.ac.ebi.uniprot.domain.proteome.builder.CanonicalProteinBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteinBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import uk.ac.ebi.uniprot.json.parser.uniprot.UniprotJsonConfig;

public class ProteinTest {
	@Test 
	void testProtein() {
	String accession ="P12345";
	Protein protein = ProteinBuilder.newInstance().accession(accession)
			.entryType(UniProtEntryType.SWISSPROT)
			.geneName("some gene")
			.geneNameType(GeneNameType.ENSEMBL)
			.sequenceLength(307)
			.build();	
	   ValidateJson.verifyJsonRoundTripParser(ProteomeJsonConfig.getInstance().getFullObjectMapper(), protein);
	   
	   try {
           ObjectMapper mapper = ProteomeJsonConfig.getInstance().getSimpleObjectMapper();
           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(protein);
           System.out.println(json);
       }catch(Exception e) {
           fail(e.getMessage());
       }
	   
	   
	}
	@Test
	void testCanonicalProtein() {
		String accession ="P12345";
		Protein protein = ProteinBuilder.newInstance().accession(accession)
				.entryType(UniProtEntryType.SWISSPROT)
				.geneName("some gene")
				.geneNameType(GeneNameType.ENSEMBL)
				.sequenceLength(307)
				.build();	
		
		Protein rProtein1 = ProteinBuilder.newInstance().accession("P23456")
				.entryType(UniProtEntryType.SWISSPROT)
				.geneName("some gene2")
				.geneNameType(GeneNameType.ENSEMBL)
				.sequenceLength(303)
				.build();	
		Protein rProtein2 = ProteinBuilder.newInstance().accession("P26456")
				.entryType(UniProtEntryType.TREMBL)
				.geneName("some gene5")
				.geneNameType(GeneNameType.MOD)
				.sequenceLength(504)
				.build();	
		
		CanonicalProtein cProtein = CanonicalProteinBuilder.newInstance().canonicalProtein(protein)
				.addRelatedProtein(rProtein1)
				.addRelatedProtein(rProtein2)
				.build();	
		  ValidateJson.verifyJsonRoundTripParser(ProteomeJsonConfig.getInstance().getFullObjectMapper(), cProtein);
		  try {
	           ObjectMapper mapper = ProteomeJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cProtein);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
		   
	}
}
