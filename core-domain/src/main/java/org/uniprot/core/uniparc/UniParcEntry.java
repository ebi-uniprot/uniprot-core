package org.uniprot.core.uniparc;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

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
