package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.PhysiologicalReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PhysiologicalReactionImpl implements PhysiologicalReaction, Serializable {
    private static final long serialVersionUID = -8105451459738897020L;
    private PhysiologicalDirectionType directionType;
    private DBCrossReference<ReactionReferenceType> reactionReference;
    private List<Evidence> evidences;

    private PhysiologicalReactionImpl() {
        this.evidences = Collections.emptyList();
    }

    public PhysiologicalReactionImpl(PhysiologicalReactionBuilder builder) {
        this.directionType = builder.getDirectionType();
        this.reactionReference = builder.getReactionReference();
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public PhysiologicalDirectionType getDirectionType() {
        return directionType;
    }

    @Override
    public DBCrossReference<ReactionReferenceType> getReactionReference() {
        return reactionReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysiologicalReactionImpl that = (PhysiologicalReactionImpl) o;
        return directionType == that.directionType &&
                Objects.equals(reactionReference, that.reactionReference) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directionType, reactionReference, evidences);
    }
}
