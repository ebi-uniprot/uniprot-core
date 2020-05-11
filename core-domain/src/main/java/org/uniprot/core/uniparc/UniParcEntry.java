package org.uniprot.core.uniparc;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

import java.io.Serializable;
import java.util.List;

/**
 * @author jluo
 * @date: 21 May 2019
 */
public interface UniParcEntry extends Serializable {
    UniParcId getUniParcId();

    List<UniParcCrossReference> getUniParcCrossReferences();

    Sequence getSequence();

    String getUniProtExclusionReason();

    List<SequenceFeature> getSequenceFeatures();

    List<Taxonomy> getTaxonomies();
}
