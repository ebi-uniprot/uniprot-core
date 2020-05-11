package org.uniprot.core.antigen;

import java.util.List;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author lgonzales
 * @since 05/05/2020
 */
public interface AntigenEntry {

    UniProtKBAccession getPrimaryAccession();

    UniProtKBId getUniProtkbId();

    Organism getOrganism();

    Sequence getSequence();

    List<AntigenFeature> getFeatures();
}
