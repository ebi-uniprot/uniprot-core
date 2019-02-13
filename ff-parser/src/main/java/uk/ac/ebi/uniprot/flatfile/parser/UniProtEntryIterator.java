package uk.ac.ebi.uniprot.flatfile.parser;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;

import java.util.Iterator;

public interface UniProtEntryIterator extends Iterator<UniProtEntry> {
	void setInput(String filename, String keywordFile, String diseaseFile, String accessionGoPubmedFile) ;
}
