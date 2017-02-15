package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.interfaces.HasEvidences;

public interface Cofactor extends HasEvidences {
	String getName();
	CofactorReference getCofactorReference();
}
