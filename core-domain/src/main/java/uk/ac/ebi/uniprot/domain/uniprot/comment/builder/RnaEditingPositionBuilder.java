package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class RnaEditingPositionBuilder implements Builder2<RnaEditingPositionBuilder, RnaEdPosition> {
    private String position;
    private List<Evidence> evidences = new ArrayList<>();

    private RnaEditingPositionBuilder() {
    }

    public RnaEditingPositionBuilder(String position, List<Evidence> evidences) {
        this.position = position;
        nonNullAddAll(evidences, this.evidences);
    }

    @Override
    public RnaEdPosition build() {
        return new RnaEditingCommentImpl.RnaEdPositionImpl(position, evidences);
    }

    @Override
    public RnaEditingPositionBuilder from(RnaEdPosition instance) {
        evidences.clear();
        return this
                .evidences(instance.getEvidences())
                .position(instance.getPosition());
    }

    public RnaEditingPositionBuilder position(String position) {
        this.position = position;
        return this;
    }

    public RnaEditingPositionBuilder evidences(List<Evidence> evidences) {
        nonNullAddAll(evidences, this.evidences);
        return this;
    }

    public RnaEditingPositionBuilder addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }
}
