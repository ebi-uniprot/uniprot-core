package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;


public interface Reaction extends HasEvidences{
	String getName();
	List<ReactionReference> getReactionReferences();
	ECNumber getECNumber();
}
