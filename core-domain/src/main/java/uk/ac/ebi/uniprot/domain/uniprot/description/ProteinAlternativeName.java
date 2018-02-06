package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinAlternativeName {
	List<AltName> getAltNames();
	Name getAllergenName();
	Name getBiotechName();
	List<Name> getCDAntigenNames();
	List<Name> getINNNames();
}
