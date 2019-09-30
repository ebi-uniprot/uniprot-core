package org.uniprot.core.proteome;

import java.io.Serializable;

import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.UniProtEntryType;

public interface Protein extends Serializable {
    UniProtAccession getAccession();

    UniProtEntryType getEntryType();

    long getSequenceLength();

    String getGeneName();

    GeneNameType getGeneNameType();
}
