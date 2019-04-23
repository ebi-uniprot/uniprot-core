package uk.ac.ebi.uniprot.json.parser.disease;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.disease.CrossReference;
import uk.ac.ebi.uniprot.cv.disease.Disease;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordImpl;
import uk.ac.ebi.uniprot.domain.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import java.util.Arrays;
import java.util.List;

public class DiseaseTest {
	@Test
	void testCrossReference() {
		List<String> props = Arrays.asList("prop1", "prop2", "prop3");
		String id = "XREF-123";
		String databaseType = "SAMPLE_TYPE";
		CrossReference cr = new CrossReference(databaseType, id, props);
		ValidateJson.verifyJsonRoundTripParser(DiseaseJsonConfig.getInstance().getObjectMapper(), cr);
	}
	
	@Test
	void testKeyword() {
		String id = "Sample Keyword";
		String accession = "KW-1234";
		Keyword keyword = new KeywordImpl(id, accession);
		ValidateJson.verifyJsonRoundTripParser(DiseaseJsonConfig.getInstance().getObjectMapper(), keyword);
	}

	@Test
	void testDisease() {
		String id = "Sample Disease";
		String accession ="DI-12345";
		String acronym = "SAMPLE-DIS";
		String def = "This is sample definition.";
		List<String> altNames = Arrays.asList("name1", "name2", "name3");
		Long proteinCount = 100L;

		// cross ref
		List<String> props = Arrays.asList("prop1", "prop2", "prop3");
		String xrefId = "XREF-123";
		String databaseType = "SAMPLE_TYPE";
		CrossReference cr = new CrossReference(databaseType, xrefId, props);

		//keyword
		String kId = "Sample Keyword";
		String kwAC = "KW-1234";
		Keyword keyword = new KeywordImpl(kId, kwAC);

		DiseaseBuilder builder = DiseaseBuilder.newInstance();
		builder.id(id).accession(accession).acronym(acronym).definition(def);
		builder.alternativeNames(altNames).crossReferences(Arrays.asList(cr));
		builder.keywords(Arrays.asList(keyword)).proteinCount(proteinCount);

		Disease disease = builder.build();

		ValidateJson.verifyJsonRoundTripParser(DiseaseJsonConfig.getInstance().getObjectMapper(), disease);
	}
}
