package org.uniprot.core.cv.xdb;

import java.io.Serializable;

public interface CrossRefEntry extends Serializable {
    String getAccession();

    String getName();

    String getAbbrev();

    String getPubMedId();

    String getDoiId();

    String getLinkType();

    String getServer();

    String getDbUrl();

    String getCategory();

    Long getReviewedProteinCount();

    Long getUnreviewedProteinCount();
}
