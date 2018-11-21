package uk.ac.ebi.uniprot.domain.citation.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.impl.JournalImpl;

class JournalImplTest {

	@Test
	void testJournalImpl() {
		Journal journal =new JournalImpl("Nature");
		assertEquals("Nature", journal.getName());
		TestHelper.verifyJson(journal);
	}

}
