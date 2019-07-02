package uk.ac.ebi.uniprot.flatfile.parser;

import java.io.Serializable;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;

public interface UniProtParser extends Serializable{
	UniProtEntry parse(String entryff);
}
