package org.uniprot.core.literature;

import org.uniprot.core.Statistics;

/** @author lgonzales */
public interface LiteratureStatistics extends Statistics {

    long getMappedProteinCount();

    default boolean hasMappedProteinCount() {
        return getMappedProteinCount() > 0;
    }
}
