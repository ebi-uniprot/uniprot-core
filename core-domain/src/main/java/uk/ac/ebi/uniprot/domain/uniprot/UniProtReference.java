package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.HasEvidences;

import java.util.List;

public interface UniProtReference extends HasEvidences {
    Citation getCitation();

    public List<ReferenceComment> getTypedReferenceComments(ReferenceCommentType type);

    public List<ReferenceComment> getReferenceComments();

    public List<String> getReferencePositions();

}
