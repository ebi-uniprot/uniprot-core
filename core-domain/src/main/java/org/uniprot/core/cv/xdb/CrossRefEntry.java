package org.uniprot.core.cv.xdb;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.Statistics;

public interface CrossRefEntry extends Serializable {
    /**
     * Uniprot accession as an id to identify CrossRef uniquely
     *
     * @return accession
     */
    String getId();

    String getName();

    String getAbbrev();

    String getPubMedId();

    String getDoiId();

    String getLinkType();

    List<String> getServers();

    String getDbUrl();

    String getCategory();

    Statistics getStatistics();
}
