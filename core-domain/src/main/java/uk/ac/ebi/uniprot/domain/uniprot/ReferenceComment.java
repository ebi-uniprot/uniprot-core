package uk.ac.ebi.uniprot.domain.uniprot;

      import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

public interface ReferenceComment extends EvidencedValue {
    ReferenceCommentType getType();
}
