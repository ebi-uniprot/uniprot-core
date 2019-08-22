package org.uniprot.core.uniprot.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinAltName extends Serializable {
	Name getFullName();

	List<Name> getShortNames();

	List<EC> getEcNumbers();
	
	boolean hasFullName();

	boolean hasShortNames();

	boolean hasEcNumbers();

	boolean isValid();
}