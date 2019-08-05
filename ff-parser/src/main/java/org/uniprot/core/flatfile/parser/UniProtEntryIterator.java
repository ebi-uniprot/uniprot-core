package org.uniprot.core.flatfile.parser;

import java.util.Iterator;

import org.uniprot.core.uniprot.UniProtEntry;

public interface UniProtEntryIterator extends Iterator<UniProtEntry> {
	void setInput(String filename, String keywordFile, String diseaseFile, String accessionGoPubmedFile, String subcellularLocationFile) ;
}
