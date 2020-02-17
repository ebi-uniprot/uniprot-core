package org.uniprot.core.taxonomy;

import org.uniprot.core.Statistics;

public interface TaxonomyStatistics extends Statistics {

    long getReferenceProteomeCount();

    long getProteomeCount();

    default boolean hasReferenceProteomeCount() {
        return getReferenceProteomeCount() > 0L;
    }

    default boolean hasProteomeCount() {
        return getProteomeCount() > 0L;
    }
}
