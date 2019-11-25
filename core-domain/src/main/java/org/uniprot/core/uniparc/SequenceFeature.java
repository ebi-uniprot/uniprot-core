package org.uniprot.core.uniparc;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.Location;

/**
 * @author jluo
 * @date: 22 May 2019
 */
public interface SequenceFeature extends Serializable {
    InterProGroup getInterProDomain();

    SignatureDbType getSignatureDbType();

    String getSignatureDbId();

    List<Location> getLocations();
}
