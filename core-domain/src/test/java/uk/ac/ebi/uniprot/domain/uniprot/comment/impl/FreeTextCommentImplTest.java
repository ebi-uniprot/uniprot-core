package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

class FreeTextCommentImplTest {

	@Test
	void testNoEvidence() {
		List<EvidencedValue> texts = createEvidenceValues();
		FreeTextCommentImpl comment = new FreeTextCommentImpl(CommentType.ALLERGEN, texts);
		assertEquals(CommentType.ALLERGEN, comment.getCommentType());
		assertEquals(texts, comment.getTexts());
		TestHelper.verifyJson(comment);
	}

	@Test
	void testWithEvidence() {
		List<EvidencedValue> texts = createEvidenceValues2();
		FreeTextCommentImpl comment = new FreeTextCommentImpl(CommentType.BIOTECHNOLOGY, texts);
		assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
		assertEquals(texts, comment.getTexts());
		TestHelper.verifyJson(comment);
	}

	private List<EvidencedValue> createEvidenceValues2() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
		evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));

		List<Evidence> evidences2 = new ArrayList<>();
		evidences2.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));

		List<EvidencedValue> evidencedValues = new ArrayList<>();
		evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", evidences));
		evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", evidences2));
		return evidencedValues;
	}

	private List<EvidencedValue> createEvidenceValues() {
		List<EvidencedValue> evidencedValues = new ArrayList<>();
		evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
		evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
		return evidencedValues;
	}

}
