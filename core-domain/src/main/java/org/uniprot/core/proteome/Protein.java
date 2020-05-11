package org.uniprot.core.proteome;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;

import java.io.Serializable;

public interface Protein extends Serializable {
    UniProtKBAccession getAccession();

    UniProtKBEntryType getEntryType();

    long getSequenceLength();

    String getGeneName();

    GeneNameType getGeneNameType();
}
