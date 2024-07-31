package org.uniprot.core.uniparc;

import java.io.Serializable;
import java.util.List;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public interface SequenceFeature extends Serializable {
    InterProGroup getInterProDomain();

    SignatureDbType getSignatureDbType();

    String getSignatureDbId();

    List<SequenceFeatureLocation> getLocations();
}
