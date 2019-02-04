package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidenceBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EntryMapUtilTest {
	@Test
	void testEvidencesToString() {
		String expected = "{ECO:0000269|PubMed:18258429, ECO:0000313|EMBL:AAC45250.1}";
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(createEvidence("ECO:0000269", "PubMed", "18258429"));
		// ECO:0000313|EMBL:AAC45250.1
		evidences.add(createEvidence("ECO:0000313", "EMBL", "AAC45250.1"));
		String result = EntryMapUtil.evidencesToString(evidences);
		assertEquals(expected, result);
	}

	@Test
	void testEvidencesToStringEmpty() {
		String result = EntryMapUtil.evidencesToString(null);
		assertEquals("", result);
		result = EntryMapUtil.evidencesToString(Collections.emptyList());
		assertEquals("", result);
	}



	private Evidence createEvidence(String code, String dbType, String dbId) {
		return new EvidenceBuilder()
				.evidenceCode(EvidenceCode.codeOf(code))
				.databaseName(dbType)
				.databaseId(dbId)
				.build();
	}
}
