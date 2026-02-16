package org.uniprot.core.proteome;

import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

import java.io.Serializable;

public interface RelatedProteome extends Serializable {
    ProteomeId getId();
    Float getSimilarity();
    Taxonomy getTaxId();
}
