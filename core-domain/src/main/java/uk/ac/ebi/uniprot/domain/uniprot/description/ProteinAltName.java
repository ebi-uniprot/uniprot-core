package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.io.Serializable;
import java.util.List;

public interface ProteinAltName extends Serializable {
	Name getFullName();

	List<Name> getShortNames();

	List<EC> getEcNumbers();

	Name getAllergenName();

	Name getBiotechName();

	List<Name> getCdAntigenNames();

	List<Name> getInnNames();

	boolean hasFullName();

	boolean hasShortNames();

	boolean hasEcNumbers();

	boolean hasAllergenName();

	boolean hasBiotechName();

	boolean hasCdAntigenNames();

	boolean hasInnNames();

	boolean isValid();
}
