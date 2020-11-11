package org.uniprot.core.fasta;

import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public interface UniProtKBFasta extends ProteinFasta {

    UniProtKBEntryType getEntryType();

    UniProtKBId getUniProtkbId();

    String getProteinName();

    Organism getOrganism();

    String getGeneName();

    ProteinExistence getProteinExistence();

    FlagType getFlagType();

    Integer getSequenceVersion();
}
