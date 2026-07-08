package org.uniprot.core.proteome;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

public interface RelatedProteome extends Serializable {
    ProteomeId getId();

    Float getSimilarity();

    Taxonomy getTaxId();
}
