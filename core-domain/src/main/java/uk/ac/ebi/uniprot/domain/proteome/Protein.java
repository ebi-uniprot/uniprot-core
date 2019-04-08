package uk.ac.ebi.uniprot.domain.proteome;

import java.io.Serializable;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;

public interface Protein extends Serializable {
	UniProtAccession getAccession();
	UniProtEntryType getEntryType();
	long getSequenceLength();
	String getGeneName();
	GeneNameType getGeneNameType();
}
