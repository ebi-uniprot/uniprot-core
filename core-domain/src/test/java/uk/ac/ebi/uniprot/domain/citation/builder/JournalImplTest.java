package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.impl.JournalImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JournalImplTest {

	@Test
	void testJournalImpl() {
		Journal journal =new JournalImpl("Nature");
		assertEquals("Nature", journal.getName());
		TestHelper.verifyJson(journal);
	}

}
