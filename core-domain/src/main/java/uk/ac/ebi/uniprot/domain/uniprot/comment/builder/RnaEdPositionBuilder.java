package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class RnaEdPositionBuilder implements Builder2<RnaEdPositionBuilder, RnaEdPosition> {
    private String position;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public RnaEdPosition build() {
        return new RnaEditingCommentImpl.RnaEdPositionImpl(position, evidences);
    }

    @Override
    public RnaEdPositionBuilder from(RnaEdPosition instance) {
        return new RnaEdPositionBuilder()
                .evidences(instance.getEvidences())
                .position(instance.getPosition());
    }

    public RnaEdPositionBuilder position(String position) {
        this.position = position;
        return this;
    }

    public RnaEdPositionBuilder evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return this;
    }

    public RnaEdPositionBuilder addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }
}
