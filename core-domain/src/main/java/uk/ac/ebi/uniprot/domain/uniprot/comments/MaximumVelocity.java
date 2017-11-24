package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface MaximumVelocity extends HasEvidences {
	public float getVelocity();
	public String getEnzyme();
	public String getVelocityUnit();

}
