package org.uniprot.core.genecentric;

import java.io.Serializable;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public interface Protein extends Serializable {

    UniProtKBEntryType getEntryType();

    UniProtKBAccession getAccession();

    UniProtKBId getUniProtkbId();

    String getProteinName();

    Organism getOrganism();

    String getGeneName();

    ProteinExistence getProteinExistence();

    FlagType getFlagType();

    String getProteomeId();

    Sequence getSequence();

    int getSequenceVersion();
}
