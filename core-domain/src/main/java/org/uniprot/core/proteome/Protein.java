package org.uniprot.core.proteome;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;

public interface Protein extends Serializable {
    UniProtKBAccession getAccession();

    UniProtKBEntryType getEntryType();

    long getSequenceLength();

    String getGeneName();

    GeneNameType getGeneNameType();
}
