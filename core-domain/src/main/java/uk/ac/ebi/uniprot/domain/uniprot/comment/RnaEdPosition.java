package uk.ac.ebi.uniprot.domain.uniprot.comment;


import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

public interface RnaEdPosition extends HasEvidences {

    String getPosition();
}
