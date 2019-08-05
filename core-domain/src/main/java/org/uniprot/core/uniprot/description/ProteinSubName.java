package org.uniprot.core.uniprot.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinSubName extends Serializable {
	Name getFullName();

	List<EC> getEcNumbers();

	boolean hasFullName();

	boolean hasEcNumbers();

	boolean isValid();
}
