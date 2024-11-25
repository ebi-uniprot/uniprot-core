package org.uniprot.core.uniparc;

import java.io.Serializable;

public interface CommonOrganism extends Serializable {
    String getTopLevel();
    String getCommonTaxon();
    Long getCommonTaxonId();
}
