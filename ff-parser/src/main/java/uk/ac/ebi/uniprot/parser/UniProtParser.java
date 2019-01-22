package uk.ac.ebi.uniprot.parser;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;

public interface UniProtParser {
	UniProtEntry parse(String entryff);
}
