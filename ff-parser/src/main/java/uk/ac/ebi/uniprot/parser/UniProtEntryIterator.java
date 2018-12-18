package uk.ac.ebi.uniprot.parser;

import java.util.Iterator;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;

public interface UniProtEntryIterator extends Iterator<UniProtEntry> {
	void setInput(String filename, String keywordFile, String diseaseFile) ;
}
