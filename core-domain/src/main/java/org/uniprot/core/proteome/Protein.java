package org.uniprot.core.proteome;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.UniProtkbEntryType;

public interface Protein extends Serializable {
    UniProtkbAccession getAccession();

    UniProtkbEntryType getEntryType();

    long getSequenceLength();

    String getGeneName();

    GeneNameType getGeneNameType();
}
