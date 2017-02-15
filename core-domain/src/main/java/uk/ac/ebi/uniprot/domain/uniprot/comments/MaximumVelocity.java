package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.interfaces.HasEvidences;

public interface MaximumVelocity extends HasEvidences {
	public float getVelocity();
	public Enzyme getEnzyme();
	public MaxVelocityUnit getMaxVelocityUnit();

}
