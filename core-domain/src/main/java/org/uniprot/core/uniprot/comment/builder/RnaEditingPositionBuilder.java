package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.impl.RnaEditingCommentImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class RnaEditingPositionBuilder
        implements Builder<RnaEditingPositionBuilder, RnaEdPosition> {
    private String position;
    private List<Evidence> evidences = new ArrayList<>();

    private RnaEditingPositionBuilder() {}

    public RnaEditingPositionBuilder(String position, List<Evidence> evidences) {
        this.position = position;
        this.evidences = this.evidences = nonNullList(evidences);
    }

    @Override
    public RnaEdPosition build() {
        return new RnaEditingCommentImpl.RnaEdPositionImpl(position, evidences);
    }

    @Override
    public RnaEditingPositionBuilder from(RnaEdPosition instance) {
        evidences.clear();
        return this.evidences(instance.getEvidences()).position(instance.getPosition());
    }

    public RnaEditingPositionBuilder position(String position) {
        this.position = position;
        return this;
    }

    public RnaEditingPositionBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public RnaEditingPositionBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }
}
