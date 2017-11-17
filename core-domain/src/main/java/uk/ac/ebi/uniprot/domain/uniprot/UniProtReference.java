package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.citation.Citation;

import java.util.List;


public interface UniProtReference<T extends Citation> extends HasEvidences {
    T getCitation();
    public List<ReferenceComment> getReferenceComments(ReferenceCommentType type);
    public List<String> getReferencePositions();
    

}
