package uk.ac.ebi.uniprot.domain.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;


/**
 * Contains a flag descriptors found within a DE line
 *
 * along with any evidences
 */
public interface Flag extends HasEvidences {
    public FlagType getFlagType();
    
}
