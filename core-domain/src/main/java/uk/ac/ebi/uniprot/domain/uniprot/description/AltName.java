package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface AltName {
	ProteinName getFullName();
	List<ProteinName> getShortNames();
	List<ECNumber> getEcNumbers();
	ProteinName getAllergenName();
	ProteinName getBiotechName();
	List<ProteinName> getCDAntigenNames();
	List<ProteinName> getINNNames();
}
