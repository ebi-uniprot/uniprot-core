package uk.ac.ebi.uniprot.domain.uniparc;

import java.io.Serializable;
import java.util.List;

import uk.ac.ebi.uniprot.domain.Location;

/**
 *
 * @author jluo
 * @date: 22 May 2019
 *
*/

public interface SequenceFeature extends Serializable {
	InterproGroup getInterProDomain();
	SignatureDbType getSignatureDbType();
	String getSignatureDbId();
	List<Location> getLocations();
}

